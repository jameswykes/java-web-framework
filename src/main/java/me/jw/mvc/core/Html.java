package me.jw.mvc.core;

public class Html extends Action {
    private String data;

    public Html(String data) {
        this.data = data;
    }

    @Override
    public void prepare() {
        setOutput(data.getBytes());
    }
}
