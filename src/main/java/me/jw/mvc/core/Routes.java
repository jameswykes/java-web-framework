package me.jw.mvc.core;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class Routes {
    static Routes instance;
    private LinkedHashMap<String, IRouteHandler> routes;

    public Routes() {
        routes = new LinkedHashMap<String, IRouteHandler>();
    }

    public static Routes getInstance() {
        if (instance == null) {
            instance = new Routes();
        }
        return instance;
    }

    public void addRoute(String method, String route, IRouteHandler handler) {
        String key = String.format("%s %s", method, route);
        routes.put(key, handler);
        System.out.println("added route => " + key);
    }

    public IRouteHandler matchRoute(String method, String path, HashMap<String, String> routeParams) {
        String[] requestPathParts = path.split("/");
        for (String route : routes.keySet()) {
            String[] parts = route.split(" ");
            String[] routeParts = parts[1].split("/");

            if (!parts[0].equals(method)) {
                continue;
            }

            if (requestPathParts.length == routeParts.length) {
                if (requestPathParts.length == 0) {
                    return routes.get(route);
                }

                // check that each part of the requested route,
                // matches the route added

                boolean ok = true;

                for (int i = 0; i != requestPathParts.length; i++) {
                    // route param
                    // e.g. /hello/:name
                    if (routeParts[i].startsWith(":")) {
                        routeParams.put(routeParts[i], requestPathParts[i]);
                        continue;
                    }

                    // wildcard
                    // e.g. /hello/*
                    if (routeParts[i].equals("*")) {
                        continue;
                    }

                    // wildcard with file extension
                    // e.g. /hello/*.jpg
                    if (routeParts[i].startsWith("*")
                            && routeParts[i].contains(".")) {

                        if (routeParts[i].substring(
                                routeParts[i].indexOf(".")).equals(
                                requestPathParts[i].substring(
                                        requestPathParts[i].indexOf("."))
                        )) {
                            continue;
                        }
                    }

                    if (!requestPathParts[i].equals(routeParts[i])) {
                        ok = false;
                        break;
                    }
                }

                if (ok) {
                    return routes.get(route);
                }
            }
        }

        return null;
    }

    public void clear() {
        routes.clear();
    }
}
