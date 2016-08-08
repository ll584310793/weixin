<html lang="en">
<head>
<title>${redpacket.nickname }的红包(${redpacket.money }元)</title>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../imports.jsp"%>
</head>
<body>
	<div class="weui_msg">
		<div class="weui_icon_area">
			<i class="weui_icon_msg"> <img src="${account.head }"></i>
		</div>
		<div class="weui_text_area">
			<h3 class="weui_msg_title">${account.nickname }的红包</h3>
			<p class="weui_msg_desc">关注问律师,下载APP</p>
			<p class="weui_msg_desc">随时随地问律师</p>
			<div class="weui_msg_title">
				<font size="25px">${account.create_time }.00</font>元
			</div>
		</div>
	</div>
	<div class="weui_cells">
		<c:forEach items="${redpackets }" var="r">
			<div class="weui_cell">
				<div class="weui_cell_bd weui_cell_primary">
					<p>${r.nickname }<b>${r.money}.00</b>元
					</p>
				</div>
				<div class="weui_cell_ft">${r.create_time }</div>
			</div>
		</c:forEach>
	</div>
</body>
</html>