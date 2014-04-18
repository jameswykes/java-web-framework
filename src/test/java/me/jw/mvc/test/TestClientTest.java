package me.jw.mvc.test;

import junit.framework.TestCase;
import me.jw.mvc.test.misc.TestController;

import java.util.HashMap;

public class TestClientTest extends TestCase {
    public void testGet() {
        try {
            TestClient client = new TestClient(TestController.class);
            TestClientResponse response = client.get("/");

            assertEquals(response.getRaw(), "test");
            assertEquals(response.getStatus(), 200);

        } catch (Exception ex) {
            ex.printStackTrace();
            fail();
        }
    }

    public void testPost() {
        try {
            TestClient client = new TestClient(TestController.class);

            HashMap<String, String> params = new HashMap<String, String>();
            params.put("param1", "test");

            TestClientResponse response = client.post("/", params);
            assertEquals(response.getRaw(), "test");
            assertEquals(response.getStatus(), 200);

        } catch (Exception ex) {
            ex.printStackTrace();
            fail();
        }
    }

    public void testError() {
        try {
            TestClient client = new TestClient(TestController.class);

            TestClientResponse response = client.get("/error");
            assertEquals(response.getRaw(), "error");
            assertEquals(response.getStatus(), 500);

        } catch (Exception ex) {
            ex.printStackTrace();
            fail();
        }
    }
}
