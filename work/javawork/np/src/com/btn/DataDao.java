package com.btn;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.frame.Dao;
import com.mapper.DataMapper;
import com.vo.DataVO;

@Repository("ddao")
public class DataDao implements Dao<String, DataVO> {

	@Autowired
	DataMapper dm;

	@Override
	public void insert(DataVO v) throws Exception {
		dm.insert(v);
	}
	
	@Override
	public void update(DataVO v) throws Exception{
		dm.update(v);		
	}
	
	@Override
	public DataVO select(String k) throws Exception {
		return dm.select(k);
	}

	@Override
	public ArrayList<DataVO> select() throws Exception {
		return dm.selectall();
	}
	
	

}
