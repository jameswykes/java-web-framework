package me.jw.mvc.core;

import java.io.File;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;

public abstract class View extends Action {
    private String html;
    private HashMap<String, String> values;

    public View() {
        this.loadView();
        this.values = new HashMap<String, String>();
    }

    private void loadView() {
        String name = getClass().getSimpleName().replace("View", "").toLowerCase();

        File templateDirectory = new File("views");
        if (!templateDirectory.exists()) {
            templateDirectory.mkdir();
        }

        File viewFile = new File("views/" + name + ".html");
        if (viewFile.exists()) {
            try {
                html = FileUtils.readFileToString(viewFile);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public abstract void render();

    @Override
    public void prepare() {
        render();
        for (String key : values.keySet()) {
            html = html.replace(key, (values.get(key) != null) ? values.get(key) : "");
        }
        setOutput(html);
    }

    protected void setValue(String key, String value) {
        values.put(key, value);
    }

    protected void setValue(String key, int value) {
        values.put(key, String.valueOf(value));
    }

    protected void setValue(String key, long value) {
        values.put(key, String.valueOf(value));
    }

    protected void setValue(String key, double value) {
        values.put(key, String.valueOf(value));
    }

    public void setExtraValue(String key, String value) {
        values.put(key, value);
    }
}
