package me.jw.mvc.core;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class Routes {
	static Routes instance; 
	private LinkedHashMap<String, IRouteHandler> routes; 
	
	public Routes () { 
		routes = new LinkedHashMap<String, IRouteHandler> (); 
	} 

	public static Routes getInstance () { 
		if (instance == null) { 
			instance = new Routes (); 
		} 
		return instance; 
	} 
	
	public void addRoute (String method, String route, IRouteHandler handler) { 
		String key = String.format ("%s %s", method, route); 
		routes.put(key, handler); 
		System.out.println("added route => " + key); 
	} 
	
	public IRouteHandler matchRoute (String method, String path, HashMap<String, String> routeParams) { 
		String[] pathParts = path.split("/"); 
		for (String route : routes.keySet()) { 
			String[] parts = route.split(" "); 
			String[] urlParts = parts[1].split("/"); 
			
			if (!parts[0].equals(method)) { 
				continue; 
			} 
			
			if (pathParts.length == urlParts.length) { 
				if (pathParts.length == 0) { 
					return routes.get(route); 
				} 
				for (int i = 0; i != urlParts.length; i++) { 
					String urlPart = urlParts[i]; 
					if (!urlPart.startsWith(":")) { 
						if (!urlPart.equals(pathParts[i])) { 
							break; 
						} 
					} else { 
						routeParams.put(urlPart, pathParts[i]); 
					} 
					if (i == urlParts.length - 1) { 
						return routes.get(route); 
					} 
				} 
			} 
		} 
		return null; 
	} 
} 
