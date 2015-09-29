package fate.webapp.blog.admin.api.aliyun;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.aliyun.oss.model.PutObjectResult;

import fate.webapp.blog.base.Constants;
import fate.webapp.blog.model.Aliyun;
import fate.webapp.blog.model.GlobalSetting;
import fate.webapp.blog.model.Param;
import fate.webapp.blog.service.OSSService;
import fate.webapp.blog.service.ParamService;
import fate.webapp.blog.utils.DateUtil;
import fate.webapp.blog.utils.Strings;

@Controller
@RequestMapping("/file")
public class FileCtl {

    private static final Logger log = Logger.getLogger(FileCtl.class);
    
	// 设置每块为 200K
	final long partSize1 = 1024 * 200;

	@Autowired
	private ParamService paramService;

	@Autowired
	private OSSService ossService;


	@RequestMapping("/uploadPage")
	public ModelAndView uploadPage(String bucketName) {
		ModelAndView mv = new ModelAndView("admin/fileManager/upload");
		mv.addObject("bucketName", bucketName);
		return mv;
	}

	// @RequestMapping("/uploadFileName")
	// @ResponseBody
	// public Object uploadFileName(String fileName,HttpSession session){
	// @SuppressWarnings("unchecked")
	// List<ProgressEntity> list = (List<ProgressEntity>)
	// session.getAttribute("progressList");
	// ProgressEntity progressEntity = new ProgressEntity();
	// fileName = fileName.substring(fileName.lastIndexOf("\\")+1);
	// progressEntity.setFileName(fileName);
	// if(list==null)
	// {
	// list = new ArrayList<ProgressEntity>();
	// session.setAttribute("progressList", list);
	// }
	// list.add(progressEntity);
	// Map<String,Object> map = new HashMap<String, Object>();
	// map.put("listAll", list.size());
	// return map;
	// }

	/**
	 * 富文本编辑器对应的文件上传
	 * 
	 * @param uploadFile
	 * @param dir
	 * @param request
	 * @return
	 */
	@RequestMapping("/uploadImg")
	@ResponseBody
	public Map<String, Object> uploadImg(
			@RequestParam("imgFile") MultipartFile uploadFile, String dir,
			HttpServletRequest request) {
		System.out.println("in");
		Map<String, Object> map = new HashMap<String, Object>();
		String filename = uploadFile.getOriginalFilename();
		GlobalSetting globalSetting = (GlobalSetting) request.getSession()
				.getAttribute("setting");
		Calendar cal = Calendar.getInstance();
		Date now = cal.getTime();
		String time = DateUtil.format(now, "yyyyMM/dd");
		dir = "forum/" + time + "/" + dir + "/";
		Param ossBucket = paramService.findByKey(Constants.OSS_BUCKET);
		if (globalSetting.getAliyunUsed()) {
			// 上传到OSS
			PutObjectResult result = ossService.simpleUpload(ossBucket.getTextValue(), uploadFile, dir, filename);// 会自动关闭流？
			if (result == null) {
				map.put("error", 1);
				map.put("message", "上传错误");
				return map;
			}
		} else {
			// 上传到本地
		}

		String url = null;
		Param OSSUrl = paramService.findByKey(Constants.OSS_URL);
		Param OSSEndpoint = paramService.findByKey(Constants.OSS_ENDPOINT);
		if (globalSetting.getAliyunUsed()) 
			url = "http://"
					+ (OSSUrl == null||OSSUrl.getTextValue().equals("") ? ossBucket.getTextValue()
							+ "." + OSSEndpoint.getTextValue() : OSSUrl.getTextValue()) + "/" + dir + filename;
		else
			url = request.getContextPath() + "/file/getfile/"
					+ ossBucket.getTextValue() + "/" + dir + filename;
		map.put("url", url);
		map.put("error", 0);

		return map;
	}

