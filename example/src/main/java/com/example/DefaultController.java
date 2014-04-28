package com.example;

import me.jw.mvc.core.*;

public class DefaultController extends Controller {
    @Override
    public void init() {
        get("/", new IRouteHandler() {
            @Override
            public Action handle(Request request, Response response) {
                return new Raw("Hello World!");
            }
        });
    }
}
