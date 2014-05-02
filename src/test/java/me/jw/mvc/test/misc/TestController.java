package me.jw.mvc.test.misc;

import me.jw.mvc.core.*;

public class TestController extends Controller {
    @Override
    public void init() {
        get("/", new IRouteHandler() {
            @Override
            public Action handle(Request request, Response response) {
                return new Text("test");
            }
        });

        post("/", new IRouteHandler() {
            @Override
            public Action handle(Request request, Response response) {
                return new Text(request.getPostParam("param1"));
            }
        });

        get("/error", new IRouteHandler() {
            @Override
            public Action handle(Request request, Response response) {
                response.setStatus(500);
                return new Text("error");
            }
        });

        get("/test-content-type", new IRouteHandler() {
            @Override
            public Action handle(Request request, Response response) {
                response.setContentType("text/html");
                return new Text("test");
            }
        });

        get("/session-test", new IRouteHandler() {
            @Override
            public Action handle(Request request, Response response) {
                return new Text(request.getSession().attribute("sessionValue").toString());
            }
        });
    }
}
