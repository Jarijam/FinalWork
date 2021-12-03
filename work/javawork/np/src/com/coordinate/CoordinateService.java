package com.coordinate;

import java.util.ArrayList;

import javax.annotation.Resource;

import com.frame.Dao;
import com.frame.Service;
import com.vo.CoordinateVO;
import com.vo.LedVO;
@org.springframework.stereotype.Service("cdservice")
public class CoordinateService implements Service<String, CoordinateVO> {

	@Resource(name="cddao")
	Dao<String,CoordinateVO> dao;
	
	@Override
	public void register(CoordinateVO v) throws Exception {
		dao.insert(v);
	}
	
	@Override
	public void modify(CoordinateVO v) throws Exception {
		dao.update(v);
		
	}
	
	@Override
	public void remove(String k) throws Exception {
		dao.delete(k);
	}
	
	@Override
	public CoordinateVO get(String k) throws Exception {
		return dao.select(k);
	}

	@Override
	public ArrayList<CoordinateVO> get() throws Exception {
		return dao.select();
	}

	



}
