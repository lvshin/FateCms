package fate.webapp.blog.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class OnlineCounterListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		// TODO Auto-generated method stub
		SiteStatistics.raise();
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		// TODO Auto-generated method stub
		String checked = (String) se.getSession().getAttribute("checked");
		if(checked==null)
			SiteStatistics.reduce();
	}

}
