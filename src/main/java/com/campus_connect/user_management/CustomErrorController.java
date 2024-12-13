package com.campus_connect.user_management;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CustomErrorController implements ErrorController {

	@RequestMapping("/error")
	public ModelAndView handleError() {
		ModelAndView modelAndView = new ModelAndView("error"); // Your error view template
		RequestAttributes attributes = RequestContextHolder.getRequestAttributes();

		if (attributes != null) {
			Integer statusCode = (Integer) attributes.getAttribute("javax.servlet.error.status_code", RequestAttributes.SCOPE_REQUEST);
			if (statusCode != null) {
				modelAndView.addObject("statusCode", statusCode);
			}
		}

		modelAndView.addObject("message", "An unexpected error occurred."); // Custom message if needed
		return modelAndView;
	}
}
