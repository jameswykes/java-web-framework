package me.jw.mvc.test;

public class TestClientResponse {
    private int status;
    private String contentType;
    private String raw;

    public TestClientResponse(int status, String contentType, String raw) {
        this.status = status;
        this.contentType = contentType;
        this.raw = raw;
    }

    public int getStatus() {
        return status;
    }

    public String getContentType() {
        return contentType;
    }

    public String getRaw() {
        return raw;
    }
}
