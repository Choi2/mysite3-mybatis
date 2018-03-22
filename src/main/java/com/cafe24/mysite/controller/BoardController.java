package com.cafe24.mysite.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cafe24.mysite.common.Pager;
import com.cafe24.mysite.service.BoardService;
import com.cafe24.mysite.vo.BoardVo;
import com.cafe24.mysite.vo.CommentVo;
import com.cafe24.mysite.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@RequestMapping("")
	public String list(Model model,  Pager pager) {
		
		List<BoardVo> list = boardService.getBoardList(pager);
		model.addAttribute("list", list);
		model.addAttribute("pager", pager);

		return "board/list";
	}
	
	@RequestMapping("/{no}")
	public String view(Model model, @PathVariable("no") long no) {
		
		boardService.updateReadCount(no);
		BoardVo vo = boardService.getOneBoard(no);
		List<CommentVo> commentList = boardService.getCommentList(no);
		
		model.addAttribute("vo", vo);
		model.addAttribute("commentList", commentList);
		
		return "board/view";
	}
	
	@RequestMapping(value="/write", method = RequestMethod.GET)
	public String write(HttpSession session) {
		
		UserVo vo = (UserVo) session.getAttribute("authUser");
		
		if(vo == null) {
			return "user/login";
		}
		
		return "board/write";
	}
	
	@RequestMapping(value="/write", method = RequestMethod.POST)
	public String write(HttpSession session, 
			@ModelAttribute BoardVo vo,
			Model model) {
		UserVo user = (UserVo) session.getAttribute("authUser");
		
		boardService.arrangeList(vo);
		boardService.writeBoard(vo, user.getNo());

		return "redirect:/board";
	}
	
	
	@RequestMapping(value="/modify/{no}", method = RequestMethod.GET)
	public String modify(HttpSession session, 
			@PathVariable ("no") long no,
			Model model) {
		
		UserVo user = (UserVo) session.getAttribute("authUser");
		BoardVo vo = boardService.getOneBoard(no);
		
		if(user == null || user.getNo() != vo.getUserNo()) {
			return "user/login";
		}
		
		model.addAttribute("vo", vo);
		return "board/modify";
	}
	
	@RequestMapping(value="/modify", method = RequestMethod.POST)
	public String modify(HttpSession session, 
			@ModelAttribute BoardVo vo) {
		boardService.modifyBoard(vo);
		return "redirect:/board";
	}
	
	@RequestMapping(value="/delete/{no}", method = RequestMethod.GET)
	public String delete(HttpSession session, 
			@PathVariable long no) {
		
		UserVo user = (UserVo) session.getAttribute("authUser");
		BoardVo vo = boardService.getOneBoard(no);
		
		if(user == null || user.getNo() != vo.getUserNo()) {
			return "user/login";
		}
		
		boardService.deleteBoard(no);
		return "redirect:/board";
	}
}
