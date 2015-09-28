package fate.webapp.blog.service.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.AbortMultipartUploadRequest;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.BucketReferer;
import com.aliyun.oss.model.CompleteMultipartUploadRequest;
import com.aliyun.oss.model.CompleteMultipartUploadResult;
import com.aliyun.oss.model.CopyObjectResult;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.InitiateMultipartUploadRequest;
import com.aliyun.oss.model.InitiateMultipartUploadResult;
import com.aliyun.oss.model.ListMultipartUploadsRequest;
import com.aliyun.oss.model.ListObjectsRequest;
import com.aliyun.oss.model.ListPartsRequest;
import com.aliyun.oss.model.MultipartUploadListing;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PartETag;
import com.aliyun.oss.model.PartListing;
import com.aliyun.oss.model.PutObjectResult;
import com.aliyun.oss.model.UploadPartRequest;
import com.aliyun.oss.model.UploadPartResult;

import fate.webapp.blog.model.Aliyun;
import fate.webapp.blog.model.ProgressEntity;
import fate.webapp.blog.service.OSSService;
import fate.webapp.blog.utils.MD5;

/**
 * 主要用于获取阿里云OSS的实例，待完善
 * 
 * @author 幻幻(Fate)
 * 
 */
@Service
@Transactional
public class OSSServiceImpl implements OSSService{

	// 设置每块为 200K
	final long partSize1 = 1024 * 200;

	// 设置每块为 1M
	final long partSize2 = 1024 * 1024;

	// 设置每块为 2M
	final long partSize3 = 1024 * 1024 * 2;

	// 设置每块为 5M
	final long partSize4 = 1024 * 1024 * 5;

	// 设置每块为 10M
	final long partSize5 = 1024 * 1024 * 20;

	private ExecutorService pool = Executors.newCachedThreadPool();
	
	private Aliyun aliyun = Aliyun.getInstance();
	
