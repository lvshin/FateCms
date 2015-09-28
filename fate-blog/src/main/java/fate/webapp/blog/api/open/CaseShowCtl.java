package fate.webapp.blog.api.open;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CaseShowCtl {

	private Logger log = Logger.getLogger(CaseShowCtl.class);
	
	@RequestMapping("/caseshow")
	public String caseshow(){
		return "caseshow";
	}
}
