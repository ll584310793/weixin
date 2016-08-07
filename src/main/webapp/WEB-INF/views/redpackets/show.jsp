<!DOCTYPE html>
<html lang="en">
<head>
<%@ page pageEncoding="UTF-8"%>
<%@ include file="../imports.jsp"%>
<title>${redpacket.nickname }的红包&nbsp;&nbsp;(${redpacket.amount }元)</title>
<script type="text/javascript">
	wx.config({
		debug : false,
		appId : '${wx_config.appId}',
		timestamp : '${wx_config.timestamp}',
		nonceStr : '${wx_config.noncestr}',
		signature : '${wx_config.signature}',
		jsApiList : [ "onMenuShareAppMessage", "onMenuShareTimeline" ]
	});
	window.wx_config = {
		"share" : {
			"title" : '${redpacket.nickname }的红包(${redpacket.amount })',
			"desc" : '问律师-关注领红包',
			"imgUrl" : 'http://wenlvs.vip.natapp.cn/weixin/images/logo.jpg',
			"link" : window.location.href
		}
	};
	wx.ready(function() {
		wx.onMenuShareAppMessage(wx_config.share);
		wx.onMenuShareTimeline(wx_config.share);
	});
</script>
</head>
<body>
	<div class="weui_msg">
		<div class="weui_icon_area">
			<i><img width="100" height="100" src="${redpacket.head }"></i>
			<h3 class="weui_msg_title">${redpacket.nickname }的红包</h3>
			<h3 class="weui_msg_desc">${redpacket.wishing }</h3>
		</div>
		<div class="weui_text_area">
			<p class="weui_msg_desc">关注问律师,下载APP</p>
			<p class="weui_msg_desc">随时随地问律师</p>
			<div class="weui_msg_title">
				<font size="25px">${redpacket.amount }</font>元
			</div>
			<br />
			<div class="weui_opr_area">
				<button id="open_redpacket" class="weui_btn weui_btn_warn">抢红包</button>
			</div>
		</div>
		<br />
		<div class="weui_extra_area">
			<a href="${ctx }/redpackets/${redpacket.id}/redpackets">查看大家手气</a>
		</div>
		<br />
		<div class="weui_msg_desc"></div>
	</div>
	<script type="text/javascript">
		$("#open_redpacket").click(function() {
			$('#open_redpacket').attr('disabled', "true");
			$.ajax({
				url : '${ctx}/redpackets/${redpacket.id }/generate',
				type : 'get',
				data : {"mode":"open"},
				dataType : 'json',
				success : function(r) {
					alert("成功领取:" + r.amount + "红包,问律师马上发!");
				}
			});
		});
	</script>
</body>
</html>