package com.cafe24.mysite.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.cafe24.mysite.vo.UserVo;



public class AuthHandlerMethodResolver implements HandlerMethodArgumentResolver {
		
	@Override
	public Object resolveArgument(
			MethodParameter parameter, 
			ModelAndViewContainer mavContainer, 
			NativeWebRequest webRequest,
			WebDataBinderFactory binderFactory) throws Exception {
		
		System.out.println("AuthHandlerMethodResolver:resolveArgument");
		
		if(supportsParameter(parameter) == false) {
			return WebArgumentResolver.UNRESOLVED;
		}
		
		// @AuthUser가 붙어 있고 파라미터 타입이 UserVo
		HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
		HttpSession session = request.getSession(true);
		if(session == null) {
			return null;
		}
					
		return session.getAttribute("authUser");
		
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		System.out.println("AuthHandlerMethodResolver:supportsParameter");
		
		//1. @AuthUser 가 붙어 있는지 확인
		AuthUser authUser = 
				parameter.getParameterAnnotation(AuthUser.class);
		
		//2. @AuthUser가 안 붙어 있음.
		if(authUser == null) {
			return false;
		}
		
		//3. Type이 UserVo가 아님
		if(parameter.getParameterType().equals(UserVo.class) == false) {
			return false;
		}
		
		return true;
	}

}
