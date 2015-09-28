package fate.webapp.blog.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.jdbc.ReturningWork;
import org.springframework.stereotype.Repository;

import fate.webapp.blog.model.User;
import fate.webapp.blog.utils.EncryptUtil;

@Repository
public class UserDao extends BaseDao<User>{

	/**
	 * 用户名密码登录
	 * @param loginName
	 * @param pwd
	 * @return
	 */
	public User login(String loginName,String pwd){
		String hql = "from User u where (u.email=:loginName or u.mobile=:loginName)";
		List<User> user = em.createQuery(hql, User.class).setParameter("loginName", loginName).getResultList();
		User u = null;
		if(user.size()!=0)
			u = user.get(0);
//		Session session = (Session) em.getDelegate();
//		session.doReturningWork(new ReturningWork<ResultSet>() {
//
//			@Override
//			public ResultSet execute(Connection connection) throws SQLException {
//				// TODO Auto-generated method stub
//				return null;
//			}
//		});
		if(u!=null)
		{
			String p = EncryptUtil.pwd(u.getActivateDate(), pwd);
			if(p.equals(u.getPassword()))
				return u;
		}
		return null;
	}
	
	public boolean checkLoginName(String loginName){
		String hql = "from User u where u.email=:loginName or u.mobile=:loginName";
		List<User> user = em.createQuery(hql, User.class).setParameter("loginName", loginName).getResultList();
		if(user.size()==0)
			return false;
		else
			return true;
	}
	
	public boolean checkNickName(String nickName){
		String hql = "from User u where u.nickName=:nickName";
		List<User> user = em.createQuery(hql, User.class).setParameter("nickName", nickName).getResultList();
		if(user.size()==0)
			return false;
		else
			return true;
	}
	
	public List<User> page(int curPage, int pageSize){
		String hql = "from User u ";
		return em.createQuery(hql, User.class).setFirstResult((curPage-1)*pageSize).setMaxResults(pageSize).getResultList();
	}
	
	public long count(){
		String hql = "select count(*) from User u";
		return em.createQuery(hql, Long.class).getSingleResult();
	}
}
