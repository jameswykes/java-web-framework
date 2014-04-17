package me.jw.mvc.test; 

import java.util.HashMap;

import me.jw.mvc.helpers.HttpHelper;
import junit.framework.TestCase;

public class HttpHelperTest extends TestCase { 
	public void testParseQueryString () { 
		String test = "?name=James&age=26"; 
		HashMap<String, String> params = HttpHelper.parseQueryString(test); 
		
		assertEquals("James", params.get("name")); 
		assertEquals("26", params.get("age")); 
	} 
} 
