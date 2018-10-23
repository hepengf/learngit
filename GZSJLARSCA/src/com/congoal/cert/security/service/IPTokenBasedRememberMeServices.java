package com.congoal.cert.security.service;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.rememberme.InvalidCookieException;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.util.DigestUtils;

public class IPTokenBasedRememberMeServices extends
		TokenBasedRememberMeServices {
	private static final ThreadLocal<HttpServletRequest> requestHolder = new ThreadLocal<HttpServletRequest>();

	public HttpServletRequest getContext() {
		return requestHolder.get();
	}

	public void setContext(HttpServletRequest context) {
		requestHolder.set(context);
	}

	protected String getUserIPAddress(HttpServletRequest request) {
		return request.getRemoteAddr();
	}

	@Override
	public void onLoginSuccess(HttpServletRequest request,
			HttpServletResponse response,
			Authentication successfulAuthentication) {
		try {
			this.setContext(request);

			super.onLoginSuccess(request, response, successfulAuthentication);

		} finally {
			this.setContext(null);
		}
	}

	@Override
	protected String makeTokenSignature(long tokenExpiryTime, String username,
			String password) {
		return DigestUtils
				.md5DigestAsHex((username + ":" + tokenExpiryTime + ":"
						+ password + ":" + getKey() + ":" + getUserIPAddress(getContext()))
						.getBytes());
	}

	@Override
	protected void setCookie(String[] tokens, int maxAge,
			HttpServletRequest request, HttpServletResponse response) {
		String[] tokensWithIPAddress = Arrays.copyOf(tokens, tokens.length + 1);
		tokensWithIPAddress[tokensWithIPAddress.length - 1] = getUserIPAddress(request);

		super.setCookie(tokensWithIPAddress, maxAge, request, response);
	}

	@Override
	protected UserDetails processAutoLoginCookie(String[] cookieTokens,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			setContext(request);
			String ipAddressToken = cookieTokens[cookieTokens.length - 1];
			if (!getUserIPAddress(request).equals(ipAddressToken)) {

				throw new InvalidCookieException(
						"Cookie IP Address did  not contain a matching Ip(contained'"
								+ ipAddressToken + "')");

			}
			return super.processAutoLoginCookie(
					Arrays.copyOf(cookieTokens, cookieTokens.length - 1),
					request, response);
		} finally {
			setContext(null);
		}

	}

}
