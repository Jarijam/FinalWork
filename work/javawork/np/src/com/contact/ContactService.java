package com.contact;

import java.util.ArrayList;

import javax.annotation.Resource;

import com.frame.Dao;
import com.frame.Service;
import com.vo.ContactVO;
import com.vo.CoordinateVO;
@org.springframework.stereotype.Service("ctservice")
public class ContactService implements Service<String, ContactVO> {

	@Resource(name="ctdao")
	Dao<String,ContactVO> dao;
	
	@Override
	public void register(ContactVO v) throws Exception {
		dao.insert(v);
	}
	
	@Override
	public void modify(ContactVO v) throws Exception {
		dao.update(v);
		
	}
	
	@Override
	public ContactVO get(String k) throws Exception {
		return dao.select(k);
	}

	@Override
	public ArrayList<ContactVO> get() throws Exception {
		return dao.select();
	}



}
