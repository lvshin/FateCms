package fate.webapp.blog.persistence;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class BaseDao<T> {

	@PersistenceContext(unitName = "fate-persistence")
    protected EntityManager em;
	
	public T find(String guid){
		Class<T> entityClass = null;
        Type t = getClass().getGenericSuperclass();
        if(t instanceof ParameterizedType){
            Type[] p = ((ParameterizedType)t).getActualTypeArguments();
            entityClass = (Class<T>)p[0];
        }
		return em.find(entityClass, guid);
	}
	
	public T find(int id){
		Class<T> entityClass = null;
        Type t = getClass().getGenericSuperclass();
        if(t instanceof ParameterizedType){
            Type[] p = ((ParameterizedType)t).getActualTypeArguments();
            entityClass = (Class<T>)p[0];
        }
		return em.find(entityClass, id);
	}
	
	public void save(T t){
		em.persist(t);
	}
	
	public T update(T t){
		return em.merge(t);
	}
	
	public void delete(T t){
		em.remove(em.merge(t));
	}
	
	public List<T> findAll(){
		Class<T> entityClass = null;
        Type t = getClass().getGenericSuperclass();
        if(t instanceof ParameterizedType){
            Type[] p = ((ParameterizedType)t).getActualTypeArguments();
            entityClass = (Class<T>)p[0];
        }
        String className = entityClass.getName();
        className = className.substring(className.lastIndexOf(".")+1);
        String hql = "from "+className;
		return em.createQuery(hql, entityClass).getResultList();
	}
}
