package fate.webapp.blog.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fate.webapp.blog.model.FriendLink;
import fate.webapp.blog.persistence.FriendLinkDao;
import fate.webapp.blog.service.FriendLinkService;

@Service
@Transactional
public class FriendLinkServiceImpl implements FriendLinkService {

	@Autowired
	private FriendLinkDao friendLinkDao;
	
	@Override
	public List<FriendLink> page(int state, int curPage, int pageSize) {
		return friendLinkDao.page(state, curPage, pageSize);
	}
	
	public long count(int state){
		return friendLinkDao.count(state);
	}

	@Override
	public FriendLink findByLink(String link) {
		return friendLinkDao.findByLink(link);
	}

	@Override
	public void save(FriendLink friendLink) {
		friendLinkDao.save(friendLink);
	}

	@Override
	public FriendLink update(FriendLink friendLink) {
		return friendLinkDao.update(friendLink);
	}

	@Override
	public int checkLink(String link) {
		FriendLink friendLink = friendLinkDao.findByLink(link);
		if(friendLink==null)
			return 0;
		else if(friendLink.getState()==FriendLink.STATE_APPLY)
			return 1;
		else if(friendLink.getState()==FriendLink.STATE_PASS)
			return 2;
		else
			return 3;
			
	}

	@Override
	public FriendLink find(int id) {
		return friendLinkDao.find(id);
	}

	@Override
	public List<FriendLink> searchByState(int state) {
		return friendLinkDao.searchByState(state);
	}

}
