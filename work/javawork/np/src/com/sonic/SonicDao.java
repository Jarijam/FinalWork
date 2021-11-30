package com.sonic;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.frame.Dao;
import com.mapper.SonicMapper;
import com.vo.SonicVO;

@Repository("sndao")
public class SonicDao implements Dao<String, SonicVO> {

	@Autowired
	SonicMapper sm;

	@Override
	public void insert(SonicVO v) throws Exception {
		sm.insert(v);
	}
	
	@Override
	public void update(SonicVO v) throws Exception{
		sm.update(v);		
	}
	
	@Override
	public SonicVO select(String k) throws Exception {
		return sm.select(k);
	}

	@Override
	public ArrayList<SonicVO> select() throws Exception {
		return sm.selectall();
	}
	
	

}
