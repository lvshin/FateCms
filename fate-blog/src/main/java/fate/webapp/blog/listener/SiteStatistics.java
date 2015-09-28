package fate.webapp.blog.listener;

import java.util.Date;

public class SiteStatistics {

	/**
	 * 当前在线人数
	 */
	private static long online = 0;
	/**
	 * 历史最高在线人数
	 */
	private static long histrry_online = 0;
	/**
	 * 统计更新时间
	 */
	private static Date updateTime = new Date();
	/**
	 * 站点主题总数
	 */
	private static long theme = 0;
	/**
	 * 用户总数
	 */
	private static long user = 0;
	/**
	 * 评论总户
	 */
	private static long comment = 0;
	/**
	 * 搜索次数
	 */
	private static long search = 0;
	
	/**
	 * 浏览次数
	 */
	private static long views = 0;

	public static long getOnline() {
		return online;
	}

	public static void raise() {
		System.out.println("session+1");
		online++;
	}

	public static void reduce() {
		System.out.println("session-1");
		online--;
	}

	public static long getHistrry_online() {
		return histrry_online;
	}

	public static void setHistrry_online(long histrry_online) {
		SiteStatistics.histrry_online = histrry_online;
	}

	public static Date getUpdateTime() {
		return updateTime;
	}

	public static void setUpdateTime(Date updateTime) {
		SiteStatistics.updateTime = updateTime;
	}

	public static long getTheme() {
		return theme;
	}

	public static void setTheme(long theme) {
		SiteStatistics.theme = theme;
	}

	public static long getUser() {
		return user;
	}

	public static void setUser(long user) {
		SiteStatistics.user = user;
	}

	public static long getComment() {
		return comment;
	}

	public static void setComment(long comment) {
		SiteStatistics.comment = comment;
	}

	public static long getSearch() {
		return search;
	}

	public static void setSearch(long search) {
		SiteStatistics.search = search;
	}

	public static long getViews() {
		return views;
	}

	public static void setViews(long views) {
		SiteStatistics.views = views;
	}
	
	
}
