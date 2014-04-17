package me.jw.mvc.core;

import com.google.gson.Gson;

public class Json extends Action { 
	private Object object; 
	
	public Json (Object object) { 
		this.object = object; 
	} 
	
	@Override
	public void prepare () {
		Gson g = new Gson (); 
		setOutput (g.toJson(object)); 
	} 
} 
