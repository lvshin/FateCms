package fate.webapp.blog.service;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.MultipartUploadListing;
import com.aliyun.oss.model.ObjectListing;
import com.aliyun.oss.model.PartETag;
import com.aliyun.oss.model.PartListing;
import com.aliyun.oss.model.PutObjectResult;

public interface OSSService {


	/**
	 * 小文件上传
	 * @param bucketName
	 * @param clientFile
	 * @param folder
	 * @return
	 */
	public PutObjectResult simpleUpload(String bucketName,
			MultipartFile clientFile, String folder, String fileName);
	
	/**
	 * 头像上传
	 * @param bucketName
	 * @param clientFile
	 * @param folder
	 * @return
	 */
	public PutObjectResult headIconUpload(String bucketName,
			File file, String folder,String fileName);

	/**
	 * 分块上传
	 * @param bucketName
	 * @param partFile
	 * @param folder
	 * @return
	 */
	public Map<String, Object> multipartUpload(final String bucketName,
			MultipartFile partFile, String folder);
	
	/**
	 * 完成上传
	 * @param bucketName
	 * @param fileName
	 * @param partETags
	 * @param uploadId
	 * @return
	 */
	public boolean completeMultipartUpload(String bucketName, String fileName,
			List<PartETag> partETags, String uploadId) ;
	
	/**
	 * 取消上传
	 * @param bucketName
	 * @param fileName
	 * @param uploadId
	 */
	public void abortMultipartUpload(String fileName,
			String uploadId);

	public String generatePresignedUrl(String key, Date expiration);
	
	public Map<String, Object> deleteObject(String key);
	
	public boolean newFolder(String folderName, String curFolder);
	
	public MultipartUploadListing listMultipartUploads();
	
	public List<Bucket> listBuckets();
	
	/**
	 * 获取文件列表
	 * @param dir
	 * @return
	 */
	public ObjectListing getList(String dir);
	
	public int count(String dir);
	
	public PartListing listParts(String key, String uploadId);
	
	/**
	 * 将所有OSS中的文件下载到本地，切换存储模式的时候用
	 */
	public void downloadAll();
	
	public void copyObject(String srcKey, String destKey);
	
	/**
	 * 给Bucket设置referer
	 * @param ossClient
	 * @param referer
	 * @param allowEmptyReferer
	 */
	public void setReferer(String referer, boolean allowEmptyReferer);
	
	/**
	 * 获取当前bucket的referer
	 * @return
	 */
	public List<String> getReferer();
	
	public boolean isAllowEmptyReferer();
}
