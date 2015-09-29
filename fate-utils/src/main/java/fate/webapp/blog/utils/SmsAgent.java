package fate.webapp.blog.utils;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

/**
 * 微米的短信接口
 * @author fate
 *
 */
public class SmsAgent {

	private SmsAgent() {
        super();
        // TODO Auto-generated constructor stub
    }

    public static Map<String, Object> sendSms(String mobiles,String code,int timeout) {

		Map<String, String> para = new HashMap<String, String>();
		
		Map<String, Object> result = new HashMap<String, Object>();

		/**
		 * 目标手机号码，多个以“,”分隔，一次性调用最多100个号码，示例：139********,138********
		 */
		para.put("mob", mobiles);

		/**
		 * 微米账号的接口UID
		 */
		para.put("uid", "");

		/**
		 * 微米账号的接口密码
		 */
		para.put("pas", "");

		/**
		 * 接口返回类型：json、xml、txt。默认值为txt
		 */
		para.put("type", "json");

		/**
		 * 短信模板cid，通过微米后台创建，由在线客服审核。必须设置好短信签名，签名规范： <br>
		 * 1、模板内容一定要带签名，<span class='label label-success'>签名放在模板内容的最前面</span>；<br>
		 * 2、签名格式：【***】，签名内容为三个汉字以上（包括三个）；<br>
		 * 3、短信内容不允许双签名，即短信内容里只有一个“【】”<br>
		 */
		para.put("cid", "");

		/**
		 * 传入模板参数。<br>
		 * <br>
		 * 短信模板示例：<br>
		 * 【微米网】您的验证码是：%P%，%P%分钟内有效。如非您本人操作，可忽略本消息。<br>
		 * <br>
		 * 传入两个参数：<br>
		 * p1：610912<br>
		 * p2：3<br>
		 * 最终发送内容：<br>
		 * 【微米网】您的验证码是：610912，3分钟内有效。如非您本人操作，可忽略本消息。
		 */
		para.put("p1", code);
		para.put("p2", ""+timeout);
		String res="";

		try {

			res = HttpClientHelper.convertStreamToString(HttpClientHelper.post("http://api.weimi.cc/2/sms/send.html", para),"UTF-8");
			JSONObject dataJson=new JSONObject(res);
			result.put("code", dataJson.get("code"));
			result.put("msg", dataJson.get("msg"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
