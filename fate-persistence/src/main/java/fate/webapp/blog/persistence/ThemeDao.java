package fate.webapp.blog.persistence;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import fate.webapp.blog.model.Theme;

@Repository
public class ThemeDao extends BaseDao<Theme>{

	public List<Theme> findAll(boolean isDelete){
		String hql = "from Theme t where t.isDelete=:isDelete";
		return em.createQuery(hql, Theme.class).setParameter("isDelete", isDelete).getResultList();
	}
	
	/**
	 * 批量逻辑删除
	 * @param guids
	 */
	public void multiDelete(List<String> guids){
	    String hql = "update Theme t set t.isDelete=true where t.guid in (:guids)";
	    em.createQuery(hql).setParameter("guids", guids).executeUpdate();
	}
	
	public List<Theme> pageByFid(int fid, int per, int curPage, boolean isDelete ,boolean timeOrder, boolean priority, int state){
		String hql = "from Theme t where t.isDelete=:isDelete";
		if(state!=0){
			hql += " and t.state=:state";
		}
		if(fid==-1){
			hql += " and t.forum is null order by "+(priority?"t.priority desc,":"")+" t.publishDate "+(timeOrder?"desc":"asc");
		}
		else if(fid!=0){
			hql += " and t.forum.fid=:fid order by "+(priority?"t.priority desc,":"")+" t.publishDate "+(timeOrder?"desc":"asc");
			TypedQuery<Theme> query =  em.createQuery(hql, Theme.class).setParameter("fid", fid).setParameter("isDelete", isDelete);
			if(state!=0)
				query = query.setParameter("state", state);
			return query.setFirstResult((curPage-1)*per).setMaxResults(per).getResultList();
		}else
			hql += " order by "+(priority?"t.priority desc,":"")+" t.publishDate "+(timeOrder?"desc":"asc");
		TypedQuery<Theme> query = em.createQuery(hql, Theme.class).setParameter("isDelete", isDelete);
		if(state!=0)
			query = query.setParameter("state", state);
		return query.setFirstResult((curPage-1)*per).setMaxResults(per).getResultList();
	}
	
	public long count(int fid, boolean isDelete, int state){
		String hql = "select count(*) from Theme t where t.isDelete=:isDelete";
		long num = 0;
		if(state!=0){
			hql += " and t.state=:state";
		}
		if(fid!=0){
			hql += " and t.forum.fid=:fid";
			if(state!=0)
				num = em.createQuery(hql, Long.class).setParameter("fid", fid).setParameter("isDelete", isDelete).setParameter("state", state).getSingleResult();
			else
				num = em.createQuery(hql, Long.class).setParameter("fid", fid).setParameter("isDelete", isDelete).getSingleResult();
		}else{ 
			if(state!=0)
				num = em.createQuery(hql, Long.class).setParameter("isDelete", isDelete).setParameter("state", state).getSingleResult();
			else
			num = em.createQuery(hql, Long.class).setParameter("isDelete", isDelete).getSingleResult();
		}
		return num;
	}
	
	public List<Theme> pageByUid(int uid, int per, int curPage, boolean isDelete ,boolean order, int state){
		String hql = "from Theme t where t.isDelete=:isDelete";
		if(state!=0){
			hql += " and t.state=:state";
		}
		if(uid!=0){
			hql += " and t.authorId=:uid order by t.publishDate "+(order?"desc":"asc");
			TypedQuery<Theme> query =  em.createQuery(hql, Theme.class).setParameter("uid", uid).setParameter("isDelete", isDelete);
			if(state!=0)
				query = query.setParameter("state", state);
			return query.setFirstResult((curPage-1)*per).setMaxResults(per).getResultList();
		}
		
		TypedQuery<Theme> query = em.createQuery(hql, Theme.class).setParameter("isDelete", isDelete);
		if(state!=0)
			query = query.setParameter("state", state);
		return query.setFirstResult((curPage-1)*per).setMaxResults(per).getResultList();
	}
	
	public long countByUid(int uid, boolean isDelete, int state){
		String hql = "select count(*) from Theme t where t.isDelete=:isDelete";
		long num = 0;
		if(state!=0){
			hql += " and t.state=:state";
		}
		if(uid!=0){
			hql += " and t.authorId=:uid";
			if(state!=0)
				num = em.createQuery(hql, Long.class).setParameter("uid", uid).setParameter("isDelete", isDelete).setParameter("state", state).getSingleResult();
			else
				num = em.createQuery(hql, Long.class).setParameter("uid", uid).setParameter("isDelete", isDelete).getSingleResult();
		}else{ 
			if(state!=0)
				num = em.createQuery(hql, Long.class).setParameter("isDelete", isDelete).setParameter("state", state).getSingleResult();
			else
			num = em.createQuery(hql, Long.class).setParameter("isDelete", isDelete).getSingleResult();
		}
		return num;
	}
	
