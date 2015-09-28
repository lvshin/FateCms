package fate.webapp.blog.service;

import java.util.List;

import com.aliyuncs.ecs.model.v20140526.DescribeDiskMonitorDataResponse.DiskMonitorData;
import com.aliyuncs.ecs.model.v20140526.DescribeDisksResponse.Disk;
import com.aliyuncs.ecs.model.v20140526.DescribeInstanceMonitorDataResponse.InstanceMonitorData;
import com.aliyuncs.ecs.model.v20140526.DescribeInstancesResponse.Instance;

public interface ECSService {

    /**
     * 获取某个地区的ECS实例列表
     * @param accessKey
     * @param accessSecret
     * @param region null表示全部
     * @return
     */
    public List<Instance> getInstances(String region);
    
    public List<InstanceMonitorData> getInstanceMonitorData(String instance, String startTime, String endTime);
    
    public List<Disk> getDisks(String region);
    
    public List<DiskMonitorData> getDiskMonitorData(String diskId, String startTime, String endTime);
}
