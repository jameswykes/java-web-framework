package me.jw.mvc.test;

import junit.framework.TestCase;
import me.jw.mvc.core.*;
import me.jw.mvc.test.misc.TestController;

import java.util.HashMap;

public class TestClientTest extends TestCase {
    public void testGet() {
        try {
            TestClient client = new TestClient(TestController.class);
            String response = client.get("/");
            assertEquals(response, "test");
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

            String response = client.post("/", params);
            assertEquals(response, "test");
        } catch (Exception ex) {
            ex.printStackTrace();
            fail();
        }
    }
}