	/**
	 * 小文件上传
	 * @param bucketName
	 * @param clientFile
	 * @param folder
	 * @return
	 */
    public PutObjectResult simpleUpload(String bucketName,
			MultipartFile clientFile, String folder,String fileName) {
		fileName = folder + fileName;
		PutObjectResult result = null;
		try {
			InputStream content = clientFile.getInputStream();
			ObjectMetadata meta = new ObjectMetadata();
			meta.setContentLength(clientFile.getSize());
			result = Aliyun.getOSSClient().putObject(bucketName, fileName, content, meta);// 会自动关闭流？
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 头像上传
	 * @param bucketName
	 * @param clientFile
	 * @param folder
	 * @return
	 */
	public PutObjectResult headIconUpload(String bucketName,
			File file, String folder,String fileName) {
		System.out.println(folder);
		fileName = folder + fileName;
		PutObjectResult result = null;
		InputStream content = null;
		try {
			content = new FileInputStream(file);
			ObjectMetadata meta = new ObjectMetadata();
			meta.setContentLength(file.length());
			result = Aliyun.getOSSClient().putObject(bucketName, fileName, content, meta);// 会自动关闭流？
			content.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(content!=null){
				try {
					content.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	/**
	 * 分块上传
	 * @param bucketName
	 * @param partFile
	 * @param folder
	 * @return
	 */
	public Map<String, Object> multipartUpload(final String bucketName,
			MultipartFile partFile, String folder) {

		long partSize;
		if (partFile.getSize() <= partSize2)
			partSize = partSize1;
		else if (partFile.getSize() <= partSize2 * 50)
			partSize = partSize2;
		else if (partFile.getSize() <= partSize3 * 50)
			partSize = partSize3;
		else if (partFile.getSize() <= partSize4 * 100)
			partSize = partSize4;
		else
			partSize = partSize5;
		InputStream content;
		final String fileName = folder + partFile.getOriginalFilename();
		Map<String, Object> map = new HashMap<String, Object>();

		// 开始Multipart Upload
		InitiateMultipartUploadRequest initiateMultipartUploadRequest = new InitiateMultipartUploadRequest(
				bucketName, fileName);
		InitiateMultipartUploadResult initiateMultipartUploadResult = Aliyun.getOSSClient()
				.initiateMultipartUpload(initiateMultipartUploadRequest);
		final String uploadId = initiateMultipartUploadResult.getUploadId();

		// 计算分块数目
		int partCount = (int) (partFile.getSize() / partSize);
		if (partFile.getSize() % partSize != 0) {
			partCount++;
		}
		// List<ProgressEntity> list = (List<ProgressEntity>)
		// session.getAttribute("progressList");
		// int i;
		// for(i=0;i<list.size();i++){
		// if(list.get(i).getFileName().contains(partFile.getOriginalFilename())){
		// break;
		// }
		// }
		final ProgressEntity progressEntity = new ProgressEntity();
		progressEntity.setBucketName(bucketName);
		progressEntity.setFileName(fileName);
		progressEntity.setFilePath(folder);
		progressEntity.setUploadId(uploadId);
		progressEntity.setPartsAll(partCount);
		// 新建一个List保存每个分块上传后的ETag和PartNumber
		final List<PartETag> partETags = new ArrayList<PartETag>();
		Runnable runnable = new Runnable() {
			public void run() {
				Tag: while (true) {
					try {
						if (progressEntity.getState() != ProgressEntity.upload_state_complete) {
							ListPartsRequest listPartsRequest = new ListPartsRequest(
									progressEntity.getBucketName(),
									progressEntity.getFileName(),
									progressEntity.getUploadId());
							if (listPartsRequest != null) {
								progressEntity
										.setState(ProgressEntity.upload_state_OSS_uploading);
								// 获取上传的所有Part信息
								PartListing partListing = Aliyun.getOSSClient()
										.listParts(listPartsRequest);

								progressEntity.setPartsRead(partListing
										.getParts().size());
								if (partListing.getParts().size() == progressEntity
										.getPartsAll()) {
									progressEntity
											.setState(ProgressEntity.upload_state_complete);
									completeMultipartUpload( 
											progressEntity.getBucketName(),
											fileName, partETags,
											progressEntity.getUploadId());
									break Tag;
								}
							}
						}
						Thread.sleep(800);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}

			}
			// }
		};

		pool.execute(runnable);
		System.out.println("开始上传");

		try {
			for (int i = 0; i < partCount; i++) {
				// 获取文件流
				FileInputStream fis = (FileInputStream) partFile
						.getInputStream();

				// 跳到每个分块的开头
				long skipBytes = partSize * i;
				fis.skip(skipBytes);

				// 计算每个分块的大小
				long size = partSize < partFile.getSize() - skipBytes ? partSize
						: partFile.getSize() - skipBytes;

				String oraginalTag = MD5.getMd5ByFile(fis, skipBytes, size);
				// 创建UploadPartRequest，上传分块
				UploadPartRequest uploadPartRequest = new UploadPartRequest();
				uploadPartRequest.setBucketName(bucketName);
				uploadPartRequest.setKey(fileName);
				uploadPartRequest.setUploadId(uploadId);
				uploadPartRequest.setInputStream(fis);
				uploadPartRequest.setPartSize(size);
				uploadPartRequest.setPartNumber(i + 1);
				UploadPartResult uploadPartResult = Aliyun.getOSSClient().uploadPart(uploadPartRequest);
				if (uploadPartResult.getPartETag().getETag()
						.equals("\"" + oraginalTag + "\""))
					// 将返回的PartETag保存到List中。
					partETags.add(uploadPartResult.getPartETag());
				else {
					i--;
				}
				// 关闭文件
				fis.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
			abortMultipartUpload(fileName, uploadId);
			map.put("success", false);
		}

		return map;
	}
	
	/**
	 * 完成上传
	 * @param bucketName
	 * @param fileName
	 * @param partETags
	 * @param uploadId
	 * @return
	 */
	public boolean completeMultipartUpload(String bucketName, String fileName,
			List<PartETag> partETags, String uploadId) {
		CompleteMultipartUploadRequest completeMultipartUploadRequest = new CompleteMultipartUploadRequest(
				bucketName, fileName, uploadId, partETags);

		// 完成分块上传
		CompleteMultipartUploadResult completeMultipartUploadResult = Aliyun.getOSSClient()
				.completeMultipartUpload(completeMultipartUploadRequest);

//		FileEntity fileEntity = fileEntityDao.loadByUploadId(uploadId);
//		fileEntity.setState(ProgressEntity.upload_state_complete);
//		fileEntityDao.update(fileEntity);
		// 打印Object的ETag
		System.out.println(completeMultipartUploadResult.getETag());
		
//		completeMultipartUploadResult.getETag()
		
		return true;
	}
	
	/**
	 * 取消上传
	 * @param bucketName
	 * @param fileName
	 * @param uploadId
	 */
	public void abortMultipartUpload(String fileName,
			String uploadId) {
		Aliyun aliyun = Aliyun.getInstance();
		AbortMultipartUploadRequest abortMultipartUploadRequest = new AbortMultipartUploadRequest(aliyun.getOssBucket(), fileName, uploadId);

		// 取消分块上传
		Aliyun.getOSSClient().abortMultipartUpload(abortMultipartUploadRequest);
	}

	public String generatePresignedUrl(String key, Date expiration){
		Aliyun aliyun = Aliyun.getInstance();
		String url = Aliyun.getOSSClient().generatePresignedUrl(aliyun.getOssBucket(), key, expiration).toString();
		if(aliyun.getOssUrl()!=null)
			url = url.replace(aliyun.getOssBucket()+"."+aliyun.getOssEndpoint(), aliyun.getOssUrl());
		return url;
	}
	
	public Map<String, Object> deleteObject(String key) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			key = java.net.URLDecoder.decode(key, "utf-8");
			key = key.replace("|", "/").replace("*", "+");
			String fileName = key.substring(key.lastIndexOf("/")+1);
			String filePath = key.substring(0,key.lastIndexOf("/")+1);
//			if(!key.endsWith("/")){
//			FileEntity fileEntity = fileEntityDao.loadByFileNameAndPath(fileName, filePath);
//			if(fileEntity!=null)
//				fileEntityDao.delete(fileEntity);
//			}
			// 删除Object
			Aliyun aliyun = Aliyun.getInstance();
			Aliyun.getOSSClient().deleteObject(aliyun.getOssBucket(), key);
			map.put("success", true);
			map.put("msg", "删除成功");
		} catch (OSSException e) {
			e.printStackTrace();
			System.out.println(e.getHeader());
			//此处需做异常信息分类
			map.put("success", false);
		} catch (ClientException e2) {
			e2.printStackTrace();
			map.put("success", false);
			map.put("msg","连接到阿里云OSS失败");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("msg","文件名包含非法字符");
		}
		
		return map;
	}
	
	public boolean newFolder(String folderName, String curFolder) {
		String key = curFolder + folderName + "/";
		ByteArrayInputStream in = null;
		Aliyun aliyun = Aliyun.getInstance();
		try {
			ObjectMetadata meta = new ObjectMetadata();
			byte[] buffer = new byte[0];
			in = new ByteArrayInputStream(buffer);  
			meta.setContentLength(0);
			Aliyun.getOSSClient().putObject(aliyun.getOssBucket(),key, in, meta);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}finally{

			try {
				if (in != null)
					in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return true;
	}
	
	public MultipartUploadListing listMultipartUploads(){
		ListMultipartUploadsRequest listMultipartUploadsRequest = new ListMultipartUploadsRequest(aliyun.getOssBucket());
		return Aliyun.getOSSClient().listMultipartUploads(listMultipartUploadsRequest);
	}
	
	public List<Bucket> listBuckets(){
		return Aliyun.getOSSClient().listBuckets();
	}
	
	public ObjectListing getList(String dir){
//		if(dir.equals(""))
//			dir="/";
		// 构造ListObjectsRequest请求
		Aliyun aliyun = Aliyun.getInstance();
		ListObjectsRequest listObjectsRequest = new ListObjectsRequest(aliyun.getOssBucket());

		// "/" 为文件夹的分隔符
		listObjectsRequest.setDelimiter("/");

		// 列出fun目录下的所有文件和文件夹
		listObjectsRequest.setPrefix(dir);
		
		ObjectListing listing = Aliyun.getOSSClient().listObjects(listObjectsRequest);
//		for (OSSObjectSummary objectSummary : listing.getObjectSummaries()) {
//	        System.out.println(objectSummary.getKey());
//	    }
		return listing;
	}
	
	public int count(String dir){
		ObjectListing list = getList(dir);
		return list.getObjectSummaries().size();
	}
	
	public PartListing listParts(String key, String uploadId){
		Aliyun aliyun = Aliyun.getInstance();
		ListPartsRequest listPartsRequest = new ListPartsRequest(aliyun.getOssBucket(), key, uploadId);
	    return Aliyun.getOSSClient().listParts(listPartsRequest);
	}
	
	/**
	 * 将所有OSS中的文件下载到本地，切换存储模式的时候用
	 */
	public void downloadAll(){
		ObjectListing objectListing = getList("/");
		Aliyun aliyun = Aliyun.getInstance();
		for (OSSObjectSummary objectSummary : objectListing.getObjectSummaries()) {
			// 新建GetObjectRequest
			GetObjectRequest getObjectRequest = new GetObjectRequest(aliyun.getOssBucket(), objectSummary.getKey());

			// 下载Object到文件
			ObjectMetadata objectMetadata = Aliyun.getOSSClient().getObject(getObjectRequest, new File("D:/"+objectSummary.getKey()));
		}
		
	}
	
	public void copyObject(String srcKey, String destKey) {
		Aliyun aliyun = Aliyun.getInstance();
	    // 拷贝Object
	    CopyObjectResult result = Aliyun.getOSSClient().copyObject(aliyun.getOssBucket(), srcKey, aliyun.getOssBucket(), destKey);

	    // 打印结果
	    System.out.println("ETag: " + result.getETag() + " LastModified: " + result.getLastModified());
	}
	
	public void setReferer(String referer, boolean allowEmptyReferer){
		List<String> refererList = new ArrayList<String>();
		String[] referers = referer.split("\n");
		for(String s:referers){
			refererList.add(s);
		}
		Aliyun aliyun = Aliyun.getInstance();
		BucketReferer bucketReferer = new BucketReferer(allowEmptyReferer, refererList);
		Aliyun.getOSSClient().setBucketReferer(aliyun.getOssBucket(), bucketReferer);
	}
	
	public List<String> getReferer(){
		Aliyun aliyun = Aliyun.getInstance();
		if(Aliyun.getOSSClient()!=null){
		    BucketReferer bucketReferer = Aliyun.getOSSClient().getBucketReferer(aliyun.getOssBucket());
	        return bucketReferer.getRefererList();
		}
		return null;
	}
	
	public boolean isAllowEmptyReferer(){
		Aliyun aliyun = Aliyun.getInstance();
		if(Aliyun.getOSSClient()!=null){
		    BucketReferer bucketReferer = Aliyun.getOSSClient().getBucketReferer(aliyun.getOssBucket());
		    return bucketReferer.isAllowEmptyReferer();
		}
		return false;
	}
	
}
