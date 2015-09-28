package fate.webapp.blog.model;

import org.springframework.mail.javamail.JavaMailSender;

/**
 * 记录网站的全局设置，单例模式
 * @author 幻幻(Fate)
 *
 */
public class GlobalSetting {

	/**
	 * 站点名称，显示在浏览器窗口，title
	 */
	private String siteName;
	/**
	 * 网站名称，显示在页面底部联系方式处
	 */
	private String appName;
	
	/**
	 * 站点英文名称
	 */
	private String appEnName;
	/**
	 * 网站域名，点击名称后跳转的链接
	 */
	private String appUrl;
	/**
	 * 管理员邮箱,接收系统通知
	 */
	private String adminEmail;
	/**
	 * 网站备案号，显示在页面底部
	 */
	private String icp;
	/**
	 * 第三方统计代码
	 */
	private String statistics;
	
	private String statisticsHead;
	/**
	 * 是否开启oss
	 */
	private boolean aliyunUsed;
	/**
	 * 是否允许注册
	 */
	private boolean regAllow;
	/**
	 * 密码最小长度
	 */
	private int minLengthOfPassword;
	/**
	 * 注册是否需要邮箱验证
	 */
	private boolean needEmailVerify;
	/**
	 * 是否开启QQ登录
	 */
	private boolean qq;
	/**
	 * 是否开启新浪微博登录
	 */
	private boolean weibo;
	
	/**
	 * 极验验证id
	 */
	private String geetestId;
	
	/**
	 * 极验验证key
	 */
	private String geetestKey;
	
	private JavaMailSender javaMailSender;
	
	private String smtpFrom;
	
	private ThirdPartyAccess qqAccess;
	
	private ThirdPartyAccess weiboAccess;
	
	private String duoshuoKey;//多说的应用名称
	
	private String duoshuoSecret;//多说的应用密钥
	
	private String txAppKey;//腾讯微博的appkey
	
	private boolean redisOpen;//是否开启redis
	
	private static final GlobalSetting globalSetting = new GlobalSetting();
	
	private GlobalSetting() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public static GlobalSetting getInstance(){
		return globalSetting;
	}
	
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	
	public String getAppUrl() {
		return appUrl;
	}
	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
	}
	
	public String getAdminEmail() {
		return adminEmail;
	}
	public void setAdminEmail(String adminEmail) {
		this.adminEmail = adminEmail;
	}
	
	public String getIcp() {
		return icp;
	}
	public void setIcp(String icp) {
		this.icp = icp;
	}
	
	public String getStatistics() {
		return statistics;
	}
	public void setStatistics(String statistics) {
		this.statistics = statistics;
	}
	
	public boolean getAliyunUsed() {
		return aliyunUsed;
	}

	public void setAliyunUsed(boolean aliyunUsed) {
		this.aliyunUsed = aliyunUsed;
	}

	public boolean getRegAllow() {
		return regAllow;
	}
	public void setRegAllow(boolean regAllow) {
		this.regAllow = regAllow;
	}
	
	public int getMinLengthOfPassword() {
		return minLengthOfPassword;
	}
	public void setMinLengthOfPassword(int minLengthOfPassword) {
		this.minLengthOfPassword = minLengthOfPassword;
	}
	
	public boolean getNeedEmailVerify() {
		return needEmailVerify;
	}
	public void setNeedEmailVerify(boolean needEmailVerify) {
		this.needEmailVerify = needEmailVerify;
	}
	
	public boolean getQq() {
		return qq;
	}
	public void setQq(boolean qq) {
		this.qq = qq;
	}
	
	public boolean getWeibo() {
		return weibo;
	}

	public void setWeibo(boolean weibo) {
		this.weibo = weibo;
	}

	public String getAppEnName() {
		return appEnName;
	}
	public void setAppEnName(String appEnName) {
		this.appEnName = appEnName;
	}

	public String getStatisticsHead() {
		return statisticsHead;
	}

	public void setStatisticsHead(String statisticsHead) {
		this.statisticsHead = statisticsHead;
	}

	public JavaMailSender getJavaMailSender() {
		return javaMailSender;
	}

	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	public String getSmtpFrom() {
		return smtpFrom;
	}

	public void setSmtpFrom(String smtpFrom) {
		this.smtpFrom = smtpFrom;
	}

	public String getGeetestId() {
		return geetestId;
	}

	public void setGeetestId(String geetestId) {
		this.geetestId = geetestId;
	}

	public String getGeetestKey() {
		return geetestKey;
	}

	public void setGeetestKey(String geetestKey) {
		this.geetestKey = geetestKey;
	}

	public ThirdPartyAccess getQqAccess() {
		return qqAccess;
	}

	public void setQqAccess(ThirdPartyAccess qqAccess) {
		this.qqAccess = qqAccess;
	}

	public ThirdPartyAccess getWeiboAccess() {
		return weiboAccess;
	}

	public void setWeiboAccess(ThirdPartyAccess weiboAccess) {
		this.weiboAccess = weiboAccess;
	}

    public String getDuoshuoKey() {
        return duoshuoKey;
    }

    public void setDuoshuoKey(String duoshuoKey) {
        this.duoshuoKey = duoshuoKey;
    }

    public String getDuoshuoSecret() {
        return duoshuoSecret;
    }

    public void setDuoshuoSecret(String duoshuoSecret) {
        this.duoshuoSecret = duoshuoSecret;
    }

    public String getTxAppKey() {
        return txAppKey;
    }

    public void setTxAppKey(String txAppKey) {
        this.txAppKey = txAppKey;
    }

    public boolean getRedisOpen() {
        return redisOpen;
    }

    public void setRedisOpen(boolean redisOpen) {
        this.redisOpen = redisOpen;
    }
	
}
