package app.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.View;

import com.alibaba.fastjson.support.spring.FastJsonJsonView;

public final class Convert {

	public static View to(HttpServletRequest request) {
		View view = new FastJsonJsonView();
		return view;
	}
	
}
