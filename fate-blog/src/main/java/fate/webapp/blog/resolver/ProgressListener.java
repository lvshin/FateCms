package fate.webapp.blog.resolver;

public interface ProgressListener {

	void update(long pBytesRead, long pContentLength, int pItems);
	
	public void setIndex(int index);
}
