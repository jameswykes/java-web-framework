package me.jw.mvc.test;

import me.jw.mvc.core.*;
import me.jw.mvc.test.misc.TestModel;
import junit.framework.TestCase;

import java.util.HashMap;

public class ControllerTest extends TestCase {
    public void testGetModel() {
        class TestController extends Controller {
            public void init() {
            }
        }

        Request mockRequest = new Request();
        mockRequest.setBody("name=James&age=27");

        try {
            mockRequest.parseRequest();

            TestController controller = new TestController();
            TestModel model = controller.getModel(mockRequest, TestModel.class);

            assertEquals("James", model.getName());
            assertEquals(27, model.getAge());

        } catch (Exception ex) {
            ex.printStackTrace();
            fail();
        }
    }

    public void testAddRoute() {
        class TestController extends Controller {
            public void init() {
                get("/test/get", new IRouteHandler() {
                    @Override
                    public Action handle(Request request, Response response) {
                        return new Text("ok");
                    }
                });

                post("/test/post", new IRouteHandler() {
                    @Override
                    public Action handle(Request request, Response response) {
                        return new Text("ok");
                    }
                });

                put("/test/put", new IRouteHandler() {
                    @Override
                    public Action handle(Request request, Response response) {
                        return new Text("ok");
                    }
                });

                delete("/test/delete", new IRouteHandler() {
                    @Override
                    public Action handle(Request request, Response response) {
                        return new Text("ok");
                    }
                });
            }
        }

        Routes routes = Routes.getInstance();

        // create controller and add the routes
        TestController controller = new TestController();
        controller.init();

        // test that the routes have been added by checking for null handler
        IRouteHandler h0 = routes.matchRoute("GET", "/test/get", new HashMap<String, String>());
        assertNotNull(h0);

        IRouteHandler h1 = routes.matchRoute("POST", "/test/post", new HashMap<String, String>());
        assertNotNull(h1);

        IRouteHandler h2 = routes.matchRoute("PUT", "/test/put", new HashMap<String, String>());
        assertNotNull(h2);

        IRouteHandler h3 = routes.matchRoute("DELETE", "/test/delete", new HashMap<String, String>());
        assertNotNull(h3);
    }
}