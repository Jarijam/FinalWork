package com.vo;

public class CoordinateVO {
	String X;
	String Y;
	
	public CoordinateVO() {
	}
	
	public CoordinateVO(String X) {
		this.X = X;
	
	}
	public CoordinateVO(String X, String Y) {
		super();
		this.X = X;
		this.Y = Y;
	}
	
	public String toString() {
		return "CoordinateVO [X=" + X +"Y="+Y+ "]";
	}
	
	public String getX() {
		return X;
	}
	public void setX(String x) {
		X = x;
	}
	public String getY() {
		return Y;
	}
	public void setY(String y) {
		Y = y;
	}
	
	
	
	
}
