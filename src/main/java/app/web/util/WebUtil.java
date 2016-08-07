package app.web.util;

import javax.servlet.http.HttpServletRequest;

public class WebUtil {

	public static Long getAccountId(HttpServletRequest request) {
		Object o = request.getSession().getAttribute("account_id");
		return o != null ? Long.parseLong(o.toString()) : null;
	}

	public static void setAccountId(HttpServletRequest request, Long account_id) {
		request.getSession().setAttribute("account_id", account_id);
	}

}
