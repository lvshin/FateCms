package fate.webapp.blog.model;

/**
 * 用于从赛利亚OSS获取到文件信息后，经过处理之后返回给页面的格式
 * @author Fate
 *
 */
public class FileListItem {

	/**
	 * 文件名
	 */
	private String fileName;
	/**
	 * 用于显示的文件名（不带后缀）
	 */
	private String showName;
	/**
	 * 处理过后的用于显示的文件大小
	 */
	private String fileSize;
	/**
	 * 此处的文件类型关系到css样式，所以没采用Int
	 */
	private String type;
	/**
	 * 处理过后的上传时间
	 */
	private String uploadDate;
	/**
	 * 跳转地址，如果是文件夹则进入下级目录；如果是文件，则打开文件
	 */
	private String url;
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getShowName() {
		return showName;
	}
	public void setShowName(String showName) {
		this.showName = showName;
	}
	public String getFileSize() {
		return fileSize;
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(String uploadDate) {
		this.uploadDate = uploadDate;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
