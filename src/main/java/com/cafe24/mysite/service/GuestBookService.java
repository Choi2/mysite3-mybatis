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
	
	public boolean delete(GuestBookVo vo) {
		return guestBookDao.delete(vo);
	}

	public GuestBookVo insert(GuestBookVo guestBookVo) {
		GuestBookVo vo = null;
		int count = guestBookDao.insert(guestBookVo);
		System.out.println(count);
		if(count == 1) {
			vo = guestBookDao.get(guestBookVo.getNo());
		}
		
		return vo;
	}

	public List<GuestBookVo> getList(long no) {
		return guestBookDao.getList(no);
	}
}
