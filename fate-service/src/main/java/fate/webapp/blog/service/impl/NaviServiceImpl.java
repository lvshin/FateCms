package fate.webapp.blog.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fate.webapp.blog.model.Navi;
import fate.webapp.blog.persistence.NaviDao;
import fate.webapp.blog.service.NaviService;

@Service
@Transactional
public class NaviServiceImpl implements NaviService {

	@Autowired
	private NaviDao naviDao;
	
	@Override
	public List<Navi> searchRoot() {
		return naviDao.searchRoot();
	}

	@Override
	public void save(Navi navi) {
		naviDao.save(navi);
	}

	@Override
	public Navi update(Navi navi) {
		return naviDao.update(navi);
	}

	@Override
	public void delete(Navi navi) {
		naviDao.delete(navi);
	}

	@Override
	public Navi find(int id) {
		return naviDao.find(id);
	}

}
