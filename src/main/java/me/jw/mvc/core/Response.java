package me.jw.mvc.core;

import javax.servlet.http.HttpServletResponse;

public class Response {
	private HttpServletResponse raw;

	public HttpServletResponse getRaw () {
		return raw;
	}

	public void setRaw (HttpServletResponse raw) {
		this.raw = raw;
	}

	public void redirect (String url) {
		try {
			raw.sendRedirect (url);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void addHeader (String type, String value) {
        try {
            raw.addHeader(type, value);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setStatus (int status) {
        raw.setStatus(status);
    }
}
