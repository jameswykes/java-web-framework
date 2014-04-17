package me.jw.mvc.core;

import java.util.Enumeration;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpSession;

public class Session { 
	private HttpSession session; 
	
	public Session (HttpSession session) {
		this.session = session; 
	} 
	
	public Set<String> attributes () { 
		TreeSet<String> attributes = new TreeSet<String>();
        Enumeration<String> enumeration = session.getAttributeNames();
        while (enumeration.hasMoreElements()) {
            attributes.add(enumeration.nextElement());
        }
        return attributes;
	} 
	
	public void attribute(String name, Object value) {
        session.setAttribute(name, value);
	} 
	
	@SuppressWarnings("unchecked")
    public <T> T attribute(String name) {
        return (T) session.getAttribute(name);
	} 
	
	public void invalidate () { 
		session.invalidate(); 
	} 
	
	public String getId () { 
		return session.getId(); 
	}
}
