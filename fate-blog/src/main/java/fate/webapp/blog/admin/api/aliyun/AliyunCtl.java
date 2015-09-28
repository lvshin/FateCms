package fate.webapp.blog.admin.api.aliyun;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.MultipartUpload;
import com.aliyun.oss.model.MultipartUploadListing;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.aliyun.oss.model.PartListing;
import com.aliyun.oss.model.PartSummary;

import fate.webapp.blog.base.Constants;
import fate.webapp.blog.model.Aliyun;
import fate.webapp.blog.model.FileListItem;
import fate.webapp.blog.model.GlobalSetting;
import fate.webapp.blog.model.Param;
import fate.webapp.blog.model.ProgressEntity;
import fate.webapp.blog.service.OSSService;
import fate.webapp.blog.service.ParamService;
import fate.webapp.blog.utils.Convert;
import fate.webapp.blog.utils.DateUtil;
import fate.webapp.blog.utils.Strings;

/**
 * 管理中心--文件管理
 * 
 * @author fate
 *
 */
@Controller
@RequestMapping("/admin/aliyun")
public class AliyunCtl {

    @Autowired
    private ParamService paramService;

    @Autowired
    private OSSService ossService;

    private Aliyun aliyun = Aliyun.getInstance();

    /**
     * 文件系统基本设置页面
     * 
     * @return
     */
    @RequestMapping("/set")
    public ModelAndView set() {
        ModelAndView mv = new ModelAndView("admin/aliyun/set");
        Param paccessKeyId = paramService.findByKey(Constants.ALIYUN_ACCESS_KEY_ID);
        // 阿里云Secret
        Param paccessKeySecret = paramService.findByKey(Constants.ALIYUN_ACCESS_KEY_SECRET);
        if (paccessKeyId != null) {
            mv.addObject("paccessKeyId", paccessKeyId.getTextValue());
            mv.addObject("paccessKeySecret", paccessKeySecret.getTextValue());
        }
        return mv;
    }

    @RequestMapping("/ossSet")
    public ModelAndView ossSet() {
        ModelAndView mv = new ModelAndView("admin/aliyun/ossSet");
        // OSS的endpoint
        Param ossEndpoint = paramService.findByKey(Constants.OSS_ENDPOINT);
        // OSS的bucket
        Param ossBucket = paramService.findByKey(Constants.OSS_BUCKET);

        Param ossUrl = paramService.findByKey(Constants.OSS_URL);
        if (ossBucket != null && ossBucket.getTextValue() != null
                && !ossBucket.getTextValue().equals("")) {
            List<Bucket> buckets = ossService.listBuckets();
            mv.addObject("buckets", buckets);
            mv.addObject("ossEndpoint", ossEndpoint.getTextValue());
            mv.addObject("ossBucket", ossBucket.getTextValue());
            mv.addObject("ossUrl", ossUrl.getTextValue());

        }

        List<String> referer = ossService.getReferer();
        String referers = "";
        if(referer!=null){
            for (String s : referer) {
                referers += s + "\r\n";
            }
        }
        

        mv.addObject("referers", referers);
        mv.addObject("allowEmptyReferer", ossService.isAllowEmptyReferer());
        return mv;
    }

    @RequestMapping("/openSearchSet")
    public ModelAndView openSearchSet() {
        ModelAndView mv = new ModelAndView("admin/aliyun/openSearchSet");
        Param openSearchEndpoint = paramService.findByKey(Constants.OPENSEARCH_ENDPOINT);
        Param openSearchAppName = paramService.findByKey(Constants.OPENSEARCH_APPNAME);

        if (openSearchAppName != null && openSearchAppName.getTextValue() != null
                && !openSearchAppName.getTextValue().equals("")) {
            mv.addObject("openSearchAppName", openSearchAppName.getTextValue());
            mv.addObject("openSearchEndpoint", openSearchEndpoint.getTextValue());

        }
        return mv;
    }

