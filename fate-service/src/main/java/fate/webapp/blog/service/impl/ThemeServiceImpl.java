package fate.webapp.blog.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fate.webapp.blog.model.Theme;
import fate.webapp.blog.persistence.ThemeDao;
import fate.webapp.blog.persistence.ThemeRedisDao;
import fate.webapp.blog.service.ThemeService;

@Service
@Transactional
public class ThemeServiceImpl implements ThemeService {

    @Autowired
    private ThemeDao themeDao;
    
    @Autowired
    private ThemeRedisDao themeRedisDao;
    
    
    
    public Theme find(String guid) {
        Theme theme = themeRedisDao.redisThemeFetch(guid);
        if(theme==null){
            Theme t = themeDao.find(guid);
            themeRedisDao.redisThemeUpdate(t);
            theme = t;
        }
        return theme;
    }

    public void save(Theme theme) {
        themeDao.save(theme);
    }

    public void crush(Theme theme) {
        themeRedisDao.redisThemeDelete(theme);
        themeDao.delete(theme);
    }
    
    public void crush(String guid) {
        Theme theme = themeDao.find(guid);
        crush(theme);
    }

    public Theme update(Theme theme) {
        Theme t = themeDao.update(theme);
        themeRedisDao.redisThemeDelete(t);
        return t;
    }

    public long count(int fid, boolean isDelete, int state){
        return themeDao.count(fid, isDelete, state);
    }
    
    public List<Theme> pageByFid(int fid, int per, int curPage, int state, boolean isDelete){
        return themeDao.pageByFid(fid, per, curPage, state, isDelete);
    }
    
    public Theme getLastestTheme(int fid){
        List<Theme> list = themeDao.pageByFid(fid, 1, 1, Theme.STATE_PUBLISH, false);
        if(list.size()==0)
            return null;
        else
            return list.get(0); 
    }
    
    public long statistics(int fid,int datetype,String day){
        return themeDao.statistics(fid, datetype, day);
    }
    
    public Theme findByDateAndTitle(String date, String title){
        String key = date+"-"+title;
        Theme theme = themeRedisDao.redisThemeFetch(themeRedisDao.redisGuidFetch(key));
        if(theme==null){
            theme = themeDao.findByDateAndTitle(date, title);
            if(theme!=null)
            themeRedisDao.redisGuidUpdate(key, theme.getGuid());
        }
        return theme;
    }

    @Override
    public List<Theme> pageByFid(int fid, int per, int curPage, boolean isDelete,boolean timeOrder, boolean priority, int state) {
        return themeDao.pageByFid(fid, per, curPage, isDelete, timeOrder, priority, state);
    }

    @Override
    public List<Theme> findAll(boolean isDelete) {
        return themeDao.findAll(isDelete);
    }

    @Override
    public List<Theme> pageHot(int per, int curPage, boolean isDelete, int state) {
        return themeDao.pageHot(per, curPage, isDelete, state);
    }

    @Override
    public long countViews(int fid, boolean isDelete, int state) {
        return themeDao.countViews(fid, isDelete, state);
    }

    @Override
    public List<Theme> pageSearchHot(int per, int curPage, boolean isDelete,
            int state) {
        return themeDao.pageSearchHot(per, curPage, isDelete, state);
    }

    @Override
    public List<Theme> pageByUid(int uid, int per, int curPage,
            boolean isDelete, boolean order, int state) {
        return themeDao.pageByUid(uid, per, curPage, isDelete, order, state);
    }

    @Override
    public long countByUid(int uid, boolean isDelete, int state) {
        return themeDao.countByUid(uid, isDelete, state);
    }

    @Override
    public List<Theme> pageByTag(String tag, int pageSize, int curPage,
            boolean isDelete) {
        return themeDao.pageByTag(tag, pageSize, curPage, isDelete);
    }

    @Override
    public long countByTag(String tag, boolean isDelete) {
        return themeDao.countByTag(tag, isDelete);
    }

    @Override
    public void multiDelete(List<String> guids) {
        themeDao.multiDelete(guids);
    }
}
