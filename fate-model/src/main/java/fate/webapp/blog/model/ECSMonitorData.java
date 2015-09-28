package fate.webapp.blog.model;

import java.util.Date;
import java.util.List;

public class ECSMonitorData {

    private String instanceId;//实例id
    
    private String cpu;
    
    private String memory;
    
    private String disk;//主磁盘占用率
    
    private String timestamp;
    
    private String net;//网络使用率
    
    private String netIn;//入网流量
    
    private String netOut;//出网流量

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getMemory() {
        return memory;
    }

    public void setMemory(String memory) {
        this.memory = memory;
    }

    public String getDisk() {
        return disk;
    }

    public void setDisk(String disk) {
        this.disk = disk;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getNet() {
        return net;
    }

    public void setNet(String net) {
        this.net = net;
    }

    public String getNetIn() {
        return netIn;
    }

    public void setNetIn(String netIn) {
        this.netIn = netIn;
    }

    public String getNetOut() {
        return netOut;
    }

    public void setNetOut(String netOut) {
        this.netOut = netOut;
    }
    
    
    
}
