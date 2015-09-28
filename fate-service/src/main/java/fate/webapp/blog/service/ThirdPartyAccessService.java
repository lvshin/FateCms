package fate.webapp.blog.service;

import fate.webapp.blog.model.ThirdPartyAccess;

public interface ThirdPartyAccessService {

	public ThirdPartyAccess findByType(int type);
	
	public ThirdPartyAccess update(ThirdPartyAccess access);
}
