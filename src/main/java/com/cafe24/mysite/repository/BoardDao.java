package com.cafe24.mysite.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cafe24.mysite.vo.BoardVo;
import com.cafe24.mysite.vo.CommentVo;

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

	public List<BoardVo> getAllList(int currentDataSizePerPage, String word) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("word", word);
		map.put("currentDataSizePerPage", currentDataSizePerPage);
		return sqlSession.selectList("board.getAllList", map);
	}

	public int update(BoardVo vo) {
		return sqlSession.update("board.update", vo);
	}

	public int updateReadCount(long no) {
		return sqlSession.update("board.updateReadCount", no);
	}

	public int getPageSize(int currentGroupPage, String word) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("word", word);
		map.put("currentGroupPage", currentGroupPage);
/*		System.out.println("currentGroupPage = " + currentGroupPage);
		System.out.println("word = " + word);*/
		return sqlSession.selectOne("board.getPageSize", map);
	}

	public long getMaxOtherNo(long groupNo) {
		return sqlSession.selectOne("board.getMaxOtherNo", groupNo);
	}

	public List<CommentVo> getCommentList(long no) {
		return sqlSession.selectOne("board.getCommentList", no);
	}

}
