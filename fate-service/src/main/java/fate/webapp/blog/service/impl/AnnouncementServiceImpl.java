package fate.webapp.blog.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fate.webapp.blog.model.Announcement;
import fate.webapp.blog.persistence.AnnouncementDao;
import fate.webapp.blog.service.AnnouncementService;

@Service
@Transactional
public class AnnouncementServiceImpl implements AnnouncementService {

	@Autowired
	private AnnouncementDao announcementDao;
	
	@Override
	public List<Announcement> findAll() {
		return announcementDao.findAll();
	}

	@Override
	public Announcement find(int id) {
		return announcementDao.find(id);
	}

	@Override
	public void save(Announcement announcement) {
		announcementDao.save(announcement);
	}

	@Override
	public Announcement update(Announcement announcement) {
		return announcementDao.update(announcement);
	}

	@Override
	public List<Announcement> page(int curPage, int pageSize) {
		return announcementDao.page(curPage, pageSize);
	}

	@Override
	public List<Announcement> findLast(int num) {
		return announcementDao.findLast(num);
	}

	@Override
	public long count() {
		return announcementDao.count();
	}

    @Override
    public void delete(Announcement announcement) {
        announcementDao.delete(announcement);
    }

}
