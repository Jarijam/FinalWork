package com.coordinate;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.frame.Dao;
import com.mapper.CoordinateMapper;
import com.vo.CoordinateVO;

@Repository("cddao")
public class CoordinateDao implements Dao<String, CoordinateVO> {

	@Autowired
	CoordinateMapper cdm;

	@Override
	public void insert(CoordinateVO v) throws Exception {
		cdm.insert(v);
	}
	
	@Override
	public void update(CoordinateVO v) throws Exception{
		cdm.update(v);		
	}
	
	
	
	@Override
	public CoordinateVO select(String k) throws Exception {
		return cdm.select(k);
	}

	@Override
	public ArrayList<CoordinateVO> select() throws Exception {
		return cdm.selectall();
	}

	@Override
	public void delete(String k) throws Exception {
		cdm.delete(k);
	}

	
}
