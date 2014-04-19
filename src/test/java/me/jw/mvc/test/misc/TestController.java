package me.jw.mvc.test.misc;

import me.jw.mvc.core.*;

public class TestController extends Controller {
    @Override
    public void init() {
        get("/", new IRouteHandler() {
            @Override
            public Action handle(Request request, Response response) {
                return new Raw("test");
            }
        });

        post("/", new IRouteHandler() {
            @Override
            public Action handle(Request request, Response response) {
                return new Raw(request.getPostParam("param1"));
            }
        });

        get("/error", new IRouteHandler() {
            @Override
            public Action handle(Request request, Response response) {
                response.setStatus(500);
                return new Raw("error");
            }
        });

        get("/test-content-type", new IRouteHandler() {
            @Override
            public Action handle(Request request, Response response) {
                response.setContentType("text/html");
                return new Raw("test");
            }
        });
    }
}
