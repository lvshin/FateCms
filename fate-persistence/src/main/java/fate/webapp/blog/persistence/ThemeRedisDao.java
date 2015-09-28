package fate.webapp.blog.persistence;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import fate.webapp.blog.model.Theme;

@Repository
public class ThemeRedisDao {

	@Autowired  
    protected RedisTemplate<String, Theme> redisTemplate; 
	
	/**
	 * redis获取数据
	 * @param guid
	 * @return
	 */
	public Theme redisThemeFetch(String guid){
		return redisTemplate.execute(new RedisCallback<Theme>() {  
            public Theme doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer(); 
                byte[] key = serializer.serialize("theme."+guid);  
                byte[] value = connection.get(key);
                if (value == null) {  
                    return null;  
                } 
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(value);  
                
                Theme t = null;
				try {
					ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
					t = (Theme) objectInputStream.readObject();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                return t;  
            }  
        });
	}
	
	/**
	 * 插入redis
	 * @param theme
	 */
	public void redisThemeUpdate(Theme theme){
		redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key  = serializer.serialize("theme."+theme.getGuid());
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(); 
                ObjectOutputStream objectOutputStream;
				try {
					objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
					objectOutputStream.writeObject(theme);
	                connection.set(key, byteArrayOutputStream.toByteArray());  
	                return true;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
                return false;
            }  
        });  
	}
	
	/**
	 * 删除主题缓存
	 * @param theme
	 */
	public void redisThemeDelete(Theme theme){
		redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key  = serializer.serialize("theme."+theme.getGuid());
                connection.del(key);
                return false;
            }  
        });  
	}
	
	public boolean updateList(String k, List<Theme> list) {  
        Assert.notEmpty(list);  
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key  = serializer.serialize("themeList."+k);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(); 
                ObjectOutputStream objectOutputStream;
				try {
					objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
					objectOutputStream.writeObject(list);
	                connection.set(key, byteArrayOutputStream.toByteArray()); 
	                return true;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
                return true;  
            }  
        }, false, true);  
        return result;  
    } 
	
	/**
	 * 查询主题的guid
	 * @param k
	 * @return
	 */
	public String redisGuidFetch(String k){
		return redisTemplate.execute(new RedisCallback<String>() {  
            public String doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer(); 
                byte[] key = serializer.serialize("theme."+k);  
                byte[] value = connection.get(key);
                if (value == null) {  
                    return null;  
                } 
                
                String guid = serializer.deserialize(value);
                return guid;  
            }  
        });
	}
	
	/**
	 * 将主题的guid存入redis
	 * @param k
	 * @param guid
	 */
	public void redisGuidUpdate(String k, String guid){
		redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key  = serializer.serialize("theme."+k);
                byte[] name = serializer.serialize(guid);
	            connection.set(key,name); 
	            return true;
            }  
        });  
	}
	
	
}
