package com.vo;

public class DataVO {
	String btn;
	String temp;
	public DataVO() {
	}
	public DataVO(String btn) {
		this.btn = btn;
	}
	
	public String toString() {
		return "BtnVO [btn=" + btn +"temp="+temp+ "]";
	}
	
	public DataVO(String btn, String temp) {
		super();
		this.btn = btn;
		this.temp = temp;
	}
	public String getBtn() {
		return btn;
	}
	public void setBtn(String btn) {
		this.btn = btn;
	}
	public String getTemp() {
		return temp;
	}
	public void setTemp(String temp) {
		this.temp = temp;
	}
	
	
}
