package com.frame;

import java.util.ArrayList;

import org.springframework.transaction.annotation.Transactional;

import com.vo.DataVO;

public interface Service<K,V> {
	@Transactional
	public void register(V v) throws Exception;
	void modify(V v) throws Exception;
	
	public V get(K k) throws Exception;
	public ArrayList<V> get() throws Exception;

}



