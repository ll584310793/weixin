<!DOCTYPE html>
<html lang="en">
<head>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../imports.jsp"%>
<title>问律师红包-微信安全支付</title>
<script type="text/javascript">
	wx.config({
		debug : false,
		appId : "${wx_config.appId}",
		timestamp : "${wx_config.timestamp}",
		nonceStr : "${wx_config.noncestr}",
		signature : "${wx_config.signature}",
		jsApiList : [ "chooseWXPay" ]
	});
	function pay() {
		wx.chooseWXPay({
			timestamp : "${wx_pay_config.timeStamp}",
			nonceStr : "${wx_pay_config.nonceStr}",
			package : "${wx_pay_config['package']}",
			signType : "${wx_pay_config.signType}",
			paySign : "${wx_pay_config.paySign}",
			success : function(res) {
				//JS API的返回结果
				//get_brand_wcpay_request：ok仅在用户成功完成支付时返回。由于前端交互复杂，
				//get_brand_wcpay_request：cancel或者
				//get_brand_wcpay_request：fail可以统一处理为用户遇到错误或者主动放弃，不必细化区分。
				if (res.err_msg == "get_brand_wcpay_request：ok") {
				}
			}
		});
	}
	wx.ready(function() {
		pay();
	});
</script>
</head>
<body>
	<div class="weui_msg">
		<div class="weui_icon_area">
			<i><img width="100" height="100" src="${redpacket.head }"></i>
		</div>
		<div class="weui_text_area">
			<h3 class="weui_msg_title">${redpacket.nickname }的红包</h3>
			<h3 class="weui_msg_desc">${redpacket.wishing }</h3>
			<p class="weui_msg_desc">关注问律师,下载APP</p>
			<p class="weui_msg_desc">随时随地问律师</p>
			<div class="weui_msg_title">
				<font size="25px">${redpacket.amount }</font>元
			</div>
			<br />
			<div class="weui_msg_desc">
				<button id="pay_button" onclick="pay();"
					class="weui_btn weui_btn_warn">立即支付</button>
			</div>
		</div>
		<br />
		<div class="weui_extra_area">
			<a href="${ctx }/redpackets/${redpacket.id}">查看详情</a>
		</div>
		<br />
		<div class="weui_msg_desc"></div>
	</div>
</body>
</html>