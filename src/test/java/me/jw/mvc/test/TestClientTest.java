package me.jw.mvc.test;

import com.google.gson.Gson;
import junit.framework.TestCase;
import me.jw.mvc.test.misc.TestController;
import me.jw.mvc.test.misc.TestRequest;

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

            TestClientResponse response = null;

            response = client.post("/", params);
            assertEquals(response.getRaw(), "test");
            assertEquals(response.getStatus(), 200);

            Gson gson = new Gson();
            TestRequest testRequest = new TestRequest("test");
            response = client.post("/test-post-request", gson.toJson(testRequest));
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

    public void testSession() {
        try {
            TestClient client = new TestClient(TestController.class);

            HashMap<String, Object> data = new HashMap<String, Object>();
            data.put("sessionValue", "test");

            client.setSessionData(data);

            TestClientResponse response = client.get("/session-test");
            assertEquals(response.getRaw(), "test");
            assertEquals(response.getStatus(), 200);

        } catch (Exception ex) {
            ex.printStackTrace();
            fail();
        }
    }
}
