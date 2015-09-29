package fate.webapp.blog.listener;

import java.util.List;

import javax.servlet.http.HttpSession;

import fate.webapp.blog.model.ProgressEntity;

public class MyProgressListener implements ProgressListener{
	private HttpSession session;  
	private int index = -1;
    public MyProgressListener() {  
    }  
    public MyProgressListener(HttpSession session) {  
        this.session=session;  
    }
    public void update(long pBytesRead, long size, int pItems) { 
    	if(index!=-1){
    	@SuppressWarnings("unchecked")
		List<ProgressEntity> list = (List<ProgressEntity>) session.getAttribute("progressList");
    	if(list!=null&&list.size()!=0){
        ProgressEntity progressEntity = list.get(index);
        progressEntity.setSize(size);
        progressEntity.setpBytesRead(pBytesRead);  
        progressEntity.setState(ProgressEntity.upload_state_service_uploading);
    	}
    	}
    }
	public void setIndex(int index) {
		this.index = index;
	}  
}
