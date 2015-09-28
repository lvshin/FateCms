package fate.webapp.blog.api.open;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import fate.webapp.blog.model.FriendLink;
import fate.webapp.blog.service.FriendLinkService;
import fate.webapp.blog.utils.Strings;

@Controller
@RequestMapping("/friendLink")
public class FriendLinkCtl {

	private Logger log = Logger.getLogger(FriendLinkCtl.class);
	
	@Autowired
	private FriendLinkService friendLinkService;
	
	@RequestMapping("/add")
	@ResponseBody
	public Object add(String linkName, String linkUrl, String linkEmail){
		Map<String, Object> map = new HashMap<String, Object>();
		if(Strings.isEmpty(linkName)||Strings.isEmpty(linkUrl)){
			map.put("success", false);
			map.put("msg", "信息填写不完整");
			return map;
		}
		switch (friendLinkService.checkLink(linkUrl)) {
		case 1: map.put("success", false);
				map.put("msg", "该友链正在申请中，请勿重复提交！");
				return map;
		case 2: map.put("success", false);
				map.put("msg", "已是本站的友链，请勿重复提交！");
				return map;
		case 3: map.put("success", false);
				map.put("msg", "该友链已被本站拒绝，请过段时间后再提交！");
				return map;
		default:
			break;
		}
		FriendLink friendLink = new FriendLink();
		friendLink.setSiteName(linkName);
		friendLink.setUrl(linkUrl);
		friendLink.setEmail(linkEmail);
		friendLinkService.save(friendLink);
		log.info(linkName+"提交了友链\""+linkUrl+"\"");
		map.put("success", true);
		return map;
	}
}
