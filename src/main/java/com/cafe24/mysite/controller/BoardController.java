package com.cafe24.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.cafe24.mysite.interceptor.AuthUser;
import com.cafe24.mysite.service.BoardService;
import com.cafe24.mysite.vo.BoardVo;
import com.cafe24.mysite.vo.CommentVo;
import com.cafe24.mysite.vo.Pager;
import com.cafe24.mysite.vo.UserVo;

@Controller
@RequestMapping("/board")
@SessionAttributes("authUser") //authUser
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@RequestMapping("")
	public String list(Model model,  Pager pager) {

		List<BoardVo> list = boardService.getAllBoardList(pager);
		pager = boardService.getPager();
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
	
	@AuthUser
	@RequestMapping(value="/write", method = RequestMethod.GET)	
	public String write(@ModelAttribute UserVo vo) {
		return "board/write"; //check
	}
	
	@RequestMapping(value="/write", method = RequestMethod.POST)
	public String write(
			@ModelAttribute UserVo vo,
			@ModelAttribute BoardVo board,
			Model model) {

		boardService.writeBoard(board, vo.getNo());  //해당 회원이 쓴 글 삽입

		return "redirect:/board";
	}
	
	
	@RequestMapping(value="/modify/{no}", method = RequestMethod.GET)
	public String modify(
			@ModelAttribute("authUser") UserVo authUser,
			@PathVariable ("no") long no,
			Model model) {
		
		BoardVo board = boardService.getOneBoard(no);
		
		if(authUser == null || authUser.getNo() != board.getUserNo()) {
			return "user/login";
		}
		
		model.addAttribute("vo", board);
		return "board/modify";
	}
	
	@RequestMapping(value="/modify", method = RequestMethod.POST)
	public String modify(@ModelAttribute BoardVo vo) {
		boardService.modifyBoard(vo);
		return "redirect:/board";
	}
	
	@RequestMapping(value="/delete/{no}", method = RequestMethod.GET)
	public String delete(
			@ModelAttribute("authUser") UserVo authUser,
			@PathVariable long no) {
		BoardVo vo = boardService.getOneBoard(no);
		
		if(authUser == null || authUser.getNo() != vo.getUserNo()) {
			return "user/login";
		}
		
		boardService.deleteBoard(no);
		return "redirect:/board";
	}
}
