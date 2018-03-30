package com.cafe24.mysite.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cafe24.mysite.vo.UserVo;

@Repository
public class UserDao {
	
	@Autowired
	private SqlSession sqlSession;

	public UserVo get(Long no) {
		return sqlSession.selectOne("user.getByNo", no);
	}
	
	public UserVo get(UserVo vo) {
		return sqlSession.selectOne("user.getByEmailAndPassword", vo);
	}
	
	@Transactional
	public boolean update(UserVo vo) {
		return (sqlSession.update("user.update", vo) == 1);
	}
	
	public boolean insert(UserVo vo) {
		return (sqlSession.insert("user.insert", vo) == 1);
	}
	
	public UserVo get(String email) {
		return sqlSession.selectOne("user.getByEmail", email);
	}
}
