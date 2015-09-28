package fate.webapp.blog.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fate.webapp.blog.model.ApiLog;
import fate.webapp.blog.persistence.ApiLogDao;
import fate.webapp.blog.service.ApiLogService;

@Service
@Transactional
public class ApiLogServiceImpl implements ApiLogService{

	@Autowired
	private ApiLogDao apiLogDao;
	
	@Override
	public List<ApiLog> page(int curPage, int pageSize) {
		// TODO Auto-generated method stub
		return apiLogDao.page(curPage, pageSize);
	}

	@Override
	public void save(ApiLog apiLog) {
		// TODO Auto-generated method stub
		apiLogDao.save(apiLog);
	}

}
