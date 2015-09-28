package fate.webapp.blog.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.cms.model.v20150420.DescribeMetricDatumRequest;
import com.aliyuncs.cms.model.v20150420.DescribeMetricDatumResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

import fate.webapp.blog.service.CMSService;

@Service
public class CMSServiceImpl implements CMSService {

   public List<JSONObject> describeMetricDatum(String nameSpace, String metricName, String dimension, String startTime){
       DescribeMetricDatumRequest request = new DescribeMetricDatumRequest();
       request.setNamespace(nameSpace);
       request.setMetricName(metricName);
       request.setStartTime(startTime);
       request.setDimensions(dimension);
       
       IClientProfile profile = DefaultProfile.getProfile();
       IAcsClient client = new DefaultAcsClient(profile);
       try {
           DescribeMetricDatumResponse monitorDataResponse = client.getAcsResponse(request);
           return monitorDataResponse.getDatapoints();
       }catch (ClientException e) {
               e.printStackTrace();
               return null;
       }
   }
}
