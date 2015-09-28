package fate.webapp.blog.api.open;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import fate.webapp.blog.base.Constants;
import fate.webapp.blog.listener.SiteStatistics;
import fate.webapp.blog.model.Aliyun;
import fate.webapp.blog.model.DuoShuo;
import fate.webapp.blog.model.Forum;
import fate.webapp.blog.model.GlobalSetting;
import fate.webapp.blog.model.Index;
import fate.webapp.blog.model.Navi;
import fate.webapp.blog.model.Param;
import fate.webapp.blog.model.Theme;
import fate.webapp.blog.model.ThirdPartyAccess;
import fate.webapp.blog.service.AdvertisementService;
import fate.webapp.blog.service.AnnouncementService;
import fate.webapp.blog.service.DuoShuoService;
import fate.webapp.blog.service.ForumService;
import fate.webapp.blog.service.JPushService;
import fate.webapp.blog.service.NaviService;
import fate.webapp.blog.service.ParamService;
import fate.webapp.blog.service.ThemeService;
import fate.webapp.blog.service.ThemeTagService;
import fate.webapp.blog.service.ThirdPartyAccessService;
import fate.webapp.blog.service.UserSessionService;
import fate.webapp.blog.utils.DateUtil;
import fate.webapp.blog.utils.FilterHTMLTag;
import fate.webapp.blog.utils.QRUtil;

@Controller
@RequestMapping("/op")
public class OpenCtl {

	private Logger log = Logger.getLogger(OpenCtl.class);
	
	@Autowired
	private UserSessionService userSessionService;
	
	@Autowired
	private ThirdPartyAccessService thirdPartyAccessService;
	
	@Autowired
	private ForumService forumService;
	
	@Autowired
	private ThemeService themeService;
	
	@Autowired
	private NaviService naviService;
	
	@Autowired
	private JPushService jPushService;
	
	@Autowired
	private ParamService paramService;
	
	@Autowired
	private AnnouncementService announcementService;
	
	@Autowired
	private AdvertisementService advertisementService;
	
	@Autowired
	private DuoShuoService duoShuoService;
	
	@Autowired
	private ThemeTagService themeTagService;
	
	@RequestMapping("/footer")
	public ModelAndView footer(){
		ModelAndView mv = new ModelAndView("base/footer");
		Calendar cal = Calendar.getInstance();
		mv.addObject("year", cal.get(Calendar.YEAR));
		Index index = Index.getInstance();
		mv.addObject("adv", index.getAdvBottom());
		return mv;
	}
	
	@RequestMapping("/head")
	public String head(){
		return "base/head";
	}
	
	@RequestMapping("/header")
	public ModelAndView header(HttpServletRequest request){
//		String ip = ClientInfo.getIp(request);
//		ip = "120.195.15.202";//测试用
		ModelAndView mv = new ModelAndView("base/header2");
		GlobalSetting globalSetting = GlobalSetting.getInstance();
		mv.addObject("qq", globalSetting.getQqAccess());
		mv.addObject("xinlang", globalSetting.getWeiboAccess());
		Index index = Index.getInstance();
		if(index.getNavis().size()==0){
			List<Navi> navis = naviService.searchRoot();
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			for (Navi n : navis) {
				list.add(naviToJson(n));
			}
			index.setNavis(list);
		}
//		mv.addObject("weather", JHUtils.weather(ip));
		mv.addObject("navis", index.getNavis());
		return mv;
	}
	
	public Map<String, Object> naviToJson(Navi navi){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", navi.getId());
		map.put("name", navi.getName());
		map.put("url", navi.getUrl());
		map.put("type", navi.getType());
		map.put("order", navi.getOrder());
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String childUrl = "";
		for(Navi n:navi.getChilds()){
			list.add(naviToJson(n));
			childUrl += n.getUrl()+",";
		}
		map.put("childUrl", childUrl);
		map.put("children", list);
		return map;
	}
	
	@RequestMapping("/accessDenied")
	public ModelAndView accessDenied(String message){
		ModelAndView mv = new ModelAndView("login/accessDenied");
		mv.addObject("message", message);
		return mv;
	}
	
