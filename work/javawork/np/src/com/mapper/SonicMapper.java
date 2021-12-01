package com.mapper;

import java.util.ArrayList;

import com.vo.CamVO;
import com.vo.SonicVO;

public interface SonicMapper {
	public void insert(SonicVO obj);
	public SonicVO select(String obj);
	public ArrayList<SonicVO> selectall();
	public void update(SonicVO obj);
}



