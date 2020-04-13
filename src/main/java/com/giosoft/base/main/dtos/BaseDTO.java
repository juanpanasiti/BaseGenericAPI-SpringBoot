package com.giosoft.base.main.dtos;

import java.io.Serializable;

public abstract class BaseDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	//Atributos base del DTO
	protected int id;
	
	//Setters y Getters
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

}
