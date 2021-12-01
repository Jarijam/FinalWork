package com.contact;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.frame.Dao;
import com.mapper.ContactMapper;
import com.vo.ContactVO;

@Repository("ctdao")
public class ContactDao implements Dao<String, ContactVO> {

	@Autowired
	ContactMapper ctm;

	@Override
	public void insert(ContactVO v) throws Exception {
		ctm.insert(v);
	}
	
	@Override
	public void update(ContactVO v) throws Exception{
		ctm.update(v);		
	}
	
	@Override
	public ContactVO select(String k) throws Exception {
		return ctm.select(k);
	}

	@Override
	public ArrayList<ContactVO> select() throws Exception {
		return ctm.selectall();
	}
	
	

}
