package me.jw.mvc.core;

import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;

public class Json extends Action {
    static Gson g;
    private Object data;

    public Json(Object data) {
        if (g == null) {
            g = new Gson();
        }
        this.data = data;
    }

    @Override
    public void prepare() {
        try {
            setOutput(g.toJson(data).getBytes("UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            setOutput(g.toJson(data).getBytes());
        }
    }
}
