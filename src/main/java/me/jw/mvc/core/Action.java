package me.jw.mvc.core;

public abstract class Action { 
	private String output; 
	
	public Action () {
	} 
	
	protected void setOutput (String output) { 
		this.output = output; 
	} 
	
	public String getOutput () { 
		return output; 
	} 
	
	public abstract void prepare (); 
} 
