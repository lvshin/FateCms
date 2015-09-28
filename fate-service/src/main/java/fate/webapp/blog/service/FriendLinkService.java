package fate.webapp.blog.service;

import java.util.List;

import fate.webapp.blog.model.FriendLink;

public interface FriendLinkService {

	public List<FriendLink> page(int state, int curPage, int pageSize);
	
	public long count(int state);
	
	public FriendLink findByLink(String link);
	
	public void save(FriendLink friendLink);
	
	public FriendLink update(FriendLink friendLink);
	
	public int checkLink(String link);
	
	public FriendLink find(int id);
	
	public List<FriendLink> searchByState(int state);
}
