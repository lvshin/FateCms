package fate.webapp.blog.api.open;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
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
import fate.webapp.blog.service.AdvertisementService;
import fate.webapp.blog.service.AnnouncementService;
import fate.webapp.blog.service.DuoShuoService;
import fate.webapp.blog.service.NaviService;
import fate.webapp.blog.service.ParamService;
import fate.webapp.blog.service.ThemeService;
import fate.webapp.blog.service.ThemeTagService;
import fate.webapp.blog.utils.DateUtil;
import fate.webapp.blog.utils.FilterHTMLTag;
import fate.webapp.blog.utils.QRUtil;
import fate.webapp.blog.utils.Strings;

@Controller
@RequestMapping("/op")
public class OpenCtl {

	private static final Logger LOG = Logger.getLogger(OpenCtl.class);
	
	@Autowired
	private ThemeService themeService;
	
	@Autowired
	private NaviService naviService;
	
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
		ModelAndView mv = new ModelAndView("base/header2");
		GlobalSetting globalSetting = GlobalSetting.getInstance();
		mv.addObject("qq", globalSetting.getQqAccess());
		mv.addObject("xinlang", globalSetting.getWeiboAccess());
		Index index = Index.getInstance();
		if(index.getNavis().isEmpty()){
			List<Navi> navis = naviService.searchRoot();
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			for (Navi n : navis) {
				list.add(naviToJson(n));
			}
			index.setNavis(list);
		}
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
		
		//在右侧显示与当前版块平级的版块
//		Forum forum = forumService.find(fid);
//		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//		if(forum!=null){//新创建的站点没有版块
//			mv.addObject("region", forum.getForumName());
//			for (Forum f : forum.getChildForums()) {
//				list.add(forumToJson(f));
//			}
//		}
		Index index = Index.getInstance();
		
		mv.addObject("announcements", index.getAnnouncements());
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
        }else{
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
			LOG.error("HTTP协议异常", e);
		} catch (IOException e) {
		    LOG.error("IO异常", e);
		} catch (JSONException e) {
		    LOG.error("JSON解析异常", e);
		}
		
		return mv;
	}
	
	@RequestMapping("/getQR")
	public void getQR(String url,HttpServletResponse response){
		int width = 250;
		int height = 250;
		// 二维码的图片格式
		String format = "gif";
		HashMap<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
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
		    LOG.error("二维码生成失败", e);
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
    public void duoshuo(String action, String signature, HttpServletRequest request){
        /*同步 */
        Index index = Index.getInstance();
        GlobalSetting globalSetting = GlobalSetting.getInstance();
        try {
            String sign = signature(paramToString(request), globalSetting.getDuoshuoSecret());
            if(sign.equals(signature)){
                syncFromDuoShuo(Long.parseLong(index.getLogId().getTextValue()));
            }
        } catch (InvalidKeyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
    
    /**
     * 多说签名
     * @param stringToSign
     * @param key
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws UnsupportedEncodingException
     */
    public String signature(String stringToSign, String key) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException{
        final String ALGORITHM = "HmacSHA1";
        final String ENCODING = "UTF-8";
        
        Mac mac = Mac.getInstance(ALGORITHM);
        mac.init(new SecretKeySpec(key.getBytes(ENCODING), ALGORITHM));
        byte[] signData = mac.doFinal(stringToSign.getBytes(ENCODING));
        
        return new String(Base64.encodeBase64(signData), ENCODING);
    }
    
    /**
     * 所有参数转换成一个字符串
     * @param request
     * @return
     */
    public String paramToString(HttpServletRequest request){
        StringBuffer sb = new StringBuffer();
        Enumeration<String> paramNames = request.getParameterNames();  
        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();  
            if(!"signature".equals(paramName)){
                 String[] paramValues = request.getParameterValues(paramName);
                 for(String s:paramValues){
                    sb.append(paramName).append("=").append(s).append("&");
                 }
            }
           
        }
        return sb.substring(0, sb.length()-1).toString();
    }
	
	public void syncFromDuoShuo(long sinceId){
		HttpClient client = new HttpClient();
		
		Param duoshuoKey = paramService.findByKey(Constants.DUOSHUO_KEY);
        Param duoshuoSecret = paramService.findByKey(Constants.DUOSHUO_SECRET);
		
		GetMethod method = new GetMethod("http://api.duoshuo.com/log/list.json?short_name="+duoshuoKey.getTextValue()+"&secret="+duoshuoSecret.getTextValue()+"&since_id="+sinceId);
		client.getParams().setContentCharset("UTF-8");
		method.setRequestHeader("ContentType", "application/x-www-form-urlencoded;charset=UTF-8");
		Index index = Index.getInstance();

		try {
			client.executeMethod(method);

			String submitResult = method.getResponseBodyAsString();

			JSONObject dataJson = new JSONObject(submitResult);
			
			if(dataJson.getInt("code")!=0){
				LOG.error(dataJson.getString("errorMessage"));
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
						duoShuo.setTheme(themeService.find(meta.getString("thread_key"), false));
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
							LOG.error("时间字符串转换异常", e);
						}
						duoShuo.setMessage(meta.getString("message"));
						duoShuo.setStatus(meta.getString("status"));
						String parentId = meta.getString("parent_id");
                        parentId = "null".equals(parentId)?"0":parentId;
                        if(Strings.isEmpty(parentId)){
                            duoShuo.setParentId(0);
                        }else{
                            duoShuo.setParentId(Long.parseLong(parentId));//parent_id突然出现了一个null
                        }
						duoShuo.setType(meta.getString("type"));
						duoShuo.setLastModify(new Date().getTime());
						duoShuo.setLogId(logId);
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
			LOG.error(e);
		} catch (JSONException e) {
		    LOG.error(e);
		}
	}
}
