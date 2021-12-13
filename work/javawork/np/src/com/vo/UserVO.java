package com.vo;

public class UserVO {
	String id;
	String pwd;
	String img;
	public UserVO() {
	}
	public UserVO(String id, String pwd, String img) {
		this.id = id;
		this.pwd = pwd;
		this.img = img;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getImg() {
		return img;
	}
	public void setName(String name) {
		this.img = name;
	}
	@Override
	public String toString() {
		return "UserVO [id=" + id + ", pwd=" + pwd + ", img=" + img + "]";
	}
	
	
}
