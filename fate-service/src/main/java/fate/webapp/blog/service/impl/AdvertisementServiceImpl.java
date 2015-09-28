package fate.webapp.blog.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fate.webapp.blog.model.Advertisement;
import fate.webapp.blog.persistence.AdvertisementDao;
import fate.webapp.blog.service.AdvertisementService;

@Service
@Transactional
public class AdvertisementServiceImpl implements AdvertisementService {

	@Autowired
	private AdvertisementDao advertisementDao;
	
	@Override
	public Advertisement find(int id) {
		return advertisementDao.find(id);
	}

	@Override
	public List<Advertisement> page(int curPage, int pageSize) {
		return advertisementDao.page(curPage, pageSize);
	}

	@Override
	public long count() {
		return advertisementDao.count();
	}

	@Override
	public void save(Advertisement advertisement) {
		advertisementDao.save(advertisement);
	}

	@Override
	public Advertisement update(Advertisement advertisement) {
		return advertisementDao.update(advertisement);
	}

	@Override
	public void delete(Advertisement advertisement) {
		advertisementDao.delete(advertisement);
	}

	@Override
	public Advertisement findLastByType(int type) {
		return advertisementDao.findLastByType(type);
	}

}
