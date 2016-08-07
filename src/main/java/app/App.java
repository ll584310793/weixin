package app;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import app.domain.Account;
import weixin.popular.api.PayMchAPI;
import weixin.popular.api.SnsAPI;
import weixin.popular.api.TicketAPI;
import weixin.popular.api.TokenAPI;
import weixin.popular.bean.paymch.Sendredpack;
import weixin.popular.bean.paymch.SendredpackResult;
import weixin.popular.bean.paymch.Transfers;
import weixin.popular.bean.paymch.TransfersResult;
import weixin.popular.bean.paymch.Unifiedorder;
import weixin.popular.bean.paymch.UnifiedorderResult;
import weixin.popular.bean.sns.SnsToken;
import weixin.popular.bean.ticket.Ticket;
import weixin.popular.bean.token.Token;
import weixin.popular.bean.user.User;
import weixin.popular.client.LocalHttpClient;
import weixin.popular.util.SignatureUtil;

public class App {

	private static Logger log = LoggerFactory.getLogger(App.class);

	public String app_id;// 应用id
	public String app_name; // 应用名称
	public String app_secret;// 应用app secret
	public String api_key;// 应用api key

	public String mch_id; // 商户id
	public String cert;// 证书位置

	public String token;// app token
	public String jsapi_ticket;// app jsapi_ticket
	public String ip;// app所在ip

	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}

	public void setApp_name(String app_name) {
		this.app_name = app_name;
	}

	public void setApp_secret(String app_secret) {
		this.app_secret = app_secret;
	}

	public void setApi_key(String api_key) {
		this.api_key = api_key;
	}

	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}

	public void setCert(String cert) {
		this.cert = cert;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void init() {
		log.info("-app.init-");
		log.info("app_id : " + this.app_id);
		log.info("app_name : " + this.app_name);
		log.info("app_secret : " + this.app_secret);
		log.info("mch_id : " + this.mch_id);
		log.info("api_key : " + this.api_key);
		log.info("cert : " + this.cert);
		log.info("ip : " + this.ip);
		this.cert = this.getClass().getResource(this.cert).getPath();
		LocalHttpClient.initMchKeyStore(this.mch_id, this.cert);
		log.info("init cert : " + this.cert);
	}

	public void update() {
		log.info("updating....");
		// get token
		Token token = TokenAPI.token(this.app_id, this.app_secret);
		if (token != null && token.getAccess_token().isEmpty()) {
			log.error("get token faild", token.getErrcode(), token.getErrmsg());
			return;
		}
		this.token = token.getAccess_token();
		log.info("update token : " + this.token);
		// get ticket
		Ticket ticket = TicketAPI.ticketGetticket(this.token);
		if (ticket != null && ticket.getTicket().isEmpty()) {
			log.error("get ticket faild", ticket.getErrcode(), ticket.getErrmsg());
			return;
		}
		this.jsapi_ticket = ticket.getTicket();
		log.info("update jsapi_ticket : " + this.jsapi_ticket);
	}

	public Map<String, String> generateWxConfig(String url) {
		Map<String, String> map = new HashMap<>(6);
		map.put("noncestr", UUID.randomUUID().toString());
		map.put("jsapi_ticket", this.jsapi_ticket);
		map.put("timestamp", System.currentTimeMillis() / 1000 + "");
		map.put("url", url);
		map.put("signature", SignatureUtil.generatePaySign(map, null));
		map.put("appId", this.app_id);
		return map;
	}

	public Map<String, String> generateWxPayConfig(String prepay_id) {
		Map<String, String> map = new HashMap<>(6);
		map.put("appId", this.app_id);
		map.put("timeStamp", System.currentTimeMillis() / 1000 + "");
		map.put("nonceStr", UUID.randomUUID().toString().replace("-", ""));
		map.put("package", "prepay_id=" + prepay_id);
		map.put("signType", "MD5");
		map.put("paySign", SignatureUtil.generateSign(map, this.api_key));
		return map;
	}

	public Map<String, String> unifiedorder(String openid, String body, double total_fee, String spbill_create_ip,
			String notify_url) {
		Unifiedorder o = new Unifiedorder();
		o.setOpenid(openid);// 微信用户公开id
		o.setTotal_fee((int)(total_fee*100) + "");// 总金额
		o.setSpbill_create_ip(spbill_create_ip);// 发放ip
		o.setNotify_url(notify_url);// 回掉地址
		o.setBody(body);// 发放内容

		o.setAppid(this.app_id);// 应用id
		o.setMch_id(this.mch_id);// 商户id
		o.setTrade_type("JSAPI");// 支付类型
		o.setDevice_info("WEB");// 终端设备号(门店号或收银设备ID)，注意：PC网页或公众号内支付请传"WEB"
		String uuid = UUID.randomUUID().toString().replace("-", "");
		o.setOut_trade_no(uuid);// 商户系统内部的订单号,32个字符内、可包含字母, 其他说明见商户订单号
		o.setNonce_str(uuid);// 随机字符串，不长于32位。推荐随机数生成算法

		// gen Prepay_id
		UnifiedorderResult r = PayMchAPI.payUnifiedorder(o, this.api_key);
		log.info("return code : " + r.getReturn_code());
		log.info("retrrn msg  : " + r.getReturn_msg());
		log.info("result code : " + r.getResult_code());

		return this.generateWxPayConfig(r.getPrepay_id());
	}

	public int transfer(String openid, int amount, String desc) {
		Transfers o = new Transfers();

		o.setMch_appid(this.app_id); // appid
		o.setMchid(this.mch_id); // 商户id

		String uuid = UUID.randomUUID().toString().replace("-", "");
		o.setNonce_str(uuid); // 字符串
		o.setPartner_trade_no(uuid);// 订单id
		o.setCheck_name("NO_CHECK");// 无需检验实名制名字校验
		o.setSpbill_create_ip(this.ip);// 转账ip地址

		o.setOpenid(openid); // 转账到用户id
		o.setAmount(amount + "");// 转账金额
		o.setDesc(desc);// 转账描述

		TransfersResult r = PayMchAPI.mmpaymkttransfersPromotionTransfers(o, this.api_key);
		log.info("return code : " + r.getReturn_code());
		log.info("retrrn msg  : " + r.getReturn_msg());
		log.info("result code : " + r.getResult_code());

		return 0;
	}

	public int sendRedpacket(String openid, double amount, String action_name, String wishing) {
		Sendredpack o = new Sendredpack();

		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String d1 = sdf.format(date);
		SimpleDateFormat sdf2 = new SimpleDateFormat("ddhhmmssss");
		String d2 = sdf2.format(date);
		String billno = this.mch_id + d1 + d2;

		o.setMch_billno(billno);

		o.setMch_id(this.mch_id); // 商户id
		o.setClient_ip(this.ip); // ip
		o.setWxappid(this.app_id); // appid
		o.setSend_name(this.app_name);
		o.setAct_name(action_name); // 活动名称
		o.setRe_openid(openid);// openid
		o.setTotal_amount((int) (amount * 100)); // 总价钱
		o.setTotal_num(1);// 总个数
		o.setWishing(wishing);// 活动名称

		o.setRemark("无");

		String uuid = UUID.randomUUID().toString().replace("-", "");
		o.setNonce_str(uuid);

		SendredpackResult r = PayMchAPI.mmpaymkttransfersSendredpack(o, this.api_key);
		if (r == null) {
			log.info("发送红包失败,结果集为空.");
			return -1;
		}
		log.info("return code : " + r.getReturn_code());
		log.info("retrrn msg  : " + r.getReturn_msg());
		log.info("result code : " + r.getResult_code());

		String code = "SUCCESS";

		if (code.equals(r.getReturn_code()) && code.equals(r.getResult_code())) {// 半成功
			String result_code = r.getResult_code();
			if (code.equals(result_code)) {// 完全成功
				return 1;
			}
			return 0;
		}
		return -1;// 失败
	}

	public String connectOauth2Authorize(String redirect_uri, String state) {
		return SnsAPI.connectOauth2Authorize(this.app_id, redirect_uri, true, state);
	}

	public Account getAccount(String code) {
		/* get access_token by code */
		SnsToken snsToken = SnsAPI.oauth2AccessToken(this.app_id, this.app_secret, code);
		if (snsToken.getAccess_token() != null) {
			/* get userinfo by access_token */
			User user = SnsAPI.userinfo(snsToken.getAccess_token(), snsToken.getOpenid(), "");

			Account account = new Account();
			account.setNickname(user.getNickname());
			account.setHead(user.getHeadimgurl().replaceFirst("/0", "/64"));
			account.setOpenid(user.getOpenid());
			account.setAccess_token(snsToken.getAccess_token());
			account.setRefresh_token(snsToken.getRefresh_token());
			account.setExpires_in(snsToken.getExpires_in());
			return account;
		}
		return null;
	}

}
