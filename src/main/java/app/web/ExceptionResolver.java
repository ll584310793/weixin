package app.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

public class ExceptionResolver extends ExceptionHandlerExceptionResolver {

	private static Logger logger = Logger.getLogger(ExceptionResolver.class);

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		logger.error("Catch Exception: ", ex);
		ModelAndView mav = new ModelAndView("error");
		response.setStatus(500);
		mav.setView(Convert.toView(request));
		mav.addObject("exception", ex.getMessage());
		return mav;
	}

}
