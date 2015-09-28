package fate.webapp.blog.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;

/**
 * 阿里云云监控服务
 * @author Fate
 *
 */
public interface CMSService {

    public List<JSONObject> describeMetricDatum(String nameSpace, String metricName, String dimension, String startTime);
}
