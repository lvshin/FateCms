package fate.webapp.blog.admin.api.function;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.aliyun.oss.model.PutObjectResult;

import fate.webapp.blog.base.Constants;
import fate.webapp.blog.model.Aliyun;
import fate.webapp.blog.model.Forum;
import fate.webapp.blog.model.GlobalSetting;
import fate.webapp.blog.model.Index;
import fate.webapp.blog.model.Param;
import fate.webapp.blog.service.ForumService;
import fate.webapp.blog.service.OSSService;
import fate.webapp.blog.service.ParamService;

@Controller
@RequestMapping("/admin/forum")
public class AForumCtl {

	@Autowired
	private ForumService forumService;
	
	@Autowired
	private ParamService paramService;
	
	@Autowired
	private OSSService ossService;
	
	@RequestMapping("/forumList")
	public ModelAndView forumList(){
		ModelAndView mv = new ModelAndView("admin/function/forum/forumList");
		return mv;
	}
	
	/**
	 * 获取分区根节点
	 * @return
	 */
	@RequestMapping(value = "/getRoot", method = RequestMethod.POST)
	@ResponseBody
	public Object getRoot(){
		List<Forum> forums = forumService.searchRoot();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (Forum f : forums) {
			list.add(forumToJson(f));
		}
		return list;
	}
	
	/**
	 * 分区详细页
	 * @return
	 */
	@RequestMapping("/region")
	public ModelAndView region(int fid){
		ModelAndView mv = new ModelAndView("admin/function/forum/region");
		Forum forum = forumService.find(fid);
		if(forum.getType()!=Forum.TYPE_REGION){
			mv.addObject("success", false);
			mv.addObject("msg", "参数错误");
			return mv;
		}
		mv.addObject("success", true);
		mv.addObject("forum", forum);
		return mv;
	}
	
	@RequestMapping(value = "/editRegion", method = RequestMethod.POST)
	@ResponseBody
	public Object editRegion(int fid, String regionName, String title, String keywords, String description){
		Map<String, Object> map = new HashMap<String, Object>();
		Forum forum = forumService.find(fid);
		if(forum==null){
			map.put("success", false);
			map.put("msg", "分区不存在");
			return map;
		}
		forum.setForumName(regionName);
		forum.setTitle(title);
		forum.setKeywords(keywords);
		forum.setDescription(description);
		forumService.update(forum);
		map.put("success", true);
		map.put("msg", "保存成功");
		return map;
	}
	
	/**
	 * 分区详细页
	 * @return
	 */
	@RequestMapping("/forum")
	public ModelAndView forum(int fid){
		ModelAndView mv = new ModelAndView("admin/function/forum/forum");
		Forum forum = forumService.find(fid);
		if(forum.getType()==Forum.TYPE_REGION){
			mv.addObject("success", false);
			mv.addObject("msg", "参数错误");
			return mv;
		}
		List<Forum> forums = forumService.searchRoot();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (Forum f : forums) {
			list.add(forumToJson(f));
		}
		mv.addObject("forums", list);
		mv.addObject("success", true);
		mv.addObject("forum", forum);
		return mv;
	}
	
	@RequestMapping(value = "/editForum", method = RequestMethod.POST)
	@ResponseBody
	public Object editForum(int fid, String forumName, String title, String keywords, String description, String forumIcon, String iconWidth,String forumDesc, int parentFid){
		Map<String, Object> map = new HashMap<String, Object>();
		Forum forum = forumService.find(fid);
		if(forum==null){
			map.put("success", false);
			map.put("msg", "版块不存在");
			return map;
		}
		Forum parent = forumService.find(parentFid);
		if(parent==null){
			map.put("success", false);
			map.put("msg", "父级版块不存在");
			return map;
		}
		forum.setForumName(forumName);
		forum.setTitle(title);
		forum.setKeywords(keywords);
		forum.setDescription(description);
		forum.setForumIcon(forumIcon);
		forum.setIconWidth(iconWidth);
		forum.setForumDesc(forumDesc);
		forum.setParentForum(parent);
		if(parent.getType()==Forum.TYPE_REGION)
			forum.setType(Forum.TYPE_FORUM);
		else if(parent.getType()==Forum.TYPE_FORUM){
			forum.setType(Forum.TYPE_SUB);
		}
		forumService.update(forum);
		Index index = Index.getInstance();
		List<Forum> forums = forumService.searchRoot();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list.add(forumToJson(forums.get(2)));
		index.setList(list);
		map.put("success", true);
		map.put("msg", "保存成功");
		return map;
	}
	
