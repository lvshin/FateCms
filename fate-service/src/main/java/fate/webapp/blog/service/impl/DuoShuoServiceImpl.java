package fate.webapp.blog.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fate.webapp.blog.model.DuoShuo;
import fate.webapp.blog.persistence.DuoShuoDao;
import fate.webapp.blog.service.DuoShuoService;

@Service
@Transactional
public class DuoShuoServiceImpl implements DuoShuoService {

	@Autowired
	private DuoShuoDao duoShuoDao;
	
	@Override
	public void save(DuoShuo duoShuo) {
		duoShuoDao.save(duoShuo);
	}

	@Override
	public DuoShuo update(DuoShuo duoShuo) {
		return duoShuoDao.update(duoShuo);
	}

	@Override
	public void delete(DuoShuo duoShuo) {
		duoShuoDao.delete(duoShuo);
	}

	@Override
	public DuoShuo find(int id) {
		return duoShuoDao.find(id);
	}

	@Override
	public long count() {
		return duoShuoDao.count();
	}

	@Override
	public void update(String ids, String action, long logId) {
		duoShuoDao.update(ids, action, logId);
	}

	@Override
	public long findLastLogId() {
		return duoShuoDao.findLastLogId();
	}

	@Override
	public void delete(String ids) {
		duoShuoDao.delete(ids);
	}

}
