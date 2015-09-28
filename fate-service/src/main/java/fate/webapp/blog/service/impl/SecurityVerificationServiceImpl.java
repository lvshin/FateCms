package fate.webapp.blog.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fate.webapp.blog.model.SecurityVerification;
import fate.webapp.blog.persistence.SecurityVerificationDao;
import fate.webapp.blog.service.SecurityVerificationService;

@Service
@Transactional
public class SecurityVerificationServiceImpl implements SecurityVerificationService {

	@Autowired
	private SecurityVerificationDao securityVerificationDao;
	
	@Override
	public SecurityVerification find(String guid) {
		return securityVerificationDao.find(guid);
	}

	@Override
	public void save(SecurityVerification securityVerification) {
        securityVerificationDao.save(securityVerification);
	}

	@Override
	public SecurityVerification update(SecurityVerification securityVerification) {
		return securityVerificationDao.update(securityVerification);
	}

	@Override
	public void delete(SecurityVerification securityVerification) {
        securityVerificationDao.delete(securityVerification);
	}

	@Override
	public SecurityVerification findBySecurityVerificationAndType(int uid, int type) {
		return securityVerificationDao.loadByUserAndType(uid, type);
	}
	
	@Override
	public SecurityVerification findByLoginNameAndType(String loginName, int type){
		return securityVerificationDao.loadByLoginNameAndType(loginName, type);
	}
	
	@Override
	public SecurityVerification findByGuidAndType(String guid, int type){
		return securityVerificationDao.findByGuidAndType(guid, type);
	}

	@Override
	public List<SecurityVerification> findAll() {
		return securityVerificationDao.findAll();
	}

}
