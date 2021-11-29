package com.mapper;

import java.util.ArrayList;

import com.vo.DataVO;

public interface DataMapper {
	public void insert(DataVO obj);
	public DataVO select(String obj);
	public ArrayList<DataVO> selectall();
	public void update(DataVO obj);
}



