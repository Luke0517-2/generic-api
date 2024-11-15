package com.iisigroup.generic.utils;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;

public class JWTInfoHelper {
	
	public String showUserName() {
		BearerTokenAuthentication bta = bta() ;
		if (bta==null) {
			return "unknown from security";
		}
		Map<String, Object> tas = bta.getTokenAttributes();
		String preferredUsername = (String) tas.get("preferred_username");

		return preferredUsername;
	}
	public String showRealmName() {
		BearerTokenAuthentication bta = bta() ;
		if (bta==null) {
			return "unknown from security";
		}
		Map<String, Object> tas = bta.getTokenAttributes();
		String iss = (String)tas.get("iss");  
		String [] tmp= StringUtils.split(iss, "//");
		String realmName= tmp[4]; 
		return realmName;
	}
	public String showToken() {
		BearerTokenAuthentication bta = bta() ;
		if (bta==null) {
			return "unknown from security";
		}
		return bta.getToken().getTokenValue();
	}
	protected BearerTokenAuthentication bta() {
		SecurityContext sc = SecurityContextHolder.getContext(); 
        Authentication authentication = sc.getAuthentication();
		if (authentication instanceof BearerTokenAuthentication) {
			BearerTokenAuthentication bta = (BearerTokenAuthentication) authentication; 
			return bta;
		}
		return null;
	}

	public String getCompanyIdByJwt() {
		String preferredUsername = showUserName();
		int index = preferredUsername.indexOf('#');
		if (index == -1) {
			return preferredUsername; // 如果没有#，返回原字串
		}
		return preferredUsername.substring(0, index);
	}

	public String getUsernameByJwt() {
		String preferredUsername = showUserName();
		int index = preferredUsername.indexOf('#');
		if (index == -1) {
			return preferredUsername; // 如果没有#，返回原字串
		}
		return preferredUsername.substring(index + 1); // 返回#之后的部分
	}
}
