package com.cafe24.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafe24.mysite.repository.GuestBookDao;
import com.cafe24.mysite.vo.GuestBookVo;

@Service
public class GuestBookService {

	@Autowired
	private GuestBookDao guestBookDao;
	
	public List<GuestBookVo> getList() {
		return guestBookDao.getList();
	}
	
	public int delete(long no) {
		return guestBookDao.delete(no);
	}

	public void insert(GuestBookVo vo) {
		guestBookDao.insert(vo);
	}
}