	/**
	 * 右侧导航
	 * @param fid 版块id
	 * @return
	 */
	@RequestMapping("/rightNavi")
	public ModelAndView rightNavi(int fid, String url){
		ModelAndView mv = new ModelAndView("base/rightNavi");
//		Forum forum = forumService.find(fid);
//		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//		if(forum!=null){//新创建的站点没有版块
//			mv.addObject("region", forum.getForumName());
//			for (Forum f : forum.getChildForums()) {
//				list.add(forumToJson(f));
//			}
//		}
		Index index = Index.getInstance();
		
		mv.addObject("announcement", index.getAnnouncement());
		mv.addObject("advertisement", index.getAdvRight());
		mv.addObject("online", SiteStatistics.getOnline());
		mv.addObject("updateTime", SiteStatistics.getUpdateTime());
		mv.addObject("theme", SiteStatistics.getTheme());
		mv.addObject("user", SiteStatistics.getUser());
		mv.addObject("comment", SiteStatistics.getComment());
		mv.addObject("views", SiteStatistics.getViews());
		mv.addObject("history_online", SiteStatistics.getHistrry_online());
		mv.addObject("search", SiteStatistics.getSearch());
//		mv.addObject("forums", list);
		mv.addObject("hot", index.getHot());
		mv.addObject("searchHot", index.getSearchHot());
		mv.addObject("tags", themeTagService.random(30));
		return mv;
	}
	