    /**
     * 更新文件系统的基本设置
     * 
     * @param session
     * @param on
     *            是否开启OSS
     * @param accessKeyId
     *            OSS的id
     * @param accessKeySecret
     *            OSS的密钥
     * @param bucket
     *            选用哪一个bucket
     * @param endpoint
     *            OSS节点
     * @param url
     *            域名绑定
     * @return
     */
    @RequestMapping("/updateSet")
    @ResponseBody
    public Object updateSet(String accessKeyId, String accessKeySecret) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            // 阿里云key
            Param paccessKeyId = paramService.findByKey(Constants.ALIYUN_ACCESS_KEY_ID);
            if (paccessKeyId == null) {
                paccessKeyId = new Param();
                paccessKeyId.setKey(Constants.ALIYUN_ACCESS_KEY_ID);
                paccessKeyId.setType(Param.TYPE_TEXT);
            }
            paccessKeyId.setTextValue(accessKeyId);
            paramService.update(paccessKeyId);
            // 阿里云Secret
            Param paccessKeySecret = paramService.findByKey(Constants.ALIYUN_ACCESS_KEY_SECRET);
            if (paccessKeySecret == null) {
                paccessKeySecret = new Param();
                paccessKeySecret.setKey(Constants.ALIYUN_ACCESS_KEY_SECRET);
                paccessKeySecret.setType(Param.TYPE_TEXT);
            }
            paccessKeySecret.setTextValue(accessKeySecret);
            paramService.update(paccessKeySecret);

            Aliyun aliyun = Aliyun.getInstance();
            aliyun.init(accessKeyId, accessKeySecret);

