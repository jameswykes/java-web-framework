package me.jw.mvc.test;

public class TestClientResponse {
    private int status;
    private String raw;

    public TestClientResponse(int status, String raw) {
        this.status = status;
        this.raw = raw;
    }

    public int getStatus() {
        return status;
    }

    public String getRaw() {
        return raw;
    }
}
