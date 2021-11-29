package com.btn;

import java.util.ArrayList;

import javax.annotation.Resource;

import com.frame.Dao;
import com.frame.Service;
import com.vo.DataVO;
import com.vo.LedVO;
@org.springframework.stereotype.Service("datavice")
public class DataService implements Service<String, DataVO> {

	@Resource(name="ddao")
	Dao<String,DataVO> dao;
	
	@Override
	public void register(DataVO v) throws Exception {
		dao.insert(v);
	}
	
	@Override
	public void modify(DataVO v) throws Exception {
		dao.update(v);
		
	}
	
	@Override
	public DataVO get(String k) throws Exception {
		return dao.select(k);
	}

	@Override
	public ArrayList<DataVO> get() throws Exception {
		return dao.select();
	}



}