	@RequestMapping(value = "/uploadIcon", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> uploadIcon(MultipartFile icon, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		String oraginal = icon.getOriginalFilename();
		String filename = "forum_icon_"+(ossService.count("site/")+1)+(oraginal.substring(oraginal.lastIndexOf(".")));
		GlobalSetting globalSetting = (GlobalSetting) request.getSession().getAttribute("setting");
		Param ossBucket = paramService.findByKey(Constants.OSS_BUCKET);
		Param ossUrl = paramService.findByKey(Constants.OSS_URL);
		Param ossEndpoint = paramService.findByKey(Constants.OSS_ENDPOINT);
		String dir = "site/";
		if (globalSetting.getAliyunUsed()) {
			// 上传到OSS
			
			PutObjectResult result = ossService.simpleUpload(ossBucket.getTextValue(),
					icon, dir, filename);// 会自动关闭流？
			if (result == null) {
				map.put("success", false);
				map.put("msg", "上传错误");
				return map;
			}
		} else {
			// 上传到本地
		}

		String url = null;
		if (globalSetting.getAliyunUsed())
			url = "http://"
					+ (ossUrl == null ? ossBucket.getTextValue()
							+ "." + ossEndpoint.getTextValue() : ossUrl.getTextValue() + "/" + dir + filename);
		else
			url = request.getContextPath() + "/file/getfile/"
					+ ossBucket.getTextValue() + "/" + dir + filename;
		map.put("url", url);
		map.put("success", true);

		return map;
	}
	
	public Map<String, Object> forumToJson(Forum forum) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("forumName", forum.getForumName());
		map.put("type", forum.getType());
		map.put("fid", forum.getFid());
		map.put("forumOrder", forum.getForumOrder());
		map.put("parentFid", forum.getParentForum()==null?0:forum.getParentForum().getFid());
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if (forum.getChildForums() != null && forum.getChildForums().size() > 0) {

			for (Forum f : forum.getChildForums()) {
				list.add(forumToJson(f));
			}
		}
		map.put("children", list);
		return map;
	}
	
	@RequestMapping(value = "/addForum", method = RequestMethod.POST)
	@ResponseBody
	public Object addForum(String result){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			JSONArray array = new JSONArray(result);
			for(int i=0;i<array.length();i++){
				JSONObject obj = (JSONObject) array.get(i);
				int fid = obj.getInt("fid");
				int parentFid = obj.getInt("parentFid");
				int forumType = obj.getInt("forumType");
				int forumOrder = obj.getInt("forumOrder");
				String forumName = obj.getString("forumName");
				Forum forum = forumService.find(fid);
				if(forum==null){
					if(!forumService.checkForumName(forumName)){
						forum = new Forum();
						forum.setType(forumType);
						if(parentFid!=0){
							Forum parent = forumService.find(parentFid);
							forum.setParentForum(parent);
						}
					}else{
						map.put("success", false);
						map.put("msg", "版块名称已存在");
					}
				}
				
				forum.setForumName(forumName);
				forum.setForumOrder(forumOrder);
				forumService.update(forum);
				}
			map.put("success", true);
		} catch (JSONException e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("msg", "未知错误");
		}
		return map;
	}
	
	@RequestMapping("/delForum")
	@ResponseBody
	public Object delForum(int fid){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			forumService.delete(forumService.find(fid));
			map.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("msg", "未知错误");
		}
		return map;
	}
}
