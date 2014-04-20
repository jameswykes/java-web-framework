package me.jw.mvc.test;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Enumeration;
import java.util.HashMap;

public class MockHttpSession implements HttpSession {
    private String id;
    private HashMap<String, Object> values;
    private long creationTime;

    public MockHttpSession() {
        values = new HashMap<String, Object>();
        id = generateId();
        creationTime = System.currentTimeMillis();
    }

    @Override
    public long getCreationTime() {
        return creationTime;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public long getLastAccessedTime() {
        return 0;
    }

    @Override
    public ServletContext getServletContext() {
        return null;
    }

    @Override
    public void setMaxInactiveInterval(int i) {

    }

    @Override
    public int getMaxInactiveInterval() {
        return 0;
    }

    @Override
    public HttpSessionContext getSessionContext() {
        return null;
    }

    @Override
    public Object getAttribute(String s) {
        return values.get(s);
    }

    @Override
    public Object getValue(String s) {
        return null;
    }

    @Override
    public Enumeration getAttributeNames() {
        return null;
    }

    @Override
    public String[] getValueNames() {
        return new String[0];
    }

    @Override
    public void setAttribute(String s, Object o) {
        values.put(s, o);
    }

    @Override
    public void putValue(String s, Object o) {
    }

    @Override
    public void removeAttribute(String s) {
        values.remove(s);
    }

    @Override
    public void removeValue(String s) {

    }

    @Override
    public void invalidate() {
        values = new HashMap<String, Object>();
        id = generateId();
        creationTime = System.currentTimeMillis();
    }

    @Override
    public boolean isNew() {
        return false;
    }

    static String generateId() {
        return new BigInteger(130, new SecureRandom()).toString(32);
    }
}
