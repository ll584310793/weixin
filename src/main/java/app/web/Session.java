package app.web;

import javax.servlet.http.HttpServletRequest;

public class Session {

	public static Long getAccountId(HttpServletRequest request) {
		return (Long) request.getSession().getAttribute("account_id");
	}

	public static void setAccountId(HttpServletRequest request, Long account_id) {
		request.getSession().setAttribute("account_id", account_id);
	}

}
