package com.mapper;

import java.util.ArrayList;

import com.vo.ContactVO;

public interface ContactMapper {
	public void insert(ContactVO obj);
	public ContactVO select(String obj);
	public ArrayList<ContactVO> selectall();
	public void update(ContactVO obj);
}



