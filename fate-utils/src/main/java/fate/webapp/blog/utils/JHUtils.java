package fate.webapp.blog.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 聚合数据工具类
 * 
 * @author Fate
 * 
 */
public class JHUtils {

	private JHUtils() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
	 * 短信发送
	 * 
	 * @param mobile
	 *            手机号
	 * @param code
	 *            验证码
	 * @param timeout
	 *            超时时间，分钟
	 * @param templeteId
	 *            短信模版id
	 * @return 错误码
	 */
	public static Map<String, Object> sendSms(String mobile, String code, int timeout,
			int templeteId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			NameValuePair[] data = {
					new NameValuePair("mobile", mobile),
					new NameValuePair("tpl_id", "2556"),
					new NameValuePair("tpl_value", "#code#=" + code + "&#hour#="
							+ timeout),
					new NameValuePair("key", "03abf4264d5bc4b5bf7f927857b85b2f") };
			
			JSONObject dataJson = sendToJH("http://v.juhe.cn/sms/send", data);
			int error_code = dataJson.getInt("error_code");
			String reason = dataJson.getString("reason");
			System.out.println(reason);
			map.put("success", error_code==0);
			map.put("msg", reason);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 天气查询
	 * @param ip
	 * @return
	 * @throws JSONException 
	 */
	public static Map<String, Object> weather(String ip) {
		Map<String, Object> map = new HashMap<String, Object>();
		NameValuePair[] data = {
				new NameValuePair("ip", ip),
				new NameValuePair("key", "f2f2be1d2581c633dbff0ed0099d4f6a") };
		
		JSONObject dataJson = sendToJH("http://v.juhe.cn/weather/ip", data);
		try {
			String reason = dataJson.getString("reason");
			int resultCode = dataJson.getInt("resultcode");
			JSONObject result = dataJson.getJSONObject("result");
			JSONObject sk = result.getJSONObject("sk");
			String temp = sk.getString("temp");
			String wind_direction = sk.getString("wind_direction");
			String wind_strength = sk.getString("wind_strength");
			String humidity = sk.getString("humidity");
			JSONObject today = result.getJSONObject("today");
			String city = today.getString("city");
			map.put("temp", temp);
			map.put("wind_direction", wind_direction);
			map.put("wind_strength", wind_strength);
			map.put("humidity", humidity);
			map.put("city", city);
			map.put("reason", reason);
			map.put("resultCode", resultCode);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			try {
				String reason = dataJson.getString("reason");
				String error_code = dataJson.getString("error_code");
				map.put("reason", reason);
				map.put("error_code", error_code);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		return map;
	}

	private static JSONObject sendToJH(String url, NameValuePair[] data) {
		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod(url);
		client.getParams().setContentCharset("UTF-8");
		method.setRequestHeader("ContentType", "application/x-www-form-urlencoded;charset=UTF-8");

		method.setRequestBody(data);

		try {
			client.executeMethod(method);

			String SubmitResult = method.getResponseBodyAsString();

			System.out.println(SubmitResult);

			JSONObject dataJson = new JSONObject(SubmitResult);
			

			return dataJson;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
