<!DOCTYPE html>
<html lang="en">
<head>
<title>问律师-发红包</title>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../imports.jsp"%>
<script type="text/javascript">
	wx.config({
		debug : false,
		appId : '${wx_config.appId}',
		timestamp : '${wx_config.timestamp}',
		nonceStr : '${wx_config.noncestr}',
		signature : '${wx_config.signature}',
		jsApiList : [ "onMenuShareAppMessage", "onMenuShareTimeline" ]
	});
	wx_config = {
		"share" : {
			"title" : '问律师-红包',
			"desc" : '问律师-发红包',
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
	<form action="${ctx }/redpackets" method="post">
		<div class="weui_cells weui_cells_form">
			<div class="weui_cell">
				<div class="weui_cell_hd">
					<label class="weui_label">红包个数</label>
				</div>
				<div class="weui_cell_bd weui_cell_primary">
					<input name="count" class="weui_input" type="tel" value="1" placeholder="10">
				</div>
				个
			</div>
		</div>

		<div class="weui_cells weui_cells_form">
			<div class="weui_cell">
				<div class="weui_cell_hd">
					<label class="weui_label">总金额</label>
				</div>
				<div class="weui_cell_bd weui_cell_primary">
					<input name="amount" class="weui_input" type="tel" value="0.01" placeholder="10">
				</div>
				元
			</div>
		</div>

		<div class="weui_cells weui_cells_form">
			<div class="weui_cell">
				<div class="weui_cell_bd weui_cell_primary">
					<input name="wishing" class="weui_input" type="tel"
						placeholder="恭喜发财，大吉大利！" />
				</div>
			</div>
		</div>

		<div class="weui_msg weui_msg_title">
			<font size="25px">0.00</font>元
		</div>

		<div class="weui_opr_area">
			<p class="weui_btn_area">
				<button type="submit"
					class="weui_btn weui_btn_disabled weui_btn_warn">塞钱进红包</button>
			</p>
		</div>
	</form>
</body>
</html>