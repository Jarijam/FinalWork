package com.dust;

import java.util.ArrayList;

import javax.annotation.Resource;

import com.frame.Dao;
import com.frame.Service;
import com.vo.DustVO;
@org.springframework.stereotype.Service("dsservice")
public class DustService implements Service<String, DustVO> {

	@Resource(name="dsdao")
	Dao<String,DustVO> dao;
	
	@Override
	public void register(DustVO v) throws Exception {
		dao.insert(v);
	}
	
	@Override
	public void modify(DustVO v) throws Exception {
		dao.update(v);
		
	}
	
	@Override
	public DustVO get(String k) throws Exception {
		return dao.select(k);
	}

	@Override
	public ArrayList<DustVO> get() throws Exception {
		return dao.select();
	}



}
