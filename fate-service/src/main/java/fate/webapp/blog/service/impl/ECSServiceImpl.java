package fate.webapp.blog.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.ecs.model.v20140526.DescribeDiskMonitorDataRequest;
import com.aliyuncs.ecs.model.v20140526.DescribeDiskMonitorDataResponse;
import com.aliyuncs.ecs.model.v20140526.DescribeDiskMonitorDataResponse.DiskMonitorData;
import com.aliyuncs.ecs.model.v20140526.DescribeDisksRequest;
import com.aliyuncs.ecs.model.v20140526.DescribeDisksResponse;
import com.aliyuncs.ecs.model.v20140526.DescribeDisksResponse.Disk;
import com.aliyuncs.ecs.model.v20140526.DescribeInstanceMonitorDataRequest;
import com.aliyuncs.ecs.model.v20140526.DescribeInstanceMonitorDataResponse;
import com.aliyuncs.ecs.model.v20140526.DescribeInstanceMonitorDataResponse.InstanceMonitorData;
import com.aliyuncs.ecs.model.v20140526.DescribeInstancesRequest;
import com.aliyuncs.ecs.model.v20140526.DescribeInstancesResponse;
import com.aliyuncs.ecs.model.v20140526.DescribeInstancesResponse.Instance;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

import fate.webapp.blog.model.Aliyun;
import fate.webapp.blog.service.ECSService;

@Service
public class ECSServiceImpl implements ECSService {

    public List<Instance> getInstances(String region){
        DescribeInstancesRequest describe = new DescribeInstancesRequest();
        Aliyun aliyun = Aliyun.getInstance();
        IClientProfile profile = DefaultProfile.getProfile(region, aliyun.getAccessKeyId(), aliyun.getAccessKeySecret());
        IAcsClient client = new DefaultAcsClient(profile);
        try {
                DescribeInstancesResponse response
                = client.getAcsResponse(describe);
                return response.getInstances();
        }catch (ClientException e) {
                e.printStackTrace();
                return null;
        }
    }
    
    public List<InstanceMonitorData> getInstanceMonitorData(String instanceId, String startTime, String endTime){
        DescribeInstanceMonitorDataRequest request = new DescribeInstanceMonitorDataRequest();
        request.setInstanceId(instanceId);
        request.setStartTime(startTime);
        request.setEndTime(endTime);
        request.setPeriod(60);
        IClientProfile profile = DefaultProfile.getProfile();
        IAcsClient client = new DefaultAcsClient(profile);
        try {
            DescribeInstanceMonitorDataResponse monitorDataResponse = client.getAcsResponse(request);
            return monitorDataResponse.getMonitorData();
        }catch (ClientException e) {
                e.printStackTrace();
                return null;
        }
    }
    
    public List<Disk> getDisks(String region){
        DescribeDisksRequest describe = new DescribeDisksRequest();
        IClientProfile profile = DefaultProfile.getProfile();
        IAcsClient client = new DefaultAcsClient(profile);
        try {
                DescribeDisksResponse response
                = client.getAcsResponse(describe);
                return response.getDisks();
        }catch (ClientException e) {
                e.printStackTrace();
                return null;
        }
    }
    
    public List<DiskMonitorData> getDiskMonitorData(String diskId, String startTime, String endTime){
        DescribeDiskMonitorDataRequest request = new DescribeDiskMonitorDataRequest();
        request.setDiskId(diskId);
        request.setStartTime(startTime);
        request.setEndTime(endTime);
        request.setPeriod(60);
        IClientProfile profile = DefaultProfile.getProfile();
        IAcsClient client = new DefaultAcsClient(profile);
        try {
            DescribeDiskMonitorDataResponse monitorDataResponse = client.getAcsResponse(request);
            return monitorDataResponse.getMonitorData();
        }catch (ClientException e) {
                e.printStackTrace();
                return null;
        }
    }
}
