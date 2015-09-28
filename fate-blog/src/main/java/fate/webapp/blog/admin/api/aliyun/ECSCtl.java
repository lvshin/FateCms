package fate.webapp.blog.admin.api.aliyun;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aliyuncs.ecs.model.v20140526.DescribeInstancesResponse.Instance;

import fate.webapp.blog.service.CMSService;
import fate.webapp.blog.service.ECSService;
import fate.webapp.blog.service.MonitorService;

@Controller
@RequestMapping("admin/aliyun/ecs")
public class ECSCtl {

    @Autowired
    private ECSService ecsService;

    @Autowired
    private CMSService cmsService;
    
    @Autowired
    private MonitorService monitorService;

    @RequestMapping("/index")
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView("admin/aliyun/ecs/index");

        List<Instance> instances = ecsService.getInstances("cn-hangzhou");
        try {
            InetAddress netAddress = InetAddress.getLocalHost();
            for (Instance instance : instances) {
                if(instance.getPublicIpAddress().contains(netAddress.getHostAddress())||instance.getInnerIpAddress().contains(netAddress.getHostAddress())){
                    mv.addObject("instance", instance);
                    break;
                }
            }

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        
        return mv;
    }
    
    @RequestMapping("/getEcsMonitorData")
    @ResponseBody
    public Object getEcsMonitorData(){
        Map<String, Object> map = new HashMap<String, Object>();
        
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.SECOND, 0);
//        cal.add(Calendar.MINUTE, -10);
        Date now = cal.getTime();
        cal.add(Calendar.MINUTE, 2);
        Date endTime = cal.getTime();
//        
//        List<InstanceMonitorData> instanceMonitorDatas = ecsService.getInstanceMonitorData(
//                instanceId, DateUtil.formatIso8601Date(now),
//                DateUtil.formatIso8601Date(endTime));
//        System.out.println("ecs_monitor:" + instanceMonitorDatas.size());

//        for(InstanceMonitorData instanceMonitorData:instanceMonitorDatas){
//            map.put("cpu", instanceMonitorData.getCPU());
//            map.put("timestamp",instanceMonitorData.getTimeStamp());
//            map.put("net", instanceMonitorData.getInternetBandwidth());
//            map.put("net_in", instanceMonitorData.getInternetRX());
//            map.put("net_out", instanceMonitorData.getInternetTX());
//            map.put("instanceId", instanceMonitorData.getInstanceId());
//            List<JSONObject> datas = cmsService.describeMetricDatum("acs/ecs", "vm.MemoryUtilization",
//                  "{instanceId:'"+instanceId+"'}", DateUtil.formatIso8601Date(now));
//            map.put("memory", datas.size() > 0?datas.get(0).get("value"):"");
////            System.out.println("IOPSRead:"+instanceMonitorData.getIOPSRead());
////            System.out.println("IOPSWrite:"+instanceMonitorData.getIOPSWrite());
//        }
        
//        List<JSONObject> datas = cmsService.describeMetricDatum("acs/ecs", "vm.CPUUtilization",
//                "{instanceId:'"+instanceId+"'}", DateUtil.formatIso8601Date(now));
//        map.put("cpu", datas.size() > 0?datas.get(0).get("value"):"");
//
//        datas = cmsService.describeMetricDatum("acs/ecs", "vm.MemoryUtilization",
//                "{instanceId:'"+instanceId+"'}", DateUtil.formatIso8601Date(now));
//        map.put("memory", datas.size() > 0?datas.get(0).get("value"):"");
//        
//        datas = cmsService.describeMetricDatum("acs/ecs", "vm.DiskUtilization",
//                "{instanceId:'"+instanceId+"'}", DateUtil.formatIso8601Date(now));
//        map.put("disk", datas.size() > 0?datas.get(0).get("value"):"");
//        
//        datas = cmsService.describeMetricDatum("acs/ecs", "vm.InternetNetworkRX",
//                "{instanceId:'"+instanceId+"'}", DateUtil.formatIso8601Date(now));
//        map.put("net_in", datas.size() > 0?datas.get(0).get("value"):"");
//        
//        datas = cmsService.describeMetricDatum("acs/ecs", "vm.InternetNetworkTX",
//                "{instanceId:'"+instanceId+"'}", DateUtil.formatIso8601Date(now));
//        map.put("net_out", datas.size() > 0?datas.get(0).get("value"):"");
        
        try {
            return monitorService.getMonitorInfoBean();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
}
