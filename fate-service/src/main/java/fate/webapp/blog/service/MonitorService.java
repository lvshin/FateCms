package fate.webapp.blog.service;

import fate.webapp.blog.model.MonitorInfoBean;

/**
 * 获取系统信息的业务逻辑类接口.
 */
public interface MonitorService {
    /**
     * 获得当前的监控对象.
     * @return 返回构造好的监控对象
     * @throws Exception
     */
    public MonitorInfoBean getMonitorInfoBean() throws Exception;
}
