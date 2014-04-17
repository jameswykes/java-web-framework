package me.jw.mvc.test.misc;

import me.jw.mvc.core.*;

public class TestController extends AbstractController {
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
    }
}
