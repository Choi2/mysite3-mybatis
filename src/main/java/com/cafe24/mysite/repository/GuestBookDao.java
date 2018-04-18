package com.cafe24.mysite.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cafe24.mysite.vo.GuestBookVo;

@Repository
public class GuestBookDao {

	@Autowired
	private SqlSession sqlSession;

	public GuestBookVo get(Long no) {
		return sqlSession.selectOne("guestbook.getByNo", no);
	}
	
	public boolean delete(GuestBookVo vo) {
/*		Map <String, Object> map = new HashMap<String, Object>();
		map.put("no", vo.getNo());
		map.put("password", vo.getPassword());*/
		boolean result = sqlSession.delete("guestbook.delete", vo) == 1;
		return result;
	}

	public int insert(GuestBookVo vo) {
		return sqlSession.insert("guestbook.insert",vo);
	}

	public List<GuestBookVo> getList(long no) {
		return sqlSession.selectList("guestbook.getList", no);
	}

}
