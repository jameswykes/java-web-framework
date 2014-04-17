package me.jw.mvc.core;

public class Raw extends Action {
	private Object object; 
	
	public Raw (Object object) { 
		this.object = object; 
	} 
	
	@Override
	public void prepare () {
		setOutput (String.valueOf(object)); 
	} 
} 
