<!DOCTYPE html>
<html lang="en">
<head>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../import.jsp"%>
<title>问律师-红包-支付结果</title>
</head>
<body>
	<div class="weui_msg">
		<div class="weui_icon_area">
			<i class="weui_icon_success weui_icon_msg"></i>
		</div>
		<div class="weui_text_area">
			<h2 class="weui_msg_title">操作成功</h2>
			<p class="weui_msg_desc">内容详情，可根据实际需要安排</p>
		</div>
		<div class="weui_opr_area">
			<p class="weui_btn_area">
				<a class="weui_btn weui_btn_primary" href="${ctx}/redpackets/new">再发一个红包</a>
			</p>
		</div>
		<div class="weui_extra_area">
			<a href="${ctx}/redpackets/${redpacket.id}">查看详情</a>
		</div>
		<br />
		<div class="weui_msg_desc"></div>
	</div>
</body>
</html>