package com.cam;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.frame.Dao;
import com.mapper.CamMapper;
import com.vo.CamVO;

@Repository("camdao")
public class CamDao implements Dao<String, CamVO> {

	@Autowired
	CamMapper cam;

	@Override
	public void insert(CamVO v) throws Exception {
		cam.insert(v);
	}
	
	@Override
	public void update(CamVO v) throws Exception{
		cam.update(v);		
	}
	
	@Override
	public CamVO select(String k) throws Exception {
		return cam.select(k);
	}

	@Override
	public ArrayList<CamVO> select() throws Exception {
		return cam.selectall();
	}
	
	

}
