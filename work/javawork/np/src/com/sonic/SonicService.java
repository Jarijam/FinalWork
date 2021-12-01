package com.sonic;

import java.util.ArrayList;

import javax.annotation.Resource;

import com.frame.Dao;
import com.frame.Service;
import com.vo.SonicVO;
@org.springframework.stereotype.Service("snservice")
public class SonicService implements Service<String, SonicVO> {

	@Resource(name="sndao")
	Dao<String,SonicVO> dao;
	
	@Override
	public void register(SonicVO v) throws Exception {
		dao.insert(v);
	}
	
	@Override
	public void modify(SonicVO v) throws Exception {
		dao.update(v);
		
	}
	
	@Override
	public SonicVO get(String k) throws Exception {
		return dao.select(k);
	}

	@Override
	public ArrayList<SonicVO> get() throws Exception {
		return dao.select();
	}



}
