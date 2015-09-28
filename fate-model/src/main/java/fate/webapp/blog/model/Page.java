package fate.webapp.blog.model;

/**
 * 分页类，简化分页操作，配合特定的js使用
 * @author Fate
 *
 */
public class Page {

	/**
	 * 当前页
	 */
	private int curPage;
	/**
	 * 每页条数
	 */
	private int pageSize;
	/**
	 * 总条数
	 */
	private int count;
	/**
	 * 总页数
	 */
	private int totalPage;
	/**
	 * 类型
	 */
	private int type;
	/**
	 * 中间位置
	 */
	private int middle;
	
	public Page(int curPage, int pageSize, int count){
		this.curPage = curPage;
		this.count = count;
		this.pageSize = pageSize;
		this.totalPage = getTotalPage(pageSize, count);
	}
	
	private int getTotalPage(int pageSize, int count){
		int totalPage = count/pageSize;
		if(count%pageSize!=0)
			totalPage++;
		return totalPage;
	}

	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
		this.middle = type/2;
	}

	public int getMiddle() {
		return middle;
	}
	
	
}
