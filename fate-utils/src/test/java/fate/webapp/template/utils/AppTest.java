package fate.webapp.template.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import fate.webapp.blog.utils.Base64;


/**
 * Unit test for simple App.
 */
public class AppTest {
	public static void main(String[] args) throws ClientProtocolException, IOException, JSONException {
		
//		String url = "http://data.zz.baidu.com/urls?site=www.nanamizuki.cn&token=5aiVBRrTO9vgDdgQ";
//		
//		HttpClient client = new HttpClient(); 
//		PostMethod method = new PostMethod(url); 
//			
//		client.getParams().setContentCharset("UTF-8");
//		HostConfiguration host = new HostConfiguration();
//		host.setHost("data.zz.baidu.com ");
//		client.setHostConfiguration(host);
//		method.setRequestHeader("User-Agent","curl/7.12.1");
//		method.setRequestHeader("ContentType","text/plain");
//		String[] list = {"http://www.nanamizuki.cn/op/theme/2015/04/30/好好吃的样子～♪.html"};
//		NameValuePair[] data = {
//			new NameValuePair("\n", "http://www.nanamizuki.cn/op/theme/2015/04/30/好好吃的样子～♪.html"),
//		};
//		HttpMethodParams params = new HttpMethodParams();
//		params.setParameter("\n", list);
//		method.setRequestBody(data);
//		System.out.println(Arrays.toString(method.getRequestHeaders()));
//		
//		try {
//			System.out.println(client.executeMethod(method));	
//			
//			String SubmitResult =method.getResponseBodyAsString();
//					
//			System.out.println(SubmitResult);
//
//			
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} 	
		System.out.println(Base64.encode("0c5e44f1e36bed41b9c66ca211d36404".getBytes()));
		System.out.println(Arrays.toString(Base64.decode("j6cQY5pEVCVmEnTWW7lywpBtm0I=")));
	}
}