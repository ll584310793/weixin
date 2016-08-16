package app.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import app.App;

public class AuthInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	App app;

	String[] excludes;

	public void setExcludes(String[] excludes) {
		this.excludes = excludes;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String servletPath = request.getServletPath();
		for (String s : this.excludes) {
			if (servletPath.contains(s)) {
				return true;
			}
		}
		// is login
		if (Session.getAccountId(request) != null) {
			return true;
		}
		// redirect_uri
		StringBuffer requestURL = request.getRequestURL();
		String redirect_uri = requestURL.substring(0, requestURL.lastIndexOf(servletPath)) + "/auth";
		response.sendRedirect(app.connectOauth2Authorize(redirect_uri, servletPath));
		return false;
	}
}
