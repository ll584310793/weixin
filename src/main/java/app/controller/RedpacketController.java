package app.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import app.App;
import app.domain.Account;
import app.domain.Redpacket;
import app.service.AccountService;
import app.service.RedpacketService;
import app.web.Convert;
import app.web.Session;

@Controller
public class RedpacketController {

	@Autowired
	App app;

	@Autowired
	RedpacketService redpacketService;

	@Autowired
	AccountService accountService;

	@RequestMapping(value = "/redpackets", method = RequestMethod.GET)
	public String index() {
		return "/redpackets/index";
	}

	@RequestMapping(value = "/accounts/{id}/redpackets", method = RequestMethod.GET)
	public ModelAndView redpackets(@PathVariable long id) {
		ModelAndView mav = new ModelAndView("accounts/redpackets");
		mav.addObject("account", accountService.get(id));
		List<Map<String, Object>> redpackets = redpacketService.jdbcTemplate
				.queryForList("SELECT * FROM redpacket WHERE account_id=? ", id);
		mav.addObject("redpackets", redpackets);
		return mav;
	}

	@RequestMapping(value = "/redpackets/{id}", method = RequestMethod.GET)
	public ModelAndView show(@PathVariable Long id, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/redpackets/show");
		mav.addObject("wx_config", app.generateWxConfig(request.getRequestURL().toString()));
		mav.addObject("redpacket", redpacketService.get(id));
		return mav;
	}

	@RequestMapping(value = "/redpackets/{id}/generate", method = RequestMethod.GET)
	public ModelAndView open(@PathVariable Long id, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		mav.setView(Convert.to(request));
		String mode = request.getParameter("mode");
		mav.addObject("account_redpacket", redpacketService.generate(id, Session.getAccountId(request), mode));
		return mav;
	}

	@RequestMapping(value = "/redpackets/new", method = RequestMethod.GET)
	public ModelAndView _new(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/redpackets/new");
		mav.addObject("wx_config", app.generateWxConfig(request.getRequestURL().toString()));
		return mav;
	}

	@RequestMapping(value = "/redpackets", method = RequestMethod.POST)
	public ModelAndView create(HttpServletRequest request,
			@RequestParam(value = "count", required = false) String count,
			@RequestParam(value = "amount", required = false) String amount,
			@RequestParam(value = "wishing", required = false) String wishing
	// ,@RequestParam(value = "head", required = false) MultipartFile head
	) {

		Account account = accountService.get(Session.getAccountId(request));
		// String path =
		// request.getSession().getServletContext().getRealPath("upload");
		// String fileName = head.getOriginalFilename();
		// System.out.println(path);
		// File targetFile = new File(path, fileName);
		// if (!targetFile.exists()) {
		// targetFile.mkdirs();
		// }
		// try {
		// head.transferTo(targetFile);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }

		Redpacket r = new Redpacket();
		r.setAccount(account);
		r.setNickname(account.getNickname());
		r.setHead(account.getHead());
		r.setWishing(wishing);
		r.setAmount(2.0);
		r.setCount(2L);
		// r.setAmount(Double.parseDouble(amount));
		// r.setCount(Long.parseLong(count));
		redpacketService.add(r);

		ModelAndView mav = new ModelAndView("redirect:/pay/" + r.getId());
		return mav;
	}

}
