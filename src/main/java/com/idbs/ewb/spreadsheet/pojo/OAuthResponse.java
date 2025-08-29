package com.idbs.ewb.spreadsheet.pojo;

import lombok.NoArgsConstructor;

/**
 * Class to map the auth token response from auth API
 * 
 * @author zifo
 *
 */
@NoArgsConstructor
public class OAuthResponse {
	
	private String accessToken;
	
	private String tokenType;
	
	private String expiresIn;
	
	public String getAccessToken() {
		return accessToken;
	}
	
	public void setAccessoken(String accessToken) {
		this.accessToken = accessToken;
	}
	
	public String getTokenType() {
		return tokenType;
	}
	
	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}
	
	public String getExpiresIn() {
		return expiresIn;
	}
	
	public void setExpiresIn(String expiresIn) {
		this.expiresIn = expiresIn;
	}

}
