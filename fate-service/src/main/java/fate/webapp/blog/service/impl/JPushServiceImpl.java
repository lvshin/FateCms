package fate.webapp.blog.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;
import fate.webapp.blog.model.Param;
import fate.webapp.blog.service.JPushService;
import fate.webapp.blog.service.ParamService;
import fate.webapp.blog.utils.DateUtil;

public class JPushServiceImpl implements JPushService{
	
	@Autowired
	private ParamService paramService;
	
	private JPushClient jPushClient;
	
	public void init(){
		Param key = paramService.findByKey("jpush_key");
		Param secret = paramService.findByKey("jpush_secret");
		if(key!=null&&secret!=null)
			jPushClient = new JPushClient(secret.getTextValue(), key.getTextValue());
	}

	
	/**
	 * 点对点推送
	 * @param title 消息标题
	 * @param content 消息内容
	 * @param publishDate 发送时间
	 * @param id 事务id
	 * @param alias 接收者别名
	 * @return
	 */
	@Override
	public boolean pushAlias(String title, String content, Date publishDate, String id, String alias) {
		PushResult result;
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", id);
			map.put("content", content);
			map.put("title", title);
			map.put("type", "2");//通知
			map.put("time", DateUtil.format(publishDate, "yyyy年MM月dd日 HH:mm"));
			result = jPushClient.sendPush(PushPayload.newBuilder()
					.setPlatform(Platform.android_ios())
					.setAudience(Audience.alias(alias))
					.setNotification(Notification.ios(title, map))
					.build());
			return result.isResultOK();
		} catch (APIConnectionException | APIRequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 推送给所有人
	 * @param title 消息标题
	 * @param content 消息内容
	 * @param publishDate 发送时间
	 * @param id 公告id
	 * @return
	 */
	@Override
	public boolean pushAll(String title, String content, Date publishDate, String id) {
		PushResult result;
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", id);
			map.put("content", content);
			map.put("title", title);
			map.put("type", "1");//公告
			map.put("time", DateUtil.format(publishDate, "yyyy年MM月dd日 HH:mm"));
			result = jPushClient.sendPush(PushPayload.newBuilder()
					.setPlatform(Platform.android_ios())
					.setAudience(Audience.all())
					.setNotification(Notification.ios(title, map))
					.build());
			return result.isResultOK();
		} catch (APIConnectionException | APIRequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}


	/**
	 * 群组推送
	 * @param title 消息标题
	 * @param content 消息内容
	 * @param publishDate 发送时间
	 * @param id 事务id
	 * @param tag 群组标识
	 * @return
	 */
	@Override
	public boolean pushTag(String title, String content, Date publishDate,
			String id, String tag) {
		PushResult result;
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", id);
			map.put("content", content);
			map.put("title", title);
			map.put("type", "2");//通知
			map.put("time", DateUtil.format(publishDate, "yyyy年MM月dd日 HH:mm"));
			result = jPushClient.sendPush(PushPayload.newBuilder()
					.setPlatform(Platform.android_ios())
					.setAudience(Audience.tag(tag))
					.setNotification(Notification.ios(title, map))
					.build());
			return result.isResultOK();
		} catch (APIConnectionException | APIRequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}


}
