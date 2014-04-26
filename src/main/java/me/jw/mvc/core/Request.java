package me.jw.mvc.core;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import me.jw.mvc.helpers.HttpHelper;

public class Request {
    private String route;
    private String body;
    private String queryString;
    private HttpServletRequest raw;
    private HashMap<String, String> routeParams;
    private HashMap<String, String> queryParams;
    private HashMap<String, String> postParams;

    public Request() {
        routeParams = new HashMap<String, String>();
        postParams = new HashMap<String, String>();
    }

    public void parseRequest() throws Exception {
        queryParams = HttpHelper.parseQueryString(queryString);
        postParams = HttpHelper.parseQueryString(body);
    }

    public Session getSession() {
        return new Session(raw.getSession());
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public HttpServletRequest getRaw() {
        return raw;
    }

    public void setRaw(HttpServletRequest raw) {
        this.raw = raw;
    }

    public HashMap<String, String> getQueryParams() {
        return queryParams;
    }

    public void setQueryParams(HashMap<String, String> queryParams) {
        this.queryParams = queryParams;
    }

    public void setPostParams(HashMap<String, String> postParams) {
        this.postParams = postParams;
    }

    public HashMap<String, String> getPostParams() {
        return postParams;
    }

    public HashMap<String, String> getRouteParams() {
        return routeParams;
    }

    public String getRouteParam(String name) {
        if (routeParams.containsKey(name)) {
            return routeParams.get(name);
        }
        return null;
    }

    public String getQueryParam(String name) {
        if (queryParams.containsKey(name)) {
            return queryParams.get(name);
        }
        return null;
    }

    public String getPostParam(String name) {
        if (postParams.containsKey(name)) {
            return postParams.get(name);
        }
        return null;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public void setRouteParams(HashMap<String, String> routeParams) {
        this.routeParams = routeParams;
    }
}
