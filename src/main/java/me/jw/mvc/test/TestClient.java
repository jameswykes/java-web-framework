package me.jw.mvc.test;

import me.jw.mvc.DispatchServlet;
import me.jw.mvc.core.Controller;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

/* allows unit tests to invoke DispatchServlet with
   parameters to test controller functionality without the need
   to run on a server */

public class TestClient {
    private Controller controller;
    private HttpSession session;

    public <T extends Controller> TestClient(Class<T> className) {
        try {
            controller = className.newInstance();
            controller.init();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public TestClientResponse get(String route) throws Exception {
        DispatchServlet dispatchServlet = new DispatchServlet();
        MockHttpServletRequest request = new MockHttpServletRequest(
                "GET", route, null
        );
        if (session != null) {
            request.setSession(session);
        }

        MockHttpServletResponse response = new MockHttpServletResponse();

        try {
            dispatchServlet.dispatch(request, response);
            return new TestClientResponse(
                    response.getStatus(),
                    response.getContentType(),
                    response.getResponseString());

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        throw new Exception("No response received from route " + route);
    }

    public TestClientResponse post(String route, HashMap<String, String> data) throws Exception {
        DispatchServlet dispatchServlet = new DispatchServlet();
        MockHttpServletRequest request = new MockHttpServletRequest(
                "POST", route, data
        );
        if (session != null) {
            request.setSession(session);
        }

        MockHttpServletResponse response = new MockHttpServletResponse();

        try {
            dispatchServlet.dispatch(request, response);
            return new TestClientResponse(
                    response.getStatus(),
                    response.getContentType(),
                    response.getResponseString());

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        throw new Exception("No response received from route " + route);
    }

    public void setSessionData(HashMap<String, Object> data) {
        session = new MockHttpSession();
        for (String key : data.keySet()) {
            session.setAttribute(key, data.get(key));
        }
    }
}