	@RequestMapping("/upload")
	public Map<String, Object> upload(String bucketName,
			@RequestParam("uploadFile") MultipartFile uploadFile,
			String folder, HttpSession session, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		String key = uploadFile.getOriginalFilename();
		if (uploadFile.getSize() > partSize1) {
			map = ossService.multipartUpload(bucketName, uploadFile, folder);
		} else {
			PutObjectResult result = ossService.simpleUpload(bucketName, uploadFile, folder, key);
			if (result == null) {
				map.put("success", false);
				map.put("message", "上传错误");
			} else {
				map.put("success", true);
			}
		}

		return map;
	}

	// @RequestMapping("/getUploadProgress")
	// @ResponseBody
	// public Object getUploadProgress(String bucketName, String key,
	// HttpSession session) {
	// @SuppressWarnings("unchecked")
	// List<ProgressEntity> list = (List<ProgressEntity>) session
	// .getAttribute("progressList");
	// for (int i = 0; i < list.size(); i++) {
	// ListPartsRequest listPartsRequest = new ListPartsRequest(list
	// .get(i).getBucketName(), list.get(i).getFileName(), list
	// .get(i).getUploadId());
	//
	// // 获取上传的所有Part信息
	// PartListing partListing = client.listParts(listPartsRequest);
	//
	// // 遍历所有Part
	// for (PartSummary part : partListing.getParts()) {
	// System.out.println("PartNumber: " + part.getPartNumber()
	// + " ETag: " + part.getETag());
	// }
	//
	// int percent = (int) Math
	// .round(100.00 * partListing.getParts().size()
	// / list.get(i).getPartsAll());
	// list.get(i).setPartsRead(partListing.getParts().size());
	// }
	// return list;
	// }

