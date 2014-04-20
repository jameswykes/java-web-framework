package me.jw.mvc.core;

import com.google.gson.Gson;

public class Json extends Action {
    static Gson g;
    private Object object;

    public Json(Object object) {
        if (g == null) {
            g = new Gson();
        }
        this.object = object;
    }

    @Override
    public void prepare() {
        setOutput(g.toJson(object));
    }
}