	public List<Theme> pageByTag(String tag, int pageSize, int curPage, boolean isDelete){
		String hql = "from Theme t where t.isDelete=:isDelete and t.tags like :tag order by t.publishDate desc";
		return  em.createQuery(hql, Theme.class).setParameter("isDelete", isDelete).setParameter("tag", "%"+tag+"%").setFirstResult((curPage-1)*pageSize).setMaxResults(pageSize).getResultList();
	}
	
	public long countByTag(String tag, boolean isDelete){
		String hql = "select count(*) from Theme t where t.isDelete=:isDelete and t.tags like :tag";
		return em.createQuery(hql, Long.class).setParameter("isDelete", isDelete).setParameter("tag", "%"+tag+"%").getSingleResult();
	}
	
	public long countViews(int fid, boolean isDelete, int state){
		String hql = "select sum(t.views) from Theme t where t.isDelete=:isDelete";
		Long num = 0l;
		if(state!=0){
			hql += " and t.state=:state";
		}
		if(fid!=0){
			hql += " and t.forum.fid=:fid";
			if(state!=0)
				num = em.createQuery(hql, Long.class).setParameter("fid", fid).setParameter("isDelete", isDelete).setParameter("state", state).getSingleResult();
			else
				num = em.createQuery(hql, Long.class).setParameter("fid", fid).setParameter("isDelete", isDelete).getSingleResult();
		}else{ 
			if(state!=0)
				num = em.createQuery(hql, Long.class).setParameter("isDelete", isDelete).setParameter("state", state).getSingleResult();
			else
			num = em.createQuery(hql, Long.class).setParameter("isDelete", isDelete).getSingleResult();
		}
		return num==null?0:num;
	}
	
	public List<Theme> pageByFid(int fid, int per, int curPage, int state, boolean isDelete){
		String hql = "from Theme t where t.forum.fid=:fid and t.isDelete=:isDelete and t.state=:state order by t.publishDate desc";
		return em.createQuery(hql, Theme.class).setParameter("fid", fid).setParameter("isDelete", isDelete).setParameter("state", state).setFirstResult((curPage-1)*per).setMaxResults(per).getResultList();
	}
	
	
	public long statistics(int fid,int dateType,String day){
		String hql = "select count(1) from Theme t where t.forum.fid=:fid";
		if(dateType==0){
			hql += " and DATEDIFF(NOW(),t.publishDate)=:day";
			return em.createQuery(hql, Long.class).setParameter("fid", fid).setParameter("day", Integer.parseInt(day)).getSingleResult();
		}
		else if(dateType==1){
			hql += " and DATE_FORMAT(t.publishDate,'%Y-%m')=:day";
			return em.createQuery(hql, Long.class).setParameter("fid", fid).setParameter("day", day).getSingleResult();
		}else{
			return em.createQuery(hql, Long.class).setParameter("fid", fid).getSingleResult();
		}
		
	}
	
	public Theme findByDateAndTitle(String date, String title){
		String hql = "select * from reinforce_theme t where t.title=:title and t.publish_date like :date";
		List<Theme> themes = em.createNativeQuery(hql, Theme.class).setParameter("title", title).setParameter("date", "%"+date+"%").getResultList();
		if(themes.size()==0)
			return null;
		else
			return themes.get(0);
	}
	
	public List<Theme> pageHot(int per, int curPage, boolean isDelete, int state){
		String hql = "from Theme t where t.isDelete=:isDelete";
		if(state!=0){
			hql += " and t.state=:state";
		}
		hql += " order by t.views desc";
		TypedQuery<Theme> query =  em.createQuery(hql, Theme.class).setParameter("isDelete", isDelete);
		if(state!=0)
			query = query.setParameter("state", state);
		return query.setFirstResult((curPage-1)*per).setMaxResults(per).getResultList();
	}
	
	public List<Theme> pageSearchHot(int per, int curPage, boolean isDelete, int state){
		String hql = "from Theme t where t.isDelete=:isDelete";
		if(state!=0){
			hql += " and t.state=:state";
		}
		hql += " order by t.search desc";
		TypedQuery<Theme> query =  em.createQuery(hql, Theme.class).setParameter("isDelete", isDelete);
		if(state!=0)
			query = query.setParameter("state", state);
		return query.setFirstResult((curPage-1)*per).setMaxResults(per).getResultList();
	}
	
}
