package com.mapper;

import java.util.ArrayList;

import com.vo.CamVO;

public interface CamMapper {
	public void insert(CamVO obj);
	public CamVO select(String obj);
	public ArrayList<CamVO> selectall();
	public void update(CamVO obj);
}



