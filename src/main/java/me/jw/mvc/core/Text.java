package me.jw.mvc.core;

import java.io.UnsupportedEncodingException;

public class Text extends Action {
    private String data;

    public Text(String data) {
        this.data = data;
    }

    @Override
    public void prepare() {
        try {
            setOutput(data.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            setOutput(data.getBytes());
        }
    }
}
