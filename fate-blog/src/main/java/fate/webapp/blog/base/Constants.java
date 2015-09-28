package fate.webapp.blog.base;

import java.text.SimpleDateFormat;

public class Constants {

	
	//主题列表每页的个数
	public final static int THEME_PER = 11;
	
	/**
	 * 手机验证码超时时间
	 */
	public final static int MOBILE_TIMEOUT=10;
	
	/**
	 * 邮箱验证超时时间
	 */
	public final static int EMAIL_TIMEOUT=12*60;
	
	public static final String SESSION_USERNAME = "username";
    public static final String SESSION_TUTORIALS = "tutorials";
    public static final String SESSION_ADMIN = "entity";
    public static final String UN_AUDIT_MESSAGE = "尊敬的%s,您提交的教程%s,因%s不能通过审核，请您按要求修改。";
    public static final String AUDIT_MESSAGE = "尊敬的%s,您提交的教程%s,已通过审核。";
    public static final String SESSION_USER = "user";
    public static final String WEBSOCKET_UID = "websocket_uid";
    
    /**
     * 百度ping的地址
     */
    public static final String BAIDU_PING = "http://ping.baidu.com/ping/RPC2";
    
    /**
     * ping记录的每页大小
     */
    public static final int PING_PAGE_SIZE = 10;
    
    /**
     * 蜘蛛记录的每页大小
     */
    public static final int SPIDER_PAGE_SIZE = 10;
    
    /**
     * 后台公告每页大小
     */
    public static final int ANNOUNCEMENT_PAGE_SIZE = 10;
    
    /**
     * 广告列表的每页大小
     */
    public static final int ADVERTISEMENT_PAGE_SIZE = 10;
    
    /**
     * 异常记录的每页大小
     */
    public static final int EXCEPTION_LOG_PAGE_SIZE = 20;
    
    /**
     * SEO--首页title
     */
    public static final String SEO_INDEX_TITLE = "seo_index_title";
    
    /**
     * SEO--首页keywords
     */
    public static final String SEO_INDEX_KEYWORDS = "seo_index_keywords";
    
    /**
     * SEO--首页description
     */
    public static final String SEO_INDEX_DESCRIPTION = "seo_index_description";
    
    public static final String ALIYUN_ACCESS_KEY_ID = "aliyun_access_key_id";
    
    public static final String ALIYUN_ACCESS_KEY_SECRET = "aliyun_access_key_secret";
    
    public static final String OSS_ENDPOINT = "oss_endpoint";
    
    public static final String OSS_BUCKET = "oss_bucket";
    
    public static final String OSS_URL = "oss_url";
    
    public static final String OPENSEARCH_ENDPOINT = "opensearch_endpoint";
    
    public static final String OPENSEARCH_APPNAME = "opensearch_appname";
    
    public static final String SITE_MODE = "site_mode";
    
    public static final String SITE_NAME = "site_name";
    
    public static final String APP_NAME = "app_name";
    
    public static final String APP_URL = "app_url";
    
    public static final String APP_EN_NAME = "app_en_name";
    
    public static final String ADMIN_EMAIL = "admin_email";
    
    public static final String ICP = "icp";
    
    public static final String STATISTICS = "statistics";
    
    public static final String STATISTICSHEAD = "statistics_head";
    
    public static final String ALIYUN_USED = "aliyun_used";
    
    public static final String REG_ALLOW = "reg_allow";
    
    public static final String NEED_EMAIL_VERIFY = "need_email_verify";
    
    public static final String QQ = "qq";
    
    public static final String WEIBO = "weibo";
    
    public static final String SMTP_SERVER = "smtp_server";
    
    public static final String SMTP_FROM = "smtp_from";
    
    public static final String SMTP_USERNAME = "smtp_username";
    
    public static final String SMTP_PASSWORD = "smtp_password";
    
    public static final String SMTP_TIMEOUT = "smtp_timeout";
    
    public static final String GEETEST_ID = "geetest_id";
    
    public static final String GEETEST_KEY = "geetest_key";
    
    public static final String DUOSHUO_KEY = "duoshuo_key";
    
    public static final String DUOSHUO_SECRET = "duoshuo_secret";
    
    public static final String TX_APP_KEY = "tx_app_key";
    
    public static final String LOG_ID = "log_id";
    
    public static final String REDIS_OPEN = "redis_open";
    
    /**
     * 谷歌ping地址
     */
    public static final String GOOGLE_PING = "http://ping.blog.qikoo.com/rpc2.php";

    /**
     * 主页列表长度
     */
    public static final int INDEX_LIST_LENGTH = 15;
    
    /**
     * 主题列表长度
     */
    public static final int THEME_LIST_LENGTH = 25;
    
    /**
     * 历史最高在线
     */
    public static final String HISTORY_ONLINE = "histrry_online";
    
    /**
     * 搜索次数
     */
    public static final String SEARCH_COUNT = "search_count";
}