	/**
	 * 生成链接
	 * @param bucketName
	 * @param key
	 * @param timeout
	 * @param timeType
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/getFileTempUrl")
	@ResponseBody
	public Object getFileTempUrl(String bucketName, String key, int timeout,
			int timeType){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
            key = java.net.URLDecoder.decode(key, "utf-8");
            key = key.replace("|", "/").replace("*", "+");
            switch (timeType) {
            case 1:
                timeout *= 60;
                break;
            case 2:
                timeout *= 3600;
                break;
            case 3:
                timeout *= (24 * 3600);
                break;
            default:
                break;
            }
            // 设置URL过期时间为1小时
            Date expiration = new Date(new Date().getTime() + timeout * 1000);

            // 生成URL
            String url = ossService.generatePresignedUrl(key, expiration).toString();
            //全部转为公网地址
            url = url.replace("-internal", "");
            map.put("url", url);
        } catch (UnsupportedEncodingException e) {
            log.error("无效的编码");
        }
		
		return map;
	}

	@RequestMapping(value = "/deleteObject", method = RequestMethod.POST)
	@ResponseBody
	public Object deleteObject(String key) {
		return ossService.deleteObject(key);
	}

	@RequestMapping("/batchDeleteObject")
	@ResponseBody
	public Object batchDeleteObject(String keys) {
		String[] keyList = keys.split("[*]");
		Map<String, Object> map = new HashMap<String, Object>();
		for (String key : keyList) {
			Map<String, Object> result = ossService.deleteObject(
					key);
			if (!(boolean) result.get("success")) {
				map.put("success", false);
				map.put("message", result.get("message"));
				break;
			} else {
				map.put("success", true);
			}
		}
		return map;
	}

	@RequestMapping("/newFolder")
	@ResponseBody
	public Object newFolder(String folderName, String curFolder) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success",
				ossService.newFolder(folderName, curFolder));
		return map;
	}

	
	/**
	 * 配合kindeditor的文件列表
	 * @param dir
	 * @param order
	 * @param path
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/filelist")
	@ResponseBody
	public Object filelist(@RequestParam(defaultValue="") String dir,String order,String path, HttpServletRequest request) throws UnsupportedEncodingException{
		Map<String, Object> map = new HashMap<String, Object>();
		if(!path.equals("")&&!path.endsWith("/"))
			path += "/";
		path = java.net.URLDecoder.decode(path, "utf-8");
		List<String> pathList = new ArrayList<String>();
		List<String> parentpathList = new ArrayList<String>();
		String[] paths = path.split("/");
		System.out.println(Arrays.toString(paths));
		pathList.add("");
		if(!path.equals(""))
		for(int i=0;i<paths.length;i++){
			pathList.add(paths[i]);
			parentpathList.add(path.substring(0,path.indexOf(paths[i])));
		}
		ObjectListing listing = ossService.getList(path);
		
		List<Hashtable<String, Object>> list = getFileList(path, "", listing, request);
		map.put("current_dir_path", path);
		map.put("moveup_dir_path", parentpathList.size()>0?parentpathList.get(parentpathList.size()-1):"");
		map.put("file_list", list);
		Param OSSUrl = paramService.findByKey(Constants.OSS_URL);
		Param OSSEndpoint = paramService.findByKey(Constants.OSS_ENDPOINT);
		Param ossBucket = paramService.findByKey(Constants.OSS_BUCKET);
		String url = "http://"
					+ (OSSUrl == null||OSSUrl.getTextValue().equals("") ? ossBucket.getTextValue()
							+ "." + OSSEndpoint.getTextValue() : OSSUrl.getTextValue()) + "/" + path;
		map.put("current_url", url);
		map.put("total_count", list.size());
		return map;
	}
	
	public List<Hashtable<String, Object>> getFileList(String dir, String keyword, ObjectListing listing, HttpServletRequest request) throws UnsupportedEncodingException{
		String[] fileTypes = new String[]{"gif", "jpg", "jpeg", "png", "bmp"};
		List<Hashtable<String, Object>> list = new ArrayList<Hashtable<String, Object>>();
		//获取文件列表
		List<OSSObjectSummary> objList = listing.getObjectSummaries();
		//获取目录列表
		List<String> dirList = listing.getCommonPrefixes();
		//排序，文件夹在前
		Collections.sort(objList, (os1,os2)->{
			if(os1.getKey().endsWith("/")&&os2.getKey().endsWith("/"))
				return os1.getKey().compareTo(os2.getKey());
			else
				if(os1.getKey().endsWith("/"))
					return -1;
				else if(os2.getKey().endsWith("/"))
					return 1;
				else
					return os1.getKey().toLowerCase().compareTo(os2.getKey().toLowerCase());
		});
		//排序按中文顺序
		Collections.sort(dirList, (a,b)->Strings.getHexString(a, "gb2312").compareTo(Strings.getHexString(b, "gb2312")));
		
		
		//遍历文件夹
		for(String dir2:dirList){
			if(keyword==null||dir2.contains(keyword)){
				Hashtable<String, Object> hash = new Hashtable<String, Object>();
				dir2 = dir2.replace("+", "*");
				dir2 = dir2.substring(0, dir2.length()-1);
				if(dir2.lastIndexOf("/")!=-1)
					dir2 = dir2.substring(dir2.lastIndexOf("/")+1);
				hash.put("is_dir", true);
				hash.put("has_file", true);
				hash.put("filesize", 0L);
				hash.put("is_photo", false);
				hash.put("filetype", "");
				hash.put("filename", dir2);
				hash.put("datetime", "");
				list.add(hash);	
			}
		}
		
		//遍历文件
		for (OSSObjectSummary objectSummary : objList) {
			if(keyword==null||objectSummary.getKey().contains(keyword)){
				Hashtable<String, Object> hash = new Hashtable<String, Object>();
				String type = "-";
				String objName = objectSummary.getKey();
				if(!objectSummary.getKey().equals(dir))
				{
					if(objName.indexOf(".")!=-1)
					{
						type=objName.substring(objName.lastIndexOf(".")+1);
					}
					
					String fileExt = objName.substring(objName.lastIndexOf(".") + 1).toLowerCase();
					hash.put("is_dir", false);
					hash.put("has_file", false);
					hash.put("filesize", objectSummary.getSize());
					hash.put("is_photo", Arrays.<String>asList(fileTypes).contains(fileExt));
					hash.put("filetype", fileExt);
					hash.put("filename", objName.substring(objName.lastIndexOf("/")+1));
					hash.put("datetime", DateUtil.format(objectSummary.getLastModified(), "yyyy-MM-dd HH:mm:ss"));
					list.add(hash);
			}
			}
	    }
		return list;
	}
	
}
