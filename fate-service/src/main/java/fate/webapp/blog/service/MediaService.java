package fate.webapp.blog.service;

import fate.webapp.blog.model.Media;

public interface MediaService {

	public Media find(int id);
	
	public void save(Media media);
	
	public Media update(Media media);
	
	public void delete(Media media);
	
	public Media findByUrl(String url);
}
