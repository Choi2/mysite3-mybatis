package com.cafe24.mysite.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cafe24.mysite.vo.BoardVo;
import com.cafe24.mysite.vo.CommentVo;
import com.cafe24.mysite.vo.Pager;
	
@Repository
public class BoardDao {
	
	@Autowired
	private SqlSession sqlSession;

	public BoardVo getOneList(Long no) {
		return sqlSession.selectOne("board.getOneList", no);
	}

	public CommentVo getComment(Long no) {
		return sqlSession.selectOne("board.getComment", no);
	}

	public int delete(long no) {
		return sqlSession.update("board.delete", no);
	}

	public int insert(BoardVo vo) {
		return sqlSession.insert("board.insert", vo);
	}

	public int arrangeList(BoardVo vo) {
		return sqlSession.update("board.updateArrangeList", vo);
	}

	public int insertComment(CommentVo vo) {
		return sqlSession.insert("board.comment", vo);
	}

	public List<BoardVo> getAllList(Pager pager) {
		return sqlSession.selectList("board.getAllList", pager);
	}

	public int update(BoardVo vo) {
		return sqlSession.update("board.update", vo);
	}

	public int updateReadCount(long no) {
		return sqlSession.update("board.updateReadCount", no);
	}

	public int getPageSize(Pager pager) {
		return sqlSession.selectOne("board.getPageSize", pager);
	}

	public long getMaxOtherNo(long groupNo) {
		return sqlSession.selectOne("board.getMaxOtherNo", groupNo);
	}

	public List<CommentVo> getCommentList(long no) {
		return sqlSession.selectOne("board.getCommentList", no);
	}

}