	@RequestMapping("/search")
	public ModelAndView search(String keyword, @RequestParam(defaultValue = "1")int curPage){
		ModelAndView mv = new ModelAndView("search/result");
		/*本地计数操作*/
		Param search = paramService.findByKey(Constants.SEARCH_COUNT);
        if(search!=null){
        	search.setIntValue(search.getIntValue()+1);
        }
        else{
        	search = new Param();
        	search.setKey(Constants.SEARCH_COUNT);
        	search.setIntValue(0);
        	search.setType(Param.TYPE_INT);
        }
        search = paramService.update(search);
        SiteStatistics.setSearch(search.getIntValue());

		/*本地计数操作*/
		
		try {
			String res = Aliyun.getInstance().search(keyword, curPage, 10);
			JSONObject data = new JSONObject(res);
			String status = data.getString("status");
			JSONObject result = data.getJSONObject("result");
			double searchTime = result.getDouble("searchtime");
			long total = result.getLong("total");
			long num = result.getLong("num");
			long viewTotal = result.getLong("viewtotal");
			JSONArray items = result.getJSONArray("items");
			List<Theme> themes = new ArrayList<Theme>();
			for(int i=0;i<items.length();i++){
				Theme theme = new Theme();
				JSONObject obj = items.getJSONObject(i);
				theme.setGuid(obj.getString("guid"));
				theme.setAuthor(obj.getString("author"));
				theme.setPublishDate(new Date(obj.getLong("publish_date")));
				theme.setPriority(obj.getInt("priority"));
				theme.setReplies(obj.getInt("replies"));
				theme.setTags(obj.getString("tags"));
				theme.setTitle(obj.getString("title"));
				theme.setUrl(obj.getString("url"));
				theme.setViews(obj.getInt("views"));
				theme.setContent(FilterHTMLTag.delHTMLTag(obj.getString("content")));
				themes.add(theme);
			}
			mv.addObject("status", status);
			mv.addObject("themes", themes);
			mv.addObject("searchTime", searchTime);
			mv.addObject("count", total);
			mv.addObject("curPage", curPage);
			mv.addObject("pageSize", 10);
			mv.addObject("keyword", keyword);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return mv;
	}
	
	@RequestMapping("/getQR")
	public void getQR(String url,HttpServletResponse response){
		int width = 250;
		int height = 250;
		// 二维码的图片格式
		String format = "gif";
		Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
		// 内容所使用编码
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		hints.put(EncodeHintType.MARGIN, 1);//二维码周围的空白部分大小
		BitMatrix bitMatrix;
		try {
			bitMatrix = new MultiFormatWriter().encode(url,
					BarcodeFormat.QR_CODE, width, height, hints);
			URL file = OpenCtl.class.getClassLoader().getResource("log4j.properties");
			String path = file.getPath();
			path = path.substring(0, path.indexOf("WEB-INF"));
			path = path.replaceAll("%20", " ");
			path += "images/logo_white.png";
			QRUtil.writeToStream(bitMatrix, format, response.getOutputStream(),null);
		} catch (WriterException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 对Forum做处理，取出所需数据，转为json
	 * @param forum
	 * @return
	 */
	public Map<String, Object> forumToJson(Forum forum) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("forumName", forum.getForumName());
		map.put("fid", forum.getFid());
		if(forum.getType()!=Forum.TYPE_REGION){
			long count = themeService.count(forum.getFid(), false, Theme.STATE_PUBLISH);
			map.put("count", count);
		}
//		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//		if (forum.getChildForums() != null && forum.getChildForums().size() > 0) {
//			for (Forum f : forum.getChildForums()) {
//				list.add(forumToJson(f));
//			}
//		}
//		map.put("children", list);
		return map;
	}
	
	@RequestMapping("/loading")
	public ModelAndView loading(int type){
		ModelAndView mv = new ModelAndView("base/loading"+type);
		return mv;
	}
	
	@RequestMapping("/getSuggest")
	@ResponseBody
	public Object getSuggest(String query){
		return Aliyun.getInstance().suggest(query);
	}
	
	@RequestMapping("/duoshuo")
	public void duoshuo(String action, String signature){
		/*同步 */
		Index index = Index.getInstance();
		if(signature.equals("j6cQY5pEVCVmEnTWW7lywpBtm0I="))
			SyncFromDuoShuo(Long.parseLong(index.getLogId().getTextValue()));
	}
	
	public void SyncFromDuoShuo(long since_id){
		HttpClient client = new HttpClient();
		
		Param duoshuoKey = paramService.findByKey(Constants.DUOSHUO_KEY);
        Param duoshuoSecret = paramService.findByKey(Constants.DUOSHUO_SECRET);
		
		GetMethod method = new GetMethod("http://api.duoshuo.com/log/list.json?short_name="+duoshuoKey.getTextValue()+"&secret="+duoshuoSecret.getTextValue()+"&since_id="+since_id);
		client.getParams().setContentCharset("UTF-8");
		method.setRequestHeader("ContentType", "application/x-www-form-urlencoded;charset=UTF-8");
		Index index = Index.getInstance();

		try {
			client.executeMethod(method);

			String SubmitResult = method.getResponseBodyAsString();

			JSONObject dataJson = new JSONObject(SubmitResult);
			
			if(dataJson.getInt("code")!=0){
				System.out.println(dataJson.getString("errorMessage"));
				log.error(dataJson.getString("errorMessage"));
			}else{
				JSONArray response = dataJson.getJSONArray("response");
				for(int i=0;i<response.length();i++){
					JSONObject res = response.getJSONObject(i);
					long logId = Long.parseLong(res.getString("log_id"));
					int userId = res.getInt("user_id");
					String action = res.getString("action");
					switch (action) {
					case "create":
						JSONObject meta = res.getJSONObject("meta");
						DuoShuo duoShuo = new DuoShuo();
						duoShuo.setPostId(Long.parseLong(meta.getString("post_id")));
						duoShuo.setThreadId(Long.parseLong(meta.getString("thread_id")));
						duoShuo.setTheme(themeService.find(meta.getString("thread_key")));
						duoShuo.setAuthorId(meta.getInt("author_id"));
						duoShuo.setAuthorNmae(meta.getString("author_name"));
						duoShuo.setAuthorEmail(meta.getString("author_email"));
						duoShuo.setAuthorUrl(meta.getString("author_url"));
						duoShuo.setAuthorKey(meta.getString("author_key"));
						duoShuo.setIp(meta.getString("ip"));
						try {
							String date = meta.getString("created_at");
							date = date.substring(0,date.lastIndexOf("+")).replace("T", " ");
							duoShuo.setCreatedAt(DateUtil.parse(date, "yyyy-MM-dd HH:mm:ss"));
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						duoShuo.setMessage(meta.getString("message"));
						duoShuo.setStatus(meta.getString("status"));
						duoShuo.setParentId(Long.parseLong(meta.getString("parent_id")));
						duoShuo.setType(meta.getString("type"));
						duoShuo.setLastModify(new Date().getTime());
						duoShuo.setLogId(logId);
						System.out.println(duoShuo);
						duoShuoService.save(duoShuo);
						break;
					case "approve":
					case "spam":
					case "delete":
						JSONArray meta2 = res.getJSONArray("meta");
						String ids = meta2.toString();
						ids = ids.substring(1, ids.length()-1).replace("\"", "");
						duoShuoService.update(ids, action, logId);
						break;
					default:
						JSONArray meta3 = res.getJSONArray("meta");
						String ids2 = meta3.toString();
						ids2 = ids2.substring(1, ids2.length()-1).replace("\"", "");
						duoShuoService.delete(ids2);
						break;
					}
					index.getLogId().setTextValue(""+logId);
				}
				paramService.update(index.getLogId());
				Map<Integer,List<Theme>> list = new HashMap<Integer, List<Theme>>();
				List<Theme> themes = themeService.pageByFid(0, Constants.INDEX_LIST_LENGTH, 1, false, true, false, Theme.STATE_PUBLISH);
					
				for(Theme theme:themes){
					String c = FilterHTMLTag.delHTMLTag(theme.getContent());
					theme.setContent((c.length()>200?c.substring(0, 200):c)+"...");
					theme.setReplies(theme.getDuoShuos().size());
				}
				list.put(1, themes);
				index.setThemes(list);
			}


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