            map.put("success", true);
            map.put("msg", "设置保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
        }
        return map;
    }

    @RequestMapping("/updateOSSReferer")
    @ResponseBody
    public Object updateOSSReferer(String referer, boolean allowEmptyReferer) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            ossService.setReferer(referer, allowEmptyReferer);

            map.put("success", true);
            map.put("msg", "设置保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("msg", "设置失败");
        }
        return map;
    }

    @RequestMapping("/updateOSSSet")
    @ResponseBody
    public Object updateOSSSet(HttpSession session, boolean on, String bucket, String endpoint,
            String url) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            // 阿里云key
            Param paccessKeyId = paramService.findByKey(Constants.ALIYUN_ACCESS_KEY_ID);
            // 阿里云Secret
            Param paccessKeySecret = paramService.findByKey(Constants.ALIYUN_ACCESS_KEY_SECRET);
            if (paccessKeyId == null || paccessKeyId.getTextValue().equals("")
                    || paccessKeySecret == null || paccessKeySecret.getTextValue().equals("")) {
                map.put("success", false);
                map.put("msg", "请先设置access Key ID和access Key Secret!");
                return map;
            }
            // OSS的endpoint
            Param ossEndpoint = paramService.findByKey(Constants.OSS_ENDPOINT);
            if (ossEndpoint == null) {
                ossEndpoint = new Param();
                ossEndpoint.setKey(Constants.OSS_ENDPOINT);
                ossEndpoint.setType(Param.TYPE_TEXT);
            }
            ossEndpoint.setTextValue(endpoint);
            paramService.update(ossEndpoint);

            // OSS的bucket
            Param ossBucket = paramService.findByKey(Constants.OSS_BUCKET);
            if (ossBucket == null) {
                ossBucket = new Param();
                ossBucket.setKey(Constants.OSS_BUCKET);
                ossBucket.setType(Param.TYPE_TEXT);
            }
            ossBucket.setTextValue(bucket);
            paramService.update(ossBucket);

            // OSS的url
            Param ossUrl = paramService.findByKey(Constants.OSS_URL);
            if (ossUrl == null) {
                ossUrl = new Param();
                ossUrl.setKey(Constants.OSS_URL);
                ossUrl.setType(Param.TYPE_TEXT);
            }
            ossUrl.setTextValue(url);
            paramService.update(ossUrl);

            Aliyun aliyun = Aliyun.getInstance();
            aliyun.initOSS(endpoint, ossUrl.getTextValue(), ossBucket.getTextValue(),
                    ossEndpoint.getTextValue());

            GlobalSetting globalSetting = GlobalSetting.getInstance();
            globalSetting.setAliyunUsed(on);
            updateParam(Constants.ALIYUN_USED, on ? 1 : 0, Param.TYPE_INT);

            map.put("success", true);
            map.put("msg", "设置保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
        }
        return map;
    }

    @RequestMapping("/updateOpenSearchSet")
    @ResponseBody
    public Object updateOpenSearchSet(String endpoint, String appName) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Param openSearchEndpoint = paramService.findByKey(Constants.OPENSEARCH_ENDPOINT);

            if (openSearchEndpoint == null) {
                openSearchEndpoint = new Param();
                openSearchEndpoint.setKey(Constants.OPENSEARCH_ENDPOINT);
                openSearchEndpoint.setType(Param.TYPE_TEXT);
            }
            openSearchEndpoint.setTextValue(endpoint);
            paramService.update(openSearchEndpoint);

            Param openSearchAppName = paramService.findByKey(Constants.OPENSEARCH_APPNAME);
            if (openSearchAppName == null) {
                openSearchAppName = new Param();
                openSearchAppName.setKey(Constants.OPENSEARCH_APPNAME);
                openSearchAppName.setType(Param.TYPE_TEXT);
            }
            openSearchAppName.setTextValue(appName);
            paramService.update(openSearchAppName);

            Aliyun aliyun = Aliyun.getInstance();
            aliyun.initOpenSearch(endpoint, appName);

            map.put("success", true);
            map.put("msg", "设置保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
        }
        return map;
    }

    /**
     * 获取帐号下的bucket列表
     * 
     * @param endpoint
     * @param accessKeyId
     * @param accessKeySecret
     * @return
     */
    @RequestMapping("/getBuckets")
    @ResponseBody
    public Object getBuckets(String endpoint) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Param accessKeyId = paramService.findByKey(Constants.ALIYUN_ACCESS_KEY_ID);
            // 阿里云Secret
            Param accessKeySecret = paramService.findByKey(Constants.ALIYUN_ACCESS_KEY_SECRET);
            aliyun.init(accessKeyId.getTextValue(), accessKeySecret.getTextValue());
            Param ossBucket = paramService.findByKey(Constants.OSS_BUCKET);
            Param ossUrl = paramService.findByKey(Constants.OSS_URL);
            Param ossEndpoint = paramService.findByKey(Constants.OSS_ENDPOINT);
            aliyun.initOSS(endpoint, ossUrl==null?"":ossUrl.getTextValue(), ossBucket==null?"":ossBucket.getTextValue(),
                    ossEndpoint==null?"":ossEndpoint.getTextValue());
            List<Bucket> buckets = ossService.listBuckets();
            map.put("buckets", buckets);
            map.put("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("msg", "获取bucket列表失败");
        }
        return map;
    }

    /**
     * 文件列表页面
     * 
     * @param dir
     * @param keyword
     * @param session
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/filelist")
    public ModelAndView filelist(@RequestParam(defaultValue = "") String dir,
            @RequestParam(defaultValue = "") String keyword, HttpServletRequest request)
            throws UnsupportedEncodingException {
        if (!dir.equals("") && !dir.endsWith("/"))
            dir += "/";
        dir = java.net.URLDecoder.decode(dir, "utf-8");
        List<String> dirList = new ArrayList<String>();
        List<String> parentDirList = new ArrayList<String>();
        String[] dirs = dir.split("/");

        dirList.add("");
        if (!dir.equals(""))
            for (int i = 0; i < dirs.length; i++) {
                dirList.add(dirs[i]);
                parentDirList.add(dir.substring(0, dir.indexOf(dirs[i])));
            }
        parentDirList.add(dir);

        ModelAndView mv = new ModelAndView("admin/aliyun/fileList");

        ObjectListing listing = ossService.getList(dir);

        System.out.println(listing.getBucketName());
        // 正在上传的文件列表
        @SuppressWarnings("unchecked")
        List<ProgressEntity> list = (List<ProgressEntity>) request.getSession().getAttribute(
                "progressList");

        mv.addObject("bucketName", listing.getBucketName());
        mv.addObject("dirList", dirList);
        mv.addObject("folder", dir);
        mv.addObject("pDirList", parentDirList);
        mv.addObject("list", getFileList(dir, keyword, listing, request));
        mv.addObject("keyword", keyword);
        mv.addObject("listAll", list == null ? 0 : list.size());
        return mv;
    }

    public List<FileListItem> getFileList(String dir, String keyword, ObjectListing listing,
            HttpServletRequest request) throws UnsupportedEncodingException {
        List<FileListItem> list = new ArrayList<FileListItem>();
        // 获取文件列表
        List<OSSObjectSummary> objList = listing.getObjectSummaries();
        // 获取目录列表
        List<String> dirList = listing.getCommonPrefixes();
        // 排序，文件夹在前
        Collections.sort(objList, (os1, os2) -> {
            if (os1.getKey().endsWith("/") && os2.getKey().endsWith("/"))
                return os1.getKey().compareTo(os2.getKey());
            else if (os1.getKey().endsWith("/"))
                return -1;
            else if (os2.getKey().endsWith("/"))
                return 1;
            else
                return os1.getKey().toLowerCase().compareTo(os2.getKey().toLowerCase());
        });
        // 排序按中文顺序
        Collections.sort(
                dirList,
                (a, b) -> Strings.getHexString(a, "gb2312").compareTo(
                        Strings.getHexString(b, "gb2312")));

        // 如果不是首页且搜索的关键词为空，则在列表开头显示返回上级目录
        if (!(dir.equals("") && (keyword == null || keyword.equals("")))) {
            String url = "javascript:void(0);";
            FileListItem item = new FileListItem();
            item.setType("back");
            if (dir.length() > 0) {
                int index = dir.substring(0, dir.length() - 1).lastIndexOf("/");
                url = "admin/aliyun/filelist?dir="
                        + (keyword.equals("")
                                ? (index > 0 ? dir.substring(0, index + 1) : "")
                                : dir);
            } else
                url = "admin/aliyun/filelist";
            item.setShowName(".&nbsp;.&nbsp;/(返回上一级)");
            item.setUrl(url);
            list.add(item);
        }

        // 遍历文件夹
        for (String dir2 : dirList) {
            if (keyword == null || dir2.contains(keyword)) {
                FileListItem item = new FileListItem();
                item.setType("文件夹");
                item.setUrl("admin/aliyun/filelist?dir=" + dir2);
                item.setFileName(dir2.replace("+", "*"));
                item.setShowName(getFolderName(dir2));
                item.setFileSize("-");
                item.setUploadDate("-");
                list.add(item);
            }
        }

        // 遍历文件
        for (OSSObjectSummary objectSummary : objList) {
            if (keyword == null || objectSummary.getKey().contains(keyword)) {
                FileListItem item = new FileListItem();
                String type = "-";
                String url = "javascript:void(0);";
                String objName = objectSummary.getKey();
                if (!objectSummary.getKey().equals(dir)) {
                    if (objName.indexOf(".") != -1) {
                        type = objName.substring(objName.lastIndexOf(".") + 1);
                        url = getPopContent(objName, listing.getBucketName(), type, request);
                    }

                    if (getNumofSpecialChar(objName, '/') > 1) {
                        item.setFileName(objName.replace("/", "|").replace("+", "*"));
                    } else
                        item.setFileName(objName);
                    item.setFileName(java.net.URLEncoder.encode(item.getFileName(), "UTF-8"));
                    item.setShowName(objName.substring(objName.substring(0, objName.length() - 1)
                            .lastIndexOf("/") + 1));
                    item.setFileSize(Convert.sizeConvert(objectSummary.getSize()));
                    item.setType(type);
                    item.setUploadDate(DateUtil.format(objectSummary.getLastModified(), null));
                    item.setUrl(url);
                    list.add(item);
                }
            }
        }
        return list;
    }

    // json方式获取，优点无刷新更新数据，缺点，刷新页面后会跳到首页
    // @RequestMapping("/fileList")
    // @ResponseBody
    // public Object fileList(int bucketIndex,String path) throws
    // UnsupportedEncodingException{
    // Cloud cloud = Cloud.getInstance(Constants.endpoint,
    // accessKeyId,accessKeySecret);
    // List<Bucket> buckets = cloud.getClient().listBuckets();
    // List<Hashtable> fileList = getFileList2(path, "",
    // cloudService.getList(cloud,buckets.get(bucketIndex).getName(),path),
    // bucketIndex);
    // Map<String, Object> map = new HashMap<String, Object>();
    // System.out.println(path);
    // int index = 0;
    // if(!path.equals(""))
    // index = path.substring(0,path.length()-1).lastIndexOf("/");
    // map.put("moveup_dir_path", index>0?path.substring(0,index+1):"");
    // map.put("current_dir_path", path);
    // map.put("current_url",
    // "http://www.blessingwind.cn/op/file/getFile/"+buckets.get(bucketIndex).getName()+"/");
    // System.out.println(fileList.size());
    // map.put("total_count", fileList.size());
    // map.put("file_list", fileList);
    // return map;
    // }
    //
    // public List<Hashtable> getFileList2(String dir, String keyword,
    // ObjectListing listing, int bucketIndex) throws
    // UnsupportedEncodingException{
    // List<Hashtable> list = new ArrayList<Hashtable>();
    // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    // OSSObjectSummaryComparator comparator = new OSSObjectSummaryComparator();
    // CNComparator cnComparator = new CNComparator();
    // List<OSSObjectSummary> objList = listing.getObjectSummaries();
    // List<String> dirList = listing.getCommonPrefixes();
    // Collections.sort(objList, comparator);
    // Collections.sort(dirList, cnComparator);
    // for(String dir2:dirList){
    // if(keyword==null||dir2.contains(keyword)){
    // String type = "-";
    // String url = "javascript:void(0);";
    // Hashtable<String, Object> hash = new Hashtable<String, Object>();
    // type="文件夹";
    // url = "admin/aliyun/filelist?bucketIndex="+bucketIndex+"&dir="+dir2;
    // hash.put("is_dir", true);
    // hash.put("has_file", true);
    // hash.put("filesize", 0L);
    // hash.put("is_photo", false);
    // hash.put("filetype", "");
    // hash.put("filename",
    // dir2.substring(dir2.substring(0,dir2.length()-1).lastIndexOf("/")+1,dir2.length()-1));
    // hash.put("datetime", "");
    // list.add(hash);
    // }
    // }
    //
    // for (OSSObjectSummary objectSummary : objList) {
    // if(keyword==null||objectSummary.getKey().contains(keyword)){
    // Hashtable<String, Object> hash = new Hashtable<String, Object>();
    // String type = "-";
    // String url = "javascript:void(0);";
    // String objName = objectSummary.getKey();
    // if(!objectSummary.getKey().equals(dir))
    // {
    // if(objName.indexOf(".")!=-1)
    // {
    // type=objName.substring(objName.lastIndexOf(".")+1);
    // url = getPopContent(objName, listing.getBucketName(), type);
    // }
    //
    // hash.put("is_dir", false);
    // hash.put("has_file", false);
    // hash.put("filesize", objectSummary.getSize());
    // hash.put("is_photo", Arrays.<String>asList(fileTypes).contains(type));
    // hash.put("filetype", type);
    // if(getNumofSpecialChar(objName, '/')>1)
    // {
    // hash.put("filename", objName.replace("/", "|").replace("+", "*"));
    // }
    // else
    // hash.put("filename", objName);
    // hash.put("datetime", new
    // SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(objectSummary.getLastModified()));
    // list.add(hash);
    // }
    // }
    // }
    // return list;
    // }

    /**
     * 从文件夹地址中去除当前文件夹名
     * 
     * @param src
     * @return
     */
    private String getFolderName(String src) {
        return src.substring(src.substring(0, src.length() - 1).lastIndexOf("/") + 1);
    }

    public int getNumofSpecialChar(String src, char ch) {
        if (src.equals(""))
            return 0;
        String[] res = src.split("/");
        return res.length;
    }

    /**
     * 获取对应文件类型的url
     * 
     * @param key
     * @param bucketName
     * @param type
     * @return
     * @throws UnsupportedEncodingException
     */
    public String getPopContent(String key, String bucketName, String type,
            HttpServletRequest request) throws UnsupportedEncodingException {
        String content = "javascript:void(0);";

        int index = key.substring(0, key.length() - 1).lastIndexOf("/");
        String keyTitle = key.substring(index + 1).replace("+", "*");
        Param ossBucket = paramService.findByKey(Constants.OSS_BUCKET);
        GlobalSetting globalSetting = (GlobalSetting) request.getSession().getAttribute("setting");
        String url = null;
        Param OSSUrl = paramService.findByKey(Constants.OSS_URL);
        Param OSSEndpoint = paramService.findByKey(Constants.OSS_ENDPOINT);
        if (globalSetting.getAliyunUsed())
            url = "http://"
                    + (OSSUrl == null
                            ? ossBucket.getTextValue() + "." + OSSEndpoint.getTextValue()
                            : OSSUrl.getTextValue()) + "/"
                    + java.net.URLEncoder.encode(java.net.URLEncoder.encode(key, "UTF-8"), "UTF-8");
        else {
            key = key.replace("/", "|").replace("+", "*");
            url = request.getContextPath() + "/file/getfile/" + ossBucket.getTextValue() + "/"
                    + java.net.URLEncoder.encode(java.net.URLEncoder.encode(key, "UTF-8"), "UTF-8");
        }
        if (type.equals("mp3") || type.equals("ogg") || type.equals("m4a") || type.equals("flac")
                || type.equals("ape"))
            content = "javascript:open('"
                    + url
                    + "','"
                    + java.net.URLEncoder.encode(java.net.URLEncoder.encode(keyTitle, "UTF-8"),
                            "UTF-8") + "','" + type + "','0')";
        else if (type.equals("mp4") || type.equals("mkv") || type.equals("wmv")
                || type.equals("rmvb") || type.equals("flv")) {
            content = "javascript:open('"
                    + url
                    + "','"
                    + java.net.URLEncoder.encode(java.net.URLEncoder.encode(keyTitle, "UTF-8"),
                            "UTF-8") + "','" + type + "','1')";
        }

        else if (type.equals("jpg") || type.equals("jpeg") || type.equals("png")
                || type.equals("ico") || type.equals("gif")) {
            // HttpURLConnection conn = null;
            // try {
            // long start = Calendar.getInstance().getTimeInMillis();
            // URL url = new
            // URL(Constants.basic_url+"/file/getfile/"+java.net.URLEncoder.encode(java.net.URLEncoder.encode(key,"UTF-8"),"UTF-8")+"?bucketName="+bucketName);
            // conn = (HttpURLConnection) url.openConnection();
            // BufferedImage sourceImg =ImageIO.read(conn.getInputStream());
            // int width = sourceImg.getWidth();
            // int height = sourceImg.getHeight();
            // long end = Calendar.getInstance().getTimeInMillis();
            // if(width>1200)
            // {
            //
            // height = (int) (1.0*1200/width*height);
            // width = 1200;
            // }
            content = "javascript:open('<img id=img src="
                    + url
                    + " style=max-width:1200px;/>','"
                    + java.net.URLEncoder.encode(java.net.URLEncoder.encode(keyTitle, "UTF-8"),
                            "UTF-8") + "','" + type + "','2')";
            // } catch (FileNotFoundException e) {
            // // TODO Auto-generated catch block
            // e.printStackTrace();
            // } catch (IOException e) {
            // // TODO Auto-generated catch block
            // e.printStackTrace();
            // }finally{
            // if(conn!=null)
            // conn.disconnect();
            // }

        }
        // else if(type.equals("txt")||type.equals("xml")||type.equals("sql")){
        // try {
        // byte[] b = new byte[102400];
        // URL url = new
        // URL(Constants.basic_url+"/file/getfile/"+java.net.URLEncoder.encode(java.net.URLEncoder.encode(key,"UTF-8"),"UTF-8")+"?bucketName="+bucketName+"&type="+type);
        // HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        // BufferedInputStream bis = new
        // BufferedInputStream(conn.getInputStream());
        // bis.read(b);
        // content =
        // "javascript:open('<pre>"+java.net.URLEncoder.encode(java.net.URLEncoder.encode(new
        // String(b,"utf-8"),"UTF-8"),"UTF-8")+"</pre>','"+key+"','"+type+"')";
        // } catch (MalformedURLException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // } catch (IOException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
        // }

        return content;
    }

    /**
     * 上传列表页面
     * 
     * @param session
     * @return
     */
    @RequestMapping("/uploadlist")
    public ModelAndView uploadlist(HttpSession session) {
        ModelAndView mv = new ModelAndView("admin/aliyun/uploadList");
        @SuppressWarnings("unchecked")
        List<ProgressEntity> list = (List<ProgressEntity>) session.getAttribute("progressList");
        mv.addObject("list", list);
        mv.addObject("listAll", list == null ? 0 : list.size());
        mv.addObject("bucketName", "hhcloud");
        return mv;
    }

    /**
     * 获取成长上传的文件列表
     * 
     * @param bucketName
     * @param session
     * @return
     */
    @RequestMapping("/getLists")
    @ResponseBody
    public Object getLists(String bucketName, HttpSession session) {
        @SuppressWarnings("unchecked")
        List<ProgressEntity> list = (List<ProgressEntity>) session.getAttribute("progressList");
        return list == null ? 0 : list;
    }

    /**
     * 碎片整理页面
     * 
     * @param session
     * @return
     */
    @RequestMapping("/debrislist")
    public ModelAndView debrislist(HttpSession session) {
        ModelAndView mv = new ModelAndView("admin/aliyun/debrisList");
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        // 鑾峰彇Bucket鍐呮墍鏈変笂浼犱簨浠�
        MultipartUploadListing listing = ossService.listMultipartUploads();

        // List<Bucket> buckets = ossService.listBuckets();

        // 閬嶅巻鎵�湁涓婁紶浜嬩欢
        for (MultipartUpload multipartUpload : listing.getMultipartUploads()) {
            System.out.println("Key: " + multipartUpload.getKey() + " UploadId: "
                    + multipartUpload.getUploadId());
            Map<String, String> map = new HashMap<String, String>();
            map.put("fileName", multipartUpload.getKey());
            map.put("uploadId", multipartUpload.getUploadId());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            map.put("uploadDate", sdf.format(multipartUpload.getInitiated()));
            PartListing partListing = ossService.listParts(multipartUpload.getKey(),
                    multipartUpload.getUploadId());
            map.put("debrisNum", "" + partListing.getParts().size());
            long size = 0;
            for (PartSummary part : partListing.getParts()) {
                size += part.getSize();
            }
            map.put("debrisSize", Convert.sizeConvert(size));
            list.add(map);
        }
        mv.addObject("list", list);
        // mv.addObject("bucketName", ossSetting.getBucket());
        return mv;
    }

    /**
     * 删除碎片
     * 
     * @param bucketName
     * @param key
     * @param uploadId
     * @param session
     * @return
     */
    @RequestMapping("/deleteDebris")
    @ResponseBody
    public Object deleteDebris(String bucketName, String key, String uploadId, HttpSession session) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            ossService.abortMultipartUpload(key, uploadId);
            map.put("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
        }
        return map;
    }

    public void updateParam(String key, Object value, int type) {
        Param pobj = paramService.findByKey(key);
        if (pobj == null) {
            pobj = new Param();
            pobj.setKey(key);
            pobj.setType(type);
        }
        if (type == Param.TYPE_TEXT)
            pobj.setTextValue((String) value);
        else
            pobj.setIntValue((int) value);
        paramService.update(pobj);
    }
}
