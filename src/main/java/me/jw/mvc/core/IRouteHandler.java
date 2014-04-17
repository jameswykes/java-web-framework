package me.jw.mvc.core;

public interface IRouteHandler {
	public Action handle(Request request, Response response);
}
