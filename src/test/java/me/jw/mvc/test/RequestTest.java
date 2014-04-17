package me.jw.mvc.test;

import me.jw.mvc.core.Request;
import junit.framework.TestCase;

public class RequestTest extends TestCase { 
	public void testParseRequest () { 
		try { 
			Request request = new Request (); 
			request.setBody("CallDuration=10&CallSid=&DialCallDuration=1016/09/2013"); 
			request.setQueryString("?exampleValue=1"); 
			request.parseRequest(); 
			
			// test parsing post / query string parameters 
			assertTrue (request.getPostParams().containsKey("CallDuration")); 
			assertTrue (request.getPostParam("CallDuration") != null); 
			assertTrue (request.getQueryParams().containsKey("exampleValue")); 
			assertEquals ("1", request.getQueryParam("exampleValue")); 
			
			// some edge cases
			request.setBody(null); 
			request.setQueryString(null); 
			request.parseRequest(); 
			
			assertTrue (!request.getPostParams().containsKey("CallDuration")); 
			assertTrue (!request.getQueryParams().containsKey("CallDuration")); 
			
		} catch (Exception ex) { 
			fail (); 
		} 
	} 
} 
