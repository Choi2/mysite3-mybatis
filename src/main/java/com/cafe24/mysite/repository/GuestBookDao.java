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

	public List<GuestBookVo> getList() {
		return sqlSession.selectList("guestbook.getList");
	}
	
	public int delete(long no) {
/*		Map <String, Object> map = new HashMap<String, Object>();
		map.put("no", vo.getNo());
		map.put("password", vo.getPassword());*/
		int count = sqlSession.delete("guestbook.delete", no);
		return count;
	}

	public boolean insert(GuestBookVo vo) {
		return (sqlSession.insert("guestbook.insert",vo) == 1);
	}

	
}
