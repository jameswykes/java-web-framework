package me.jw.mvc.test;

import junit.framework.TestCase;
import me.jw.mvc.test.misc.TestController;

public class DispatchTest extends TestCase {
    public void testAutoHeader() {
        try {
            TestClient client = new TestClient(TestController.class);
            TestClientResponse response = client.get("/test-content-type");

            assertEquals("text/html", response.getContentType());

        } catch (Exception ex) {
            ex.printStackTrace();
            fail();
        }
    }
}
