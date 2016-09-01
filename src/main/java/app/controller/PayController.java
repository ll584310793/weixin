package app.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import app.App;
import app.domain.Redpacket;
import app.service.RedpacketService;

@Controller
@RequestMapping("/pay")
public class PayController {

	@Autowired
	App app;

	@Autowired
	RedpacketService redpacketService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ModelAndView pay(@PathVariable Long id, HttpServletRequest request) {
		Redpacket redpacket = redpacketService.get(id);
		
		redpacket.setAmount(0.01);

		String openid = redpacket.getAccount().getOpenid();
		double total_fee = redpacket.getAmount();
		String spbill_create_ip = request.getRemoteAddr();
		String nodify_url = request.getRequestURL().append("/nodify").toString();
		String body = app.app_name + "-红包";

		ModelAndView mav = new ModelAndView("/redpacket/pay");
		mav.addObject("wx_pay_config", app.unifiedorder(openid, body, total_fee, spbill_create_ip, nodify_url));
		mav.addObject("wx_config", app.generateWxConfig(request.getRequestURL().toString()));
		mav.addObject("redpacket", redpacket);
		return mav;
	}

	@RequestMapping(value = "/{id}/nodify", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView _notify(@PathVariable Long id, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/redpacket/nodify");
		Redpacket redpacket = redpacketService.get(id);
		mav.addObject("redpacket", redpacket);
		return mav;
	}

}
