package me.jw.mvc.test;

import java.util.HashMap;

import me.jw.mvc.core.Action;
import me.jw.mvc.core.IRouteHandler;
import me.jw.mvc.core.Json;
import me.jw.mvc.core.Raw;
import me.jw.mvc.core.Request;
import me.jw.mvc.core.Response;
import me.jw.mvc.core.Routes;
import junit.framework.TestCase;

public class RoutesTest extends TestCase {
    public void testMatchRoute() {
        IRouteHandler handler = new IRouteHandler() {
            @Override
            public Action handle(Request request, Response response) {
                return new Json("hello");
            }
        };

        Routes routes = Routes.getInstance();
        routes.addRoute("GET", "/", handler);
        routes.addRoute("GET", "/hello", handler);
        routes.addRoute("GET", "/hello/:param", handler);
        routes.addRoute("GET", "/hello/hello/hello/:param", handler);
        routes.addRoute("POST", "/", handler);

        IRouteHandler test1 = routes.matchRoute("GET", "/hello/hello/hello/1", new HashMap<String, String>());
        assertNotNull(test1);

        IRouteHandler test2 = routes.matchRoute("GET", "/hello/1", new HashMap<String, String>());
        assertNotNull(test2);
    }

    public void testRouteExecutionOrder() {
        IRouteHandler handler1 = new IRouteHandler() {
            @Override
            public Action handle(Request request, Response response) {
                return new Raw("handler1");
            }
        };

        IRouteHandler handler2 = new IRouteHandler() {
            @Override
            public Action handle(Request request, Response response) {
                return new Raw("handler2");
            }
        };

        Routes routes = Routes.getInstance();
        routes.addRoute("POST", "/calls/call", handler1);
        routes.addRoute("POST", "/calls/:callhistoryid", handler2);

        IRouteHandler route = routes.matchRoute("POST", "/calls/call", new HashMap<String, String>());
        Action action = route.handle(null, null);
        action.prepare();

        assertEquals("handler1", action.getOutput());
    }
}
