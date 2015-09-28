package fate.webapp.blog.service;

import fate.webapp.blog.model.Param;

public interface ParamService {

	public void save(Param param);
	
	public Param update(Param param);
	
	public Param findByKey(String key);
}
