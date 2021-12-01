package com.mapper;

import java.util.ArrayList;

import com.vo.CoordinateVO;

public interface CoordinateMapper {
	public void insert(CoordinateVO obj);
	public CoordinateVO select(String obj);
	public ArrayList<CoordinateVO> selectall();
	public void update(CoordinateVO obj);
}



