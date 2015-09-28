package fate.webapp.blog.utils;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

public class ClientInfo {

	/**
	 * 获取用户的IP
	 * @param request
	 * @return
	 */
	public static String getIp(HttpServletRequest request){
		String ip = request.getHeader("x-forwarded-for");
		 if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		  ip = request.getHeader("Proxy-Client-IP");
		 }
		 if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		  ip = request.getHeader("WL-Proxy-Client-IP");
		 }
		 if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		  ip = request.getHeader("http_client_ip");
		 }
		 if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		  ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		 }
		 if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			  ip = request.getRemoteAddr();
		 }
		// 如果是多级代理，那么取第一个ip为客户ip
		 if (ip != null && ip.indexOf(",") != -1) {
			  ip = ip.substring(ip.lastIndexOf(",") + 1, ip.length()).trim();
			 }
		return ip;
	}
	
	public static CityInfo getLocationByIp(String ip){
		String url = "http://ip.taobao.com/service/getIpInfo.php?ip="+ip;
		HttpClient client = new HttpClient();
		GetMethod method = new GetMethod(url);
		client.getParams().setContentCharset("UTF-8");
		method.setRequestHeader("ContentType", "application/x-www-form-urlencoded;charset=UTF-8");
		CityInfo cityInfo = null;
		try {
			client.executeMethod(method);

			String SubmitResult = method.getResponseBodyAsString();

			JSONObject dataJson = new JSONObject(SubmitResult);
			
			int code = dataJson.getInt("code");
			if(code==0){
				JSONObject data = dataJson.getJSONObject("data");
				Gson gson = new Gson();
				cityInfo = gson.fromJson(data.toString(), CityInfo.class);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cityInfo;
	}
	
}
