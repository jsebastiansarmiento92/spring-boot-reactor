package com.reactor.app.models;

public class Usuario {
	
	private String name;
	private String lasName;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLasName() {
		return lasName;
	}
	public void setLasName(String lasName) {
		this.lasName = lasName;
	}
	public Usuario(String name, String lasName) {
		super();
		this.name = name;
		this.lasName = lasName;
	}
	
	@Override
	public String toString() {
		return "Ususario [name=" + name + ", lasName=" + lasName + "]";
	}
	
	

}
