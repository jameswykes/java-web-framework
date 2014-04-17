package me.jw.mvc.test;

import me.jw.mvc.DispatchServlet;
import me.jw.mvc.core.AbstractController;

import java.util.HashMap;

/* allows unit tests to manually invoke DispatchServlet with
   test parameters to test controller functionality without the need
   to run on a server */

public class TestClient {
    private AbstractController controller;

    public <T extends AbstractController> TestClient(Class<T> className) {
        try {
            controller = className.newInstance();
            controller.init();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String get(String route) throws Exception {
        DispatchServlet dispatchServlet = new DispatchServlet();
        MockHttpServletRequest request = new MockHttpServletRequest(
                "GET", route, null
        );
        MockHttpServletResponse response = new MockHttpServletResponse();

        try {
            dispatchServlet.dispatch(request, response);
            return response.getResponseString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        throw new Exception("No response received from route " + route);
    }

    public String post(String route, HashMap<String, String> data) throws Exception {
        DispatchServlet dispatchServlet = new DispatchServlet();
        MockHttpServletRequest request = new MockHttpServletRequest(
                "POST", route, data
        );
        MockHttpServletResponse response = new MockHttpServletResponse();
        
        try {
            dispatchServlet.dispatch(request, response);
            return response.getResponseString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        throw new Exception("No response received from route " + route);
    }
}
