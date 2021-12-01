package com.mapper;

import java.util.ArrayList;

import com.vo.CamVO;
import com.vo.DustVO;

public interface DustMapper {
	public void insert(DustVO obj);
	public DustVO select(String obj);
	public ArrayList<DustVO> selectall();
	public void update(DustVO v);
}



