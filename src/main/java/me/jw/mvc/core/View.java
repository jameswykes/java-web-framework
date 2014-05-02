package me.jw.mvc.core;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;

public class View extends Action {
    private String html;
    private String templateName;
    private HashMap<String, String> extras;

    public View() {
        this.extras = new HashMap<String, String>();
        this.loadTemplate();
    }

    public View(String templateName) {
        this.extras = new HashMap<String, String>();
        this.templateName = templateName;
        this.loadTemplate();
    }

    private void loadTemplate() {
        if (templateName == null) {
            templateName = getClass().getSimpleName().replace("View", "").toLowerCase();
        }

        File viewFile = new File(templateName);
        if (viewFile.exists()) {
            try {
                html = FileUtils.readFileToString(viewFile);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void prepare() {
        for (String key : extras.keySet()) {
            html = html.replace(key, (extras.get(key) != null) ? extras.get(key) : "");
        }

        try {
            setOutput(html.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            setOutput(html.getBytes());
        }
    }
    
    public void setValue(String key, String value) {
        extras.put(key, value);
    }

    public void setValue(String key, int value) {
        extras.put(key, String.valueOf(value));
    }

    public void setValue(String key, long value) {
        extras.put(key, String.valueOf(value));
    }

    public void setValue(String key, double value) {
        extras.put(key, String.valueOf(value));
    }
}
