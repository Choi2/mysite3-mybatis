package com.cafe24.mysite.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cafe24.mysite.service.UserService;
import com.cafe24.mysite.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/join", method=RequestMethod.GET)
	public String join() {
		return "user/join";
	}
	
	@RequestMapping(value="/join", method=RequestMethod.POST)
	public String join(@ModelAttribute UserVo vo) {
		userService.join(vo);
		return "redirect:/user/joinsuccess";
	}
	
	@RequestMapping("/joinsuccess")
	public String joinsuccess() {
		return "user/joinsuccess";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login() {
		return "user/login";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(
			HttpSession session,
			@ModelAttribute UserVo vo, Model model) {
		UserVo authUser = userService.getUser(vo);
		if(authUser == null) {
			model.addAttribute("result", "fail");
			return "user/login";
		}
		
		session.setAttribute("authUser", authUser); //인증처리
		return "redirect:/main";
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("authUser");
		session.invalidate();
		return "redirect:/main"; 
	}
	
/*	@ExceptionHandler( UserDaoException.class )
	public String handleUserDaoException() {
		 로그 남기기 
		return "errors/404";
	}*/

	
	@RequestMapping(value="/modify", method=RequestMethod.GET)
	public String modify(HttpSession session) {
		
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		//접근 제어
		if(authUser == null) {
			return "redirect:/main";
		}

		return "user/modify";
	}
	
	@RequestMapping(value="/modify", method=RequestMethod.POST)
	public String modify(@ModelAttribute UserVo vo) {
		userService.modifyUser(vo);
		return "redirect:/user/joinsuccess";
	}
	
	
}
