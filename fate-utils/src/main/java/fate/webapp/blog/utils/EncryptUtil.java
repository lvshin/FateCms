package fate.webapp.blog.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class EncryptUtil {

	private EncryptUtil() {
        super();
        // TODO Auto-generated constructor stub
    }

    // md5加密  
    public static String md5(String inputText) {  
        return encrypt(inputText, "md5");  
    }  
  
    // sha加密  
    public static String sha(String inputText) {  
        return encrypt(inputText, "sha-1");  
    }  
    
    //对密码加密 
    public static String pwd(Date date, String pwd){
		long time = date.getTime();
		//mysql里的时间戳精确到秒，按问题现状来看可能有四舍五入，所以这里要多除10
		time = time/10000;
		return EncryptUtil.md5(pwd+time);
	}
  
    /** 
     * md5或者sha-1加密 
     *  
     * @param inputText 
     *            要加密的内容 
     * @param algorithmName 
     *            加密算法名称：md5或者sha-1，不区分大小写 
     * @return 
     */  
    private static String encrypt(String inputText, String algorithmName) {  
        if (inputText == null || "".equals(inputText.trim())) {  
            throw new IllegalArgumentException("请输入要加密的内容");  
        }  
        if (algorithmName == null || "".equals(algorithmName.trim())) {  
            algorithmName = "md5";  
        }  
        String encryptText = null;  
        try {  
            MessageDigest m = MessageDigest.getInstance(algorithmName);  
            m.update(inputText.getBytes("UTF8"));  
            byte s[] = m.digest();  
            // m.digest(inputText.getBytes("UTF8"));  
            return hex(s);  
        } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
        return encryptText;  
    }  
  
    // 返回十六进制字符串  
    private static String hex(byte[] arr) {  
        StringBuffer sb = new StringBuffer();  
        for (int i = 0; i < arr.length; ++i) {  
            sb.append(Integer.toHexString((arr[i] & 0xFF) | 0x100).substring(1,  
                    3));  
        }  
        return sb.toString();  
    }  
    
}
