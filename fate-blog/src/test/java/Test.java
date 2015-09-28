import java.io.File;
import java.io.IOException;
import java.security.Security;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.AuthenticationFailedException;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.URLName;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeUtility;

import org.apache.commons.io.FileUtils;

import com.sun.mail.pop3.POP3Folder;

import fate.webapp.blog.utils.DateUtil;

public class Test {

	public static void main(String[] args) throws IOException {
		receive(true, "pop3", "pop.qq.com", 995, "534399332@qq.com",
				"hardy1847");
	}

	public static String receive(boolean ssl, String protocol, String host,
			int port, String username, String password) throws IOException {
		Properties props = new Properties();
		if (ssl) {
			// 使用ssl才要加
			Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
			props.setProperty("mail.pop3.socketFactory.class",
					"javax.net.ssl.SSLSocketFactory");
			props.setProperty("mail.pop3.socketFactory.fallback", "false");
		}
		Session session = Session.getDefaultInstance(props, null);
		session.setDebug(true);
		URLName urlName = new URLName(protocol, host, port, null, username, password);
		try {
			Store store = session.getStore(urlName);
			Folder folder = null;
			try {
				store.connect();
				folder = store.getFolder("INBOX");
				folder.open(Folder.READ_ONLY);
				POP3Folder inbox = (POP3Folder) folder; 
				Message[] messages = folder.getMessages();
				System.out.println(folder.getMessageCount());
				String userDir = "D://download/" + username + "/";
				for (Message message : messages) {
					
					System.out.println(inbox.getUID(message));
					Address[] recipients = message.getAllRecipients();
					StringBuilder recipient = new StringBuilder();
					// 有的邮件收件人是空
					if (recipients != null) {
						for (Address address : recipients) {
							recipient.append(((InternetAddress) address).getAddress()).append(";");
						}
					}
					InternetAddress address = (InternetAddress) message.getFrom()[0];
					// 美观用的设置发件人
					String personal = address.getPersonal();
					String sender = "<" + address.getAddress() + ">";
					if (personal == null) {
						personal = sender.substring(1, sender.indexOf("@"));
					}
					sender = personal + sender;
					String subject = message.getSubject();
					// 这个日期是空的，坑了
					// String time =
					// DateUtils.getDateTime(message.getReceivedDate());
					String time =  null;
					if(message.getSentDate()!=null)
						time = DateUtil.format(message.getSentDate(), "yyyy-MM-dd HH:mm");
					int size = message.getSize();
					StringBuilder content = new StringBuilder();
					StringBuilder attachment = new StringBuilder();
					String mailId = Integer
							.toString(message.getMessageNumber());
					if (message.isMimeType("text/*")) {
						content.append(message.getContent().toString());
					} else if (message.isMimeType("multipart/*")) {
						Multipart multipart = (Multipart) message.getContent();
						String attachmentDir = userDir + mailId + "/";
						for (int i = 0, len = multipart.getCount(); i < len; i++) {
							BodyPart bodyPart = multipart.getBodyPart(i);
							String disposition = bodyPart.getDisposition();
							if ((disposition != null)
									&& (disposition.equals(Part.ATTACHMENT) || disposition
											.equals(Part.INLINE))) {
								// 有的文件名是空，比如文件内容是图片
								if (bodyPart.getFileName() != null) {
									// 文件名被加密了，要解密
									String fileName = MimeUtility
											.decodeText(bodyPart.getFileName());
									// 文件名不能有冒号，故意选个特殊的字条来分隔
									attachment.append(":").append(fileName);
									FileUtils.copyInputStreamToFile(
											bodyPart.getInputStream(),
											new File(attachmentDir + fileName));
								} else {
									content.append(bodyPart.getContent()
											.toString());
								}
							} else {
								content.append(bodyPart.getContent().toString());
							}
						}
					}

					Map<String, Object> map = new HashMap<String, Object>();
					map.put("mailId", mailId);
					map.put("userId", username);
					map.put("recipient", recipient.toString());
					map.put("sender", sender);
					map.put("subject", subject);
					map.put("content", content.toString());
					map.put("time", time);
					map.put("size", size);
					map.put("attachment", attachment.length() > 0 ? attachment
							.substring(1).toString() : "");
					System.out.println(map);
					int i = 0;
					// 插入数据库
					i = 1;
					if (i == 0) {
						return "failure";
					}
				}
			} catch (AuthenticationFailedException e) {
				System.out.println(e);
				return "验证失败";
			} catch (MessagingException e) {
				e.printStackTrace();
				return "failure";
			} finally {
				try {
					if (folder != null) {
						folder.close(true);
					}
					store.close();
				} catch (MessagingException e) {
				}
			}
		} catch (NoSuchProviderException e1) {
			e1.printStackTrace();
			return "failure";
		}
		return "success";
	}
}
