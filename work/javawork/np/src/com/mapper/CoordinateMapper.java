package com.mapper;

import java.util.ArrayList;

import com.vo.CoordinateVO;

public interface CoordinateMapper {
	public void insert(CoordinateVO obj);
	public void update(CoordinateVO obj);
	public void delete(String obj);
	public CoordinateVO select(String obj);
	public ArrayList<CoordinateVO> selectall();
}



