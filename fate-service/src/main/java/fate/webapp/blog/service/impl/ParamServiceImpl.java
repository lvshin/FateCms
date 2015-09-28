package fate.webapp.blog.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fate.webapp.blog.model.Param;
import fate.webapp.blog.persistence.ParamDao;
import fate.webapp.blog.service.ParamService;

@Service
@Transactional
public class ParamServiceImpl implements ParamService {

	@Autowired
	private ParamDao paramDao;
	
	@Override
	public void save(Param param) {
		paramDao.save(param);
	}

	@Override
	public Param update(Param param) {
		return paramDao.update(param);
	}

	@Override
	public Param findByKey(String key) {
		return paramDao.findByKey(key);
	}

}
