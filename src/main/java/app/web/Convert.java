package app.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

public final class Convert {

	public static View toView(HttpServletRequest request) {
		View view = new MappingJackson2JsonView();
		return view;
	}
}
