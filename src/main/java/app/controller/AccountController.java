package app.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import app.App;
import app.domain.Account;
import app.service.AccountService;
import app.web.util.WebUtil;

@Controller
public class AccountController {

	@Autowired
	App app;

	@Autowired
	AccountService accountService;

	@RequestMapping(value = "/auth", method = RequestMethod.GET)
	public ModelAndView auth(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		String code = request.getParameter("code");
		if (code == null || code.isEmpty()) {
			return mav;
		}
		Account account = app.getAccount(code);
		if (account == null) {
			return mav;
		}
		accountService.update(account);
		WebUtil.setAccountId(request, account.getId());
		mav.setViewName("redirect:" + request.getParameter("state"));
		return mav;
	}

}
