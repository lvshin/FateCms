package fate.webapp.blog.service;

import java.util.List;

import fate.webapp.blog.model.Navi;

public interface NaviService {

	public List<Navi> searchRoot();
	
	public Navi find(int id);
	
	public void save(Navi navi);
	
	public Navi update(Navi navi);
	
	public void delete(Navi navi);
}
