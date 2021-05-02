package com.xiaoyan.eurekaclient.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * IP地址
 */
public class IPUtils {

	private static Logger logger = LoggerFactory.getLogger(IPUtils.class);

	/**
	 * 获取IP地址
	 * 使用Nginx等反向代理软件， 则不能通过request.getRemoteAddr()获取IP地址
	 * 如果使用了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP地址，X-Forwarded-For中第一个非unknown的有效IP字符串，则为真实IP地址
	 */
	//todo 了解一下ip是怎么回事
	public static String getIpAddr() {
		String ip = null;
		try {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
			//HTTP_X_FORWARDED_FOR = 代理服务器 IP ，经过多个代理服务器时，这个值类似如下：203.98.182.163, 203.98.182.163, 203.129.72.215
			ip = request.getHeader("x-forwarded-for");
			if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("Proxy-Client-IP");
			}
			if (StringUtils.isEmpty(ip) || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
			}
			if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_CLIENT_IP");
			}
			if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_X_FORWARDED_FOR");
			}
			//没有使用代理服务器的情况：REMOTE_ADDR = 您的 IP
			if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getRemoteAddr();
			}
		} catch (Exception e) {
			logger.error("IPUtils ERROR ", e);
		}
		// 使用代理，则获取第一个IP地址
		if (StringUtils.isNotEmpty(ip) && ip.length() > 15) {
			if (ip.indexOf(",") > 0) {
				ip = ip.substring(0, ip.indexOf(","));
			}
		}
		return ip;
	}
}
