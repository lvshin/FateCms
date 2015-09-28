package fate.webapp.blog.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fate.webapp.blog.model.User;
import fate.webapp.blog.persistence.UserDao;
import fate.webapp.blog.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	UserDao userDao;

	public User find(int uid) {
		return userDao.find(uid);
	}

	public void save(User user) {
		userDao.save(user);
	}

	public User update(User user) {
		return userDao.update(user);
	}

	public void delete(User user) {
		userDao.delete(user);
	}
	
	public User login(String loginName,String pwd){
		return userDao.login(loginName, pwd);
	}

	public boolean checkLoginName(String loginName){
		return userDao.checkLoginName(loginName);
	}

	public boolean checkNickName(String nickName) {
		return userDao.checkNickName(nickName);
	}

	@Override
	public List<User> findAll() {
		return userDao.findAll();
	}

	
	
	@Override
	public long count() {
		return userDao.count();
	}

	@Override
	public List<User> page(int curPage, int pageSize) {
		return userDao.page(curPage, pageSize);
	}
}
