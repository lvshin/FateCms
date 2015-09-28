package fate.webapp.blog.utils;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonDecoder {

	public static Map<String,Object> decode(String str){
		Map<String,Object> map = new HashMap<String, Object>();
		str=str.substring(1,str.length()-1);
		String[] objs = str.split(",");
		for(String s:objs){
			String[] tmp = s.split(":");
			String objName = tmp[0].substring(1,tmp[0].length()-1);
			map.put(objName, tmp[1]);
		}
		return map;
	}
	
	public static Map<String,Object> chatDecode(String str){
		
		Map<String,Object> map = new HashMap<String, Object>();
		
		try {
			JSONObject dataJson=new JSONObject(str);
			map.put("from", dataJson.get("from"));
			map.put("to", dataJson.get("to"));
			map.put("content", dataJson.get("content"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return map;
	}
}
