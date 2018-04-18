package com.cafe24.mysite.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cafe24.mysite.dto.JSONResult;
import com.cafe24.mysite.service.GuestBookService;
import com.cafe24.mysite.vo.GuestBookVo;

@Controller("guestbookAPIController")
@RequestMapping("/api/guestbook")
public class GuestbookController {
	
	@Autowired
	private GuestBookService guestbookService;
	
	@ResponseBody
	@RequestMapping("/list")
	public JSONResult list(
			@RequestParam(value="", required=true, defaultValue="0") Long no) {
		List<GuestBookVo> list = guestbookService.getList(no);
		return JSONResult.success(list);
	}
	
	@ResponseBody
	@RequestMapping(value="/insert")
	public JSONResult insert(@RequestBody GuestBookVo vo) {
		GuestBookVo guestbookVo =  guestbookService.insert(vo);
		return JSONResult.success(guestbookVo);
	}
	
	@ResponseBody
	@RequestMapping(value="/delete")
	public JSONResult delete(@ModelAttribute GuestBookVo vo) {
		boolean result = guestbookService.delete(vo);
		return JSONResult.success(result ? vo.getNo() : - 1);
	}
}
