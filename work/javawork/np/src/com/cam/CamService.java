package com.cam;

import java.util.ArrayList;

import javax.annotation.Resource;

import com.frame.Dao;
import com.frame.Service;
import com.vo.CamVO;
@org.springframework.stereotype.Service("camservice")
public class CamService implements Service<String, CamVO> {

	@Resource(name="camdao")
	Dao<String,CamVO> dao;
	
	@Override
	public void register(CamVO v) throws Exception {
		dao.insert(v);
	}
	
	@Override
	public void modify(CamVO v) throws Exception {
		dao.update(v);
		
	}
	
	@Override
	public CamVO get(String k) throws Exception {
		return dao.select(k);
	}

	@Override
	public ArrayList<CamVO> get() throws Exception {
		return dao.select();
	}



}
