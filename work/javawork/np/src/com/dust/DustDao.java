package com.dust;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.frame.Dao;
import com.mapper.DustMapper;
import com.vo.DustVO;

@Repository("dsdao")
public class DustDao implements Dao<String, DustVO> {

	@Autowired
	DustMapper dm;

	@Override
	public void insert(DustVO v) throws Exception {
		dm.insert(v);
	}
	
	@Override
	public void update(DustVO v) throws Exception{
		dm.update(v);		
	}
	
	@Override
	public DustVO select(String k) throws Exception {
		return dm.select(k);
	}

	@Override
	public ArrayList<DustVO> select() throws Exception {
		return dm.selectall();
	}
	
	

}
