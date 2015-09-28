package fate.webapp.blog.service;

import java.util.List;

import fate.webapp.blog.model.PingRecord;

public interface PingRecordService {

	public List<PingRecord> findAll(int curPage, int pageSize);
	
	public void save(PingRecord pingRecord);
	
	public PingRecord find(int id);
	
	public PingRecord update(PingRecord pingRecord);
	
	public PingRecord findByThemeGuid(String guid);
	
	public long count();
}
