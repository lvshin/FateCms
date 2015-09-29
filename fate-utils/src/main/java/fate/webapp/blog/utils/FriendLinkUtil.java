package fate.webapp.blog.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class FriendLinkUtil {

	private FriendLinkUtil() {
        super();
        // TODO Auto-generated constructor stub
    }

    private static Logger log = Logger.getLogger(FriendLinkUtil.class.getName());
	
	/**
	 * 检测网站的友链是否互链
	 * @param url
	 * @return
	 */
	public static List<Map<String, Object>> checkLink(String url){
	    if(url==null)
	        return null;
		log.info("解析网站友链："+url);
	    List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
	    Document doc;
		try {
			
			doc = getConnection(url).get();
		        Elements elements = doc.getElementsByTag("a");
		        for(int i=0;i<elements.size();i++){
		        	Element element = elements.get(i);
		        	String siteUrl = element.attr("href");
		        	if(!siteUrl.contains(url)&&siteUrl.contains("http://")&&!siteUrl.contains("http://www.miitbeian.gov.cn")){
		        		Map<String, Object> map = new HashMap<String, Object>();
		        		String siteName = element.html();
		        		map.put("siteName", siteName);
		        		map.put("siteUrl", siteUrl);
		        		Document target = getConnection(siteUrl).get();
		        		map.put("has", target.toString().contains(url));
		        		list.add(map);
		        	}
		        }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("网站解析错误");
		}
	    return list;
	}
	
	public static Connection getConnection(String url){
		Connection con = Jsoup.connect(url);
		con.header("Accept-Encoding", "gzip, deflate, sdch");   
		con.header("Connection", "keep-alive");   
		con.header("Content-Language", "zh-CN,zh;q=0.8");
		con.header("Host", url); 
		con.header("Cache-Control", "max-age=0");
		con.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		con.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.152 Safari/537.36");
		return con;
	}
}
