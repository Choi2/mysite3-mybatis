package com.cafe24.mysite.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.cafe24.mysite.controller.BoardController;
import com.cafe24.mysite.dto.JSONResult;
import com.fasterxml.jackson.databind.ObjectMapper;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	private static final Log LOG = LogFactory.getLog(BoardController.class );
	
	@ExceptionHandler(Exception.class)
	public void handlerException(
			HttpServletRequest request,
			HttpServletResponse response,
			Exception e) throws Exception {
		//1. 로깅
		
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		LOG.warn(new PrintWriter(errors));
	//	request.setAttribute("errors", errors.toString());
		
		
		String accept = request.getHeader("accept");
		if(accept.matches(".*application/json.*")) {
			//2. 실패 JSON 응답
			JSONResult jsonResult = JSONResult.fail(errors.toString());
			String json = new ObjectMapper().writeValueAsString(jsonResult);
			
			response.setContentType("application/json; chatset=utf-8");
			response.getWriter().println(json);
		} else {
			//2. 사과 페이지
			request.getRequestDispatcher("/WEB-INF/views/errors/404.jsp").
			forward(request,response);
		}
		
	}
}
