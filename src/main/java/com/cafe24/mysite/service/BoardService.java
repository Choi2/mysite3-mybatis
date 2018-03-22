package com.cafe24.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafe24.mysite.common.Pager;
import com.cafe24.mysite.repository.BoardDao;
import com.cafe24.mysite.vo.BoardVo;
import com.cafe24.mysite.vo.CommentVo;

@Service
public class BoardService {

	@Autowired
	private BoardDao boardDao;

	public List<BoardVo> getBoardList(Pager pager) {
		pager.calculatePage(pager.getPage(), pager.getWord());
		return boardDao.getAllList(pager.getCurrentDataSizePerPage(), pager.getWord());
	}

	public int getCurrentGroupPage(int currentGroupPage, String word) {
		return boardDao.getPageSize(currentGroupPage, word);
	}

	public void updateReadCount(long no) {
		boardDao.updateReadCount(no);
	}

	public BoardVo getOneBoard(long no) {
		return boardDao.getOneList(no);
	}

	public List<CommentVo> getCommentList(long no) {
		return boardDao.getCommentList(no);
	}

	public void writeBoard(BoardVo vo, Long userNo) {
		vo.setUserNo(userNo);
		boardDao.insert(vo);
	}

	public void modifyBoard(BoardVo vo) {
		boardDao.update(vo);
	}

	public void deleteBoard(long no) {
		boardDao.delete(no);
	}

	public void arrangeList(BoardVo vo) {
		boardDao.arrangeList(vo);
	}

}
