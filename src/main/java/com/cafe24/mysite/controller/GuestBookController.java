package com.cafe24.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cafe24.mysite.service.GuestBookService;
import com.cafe24.mysite.vo.GuestBookVo;

@Controller
@RequestMapping("/guestbook")
public class GuestBookController {
	
	@Autowired
	private GuestBookService guestBookService;

	@RequestMapping("")
	public String list(Model model, 
			@RequestParam(value="", required=true, defaultValue="0") Long no) {
		List<GuestBookVo> list = guestBookService.getList(no);
		model.addAttribute("list", list);
		return "guestbook/list";
	}
	
	@RequestMapping(value="/ajax", method=RequestMethod.GET)
	public String AjaxList(Model model,
			@RequestParam(value="", required=true, defaultValue="0") Long no) {
		List<GuestBookVo> list = guestBookService.getList(no);
		model.addAttribute("list", list);
		return "guestbook/index-ajax";
	}
	
	@RequestMapping(value="/insert", method=RequestMethod.POST)
	public String insert(@ModelAttribute GuestBookVo vo) {

		guestBookService.insert(vo);
		return "redirect:/guestbook/ajax";
	}
	
	@RequestMapping(value="/delete/{no}", method=RequestMethod.GET)
	public String delete(@PathVariable long no) {
		//guestBookService.delete(no);
		return "guestbook/deleteform";
	}
	
/*	@RequestMapping(value="/delete/{no}", method=RequestMethod.POST)
	public String deleteGuestBook(@PathVariable long no) {
		guestBookService.delete(no);
		return "redirect:/guestbook";
	}*/
}
