A lightweight Java web framework

This documentation is currently in progress.

### Adding a controller

Define a controller by extending AbstractController

```java
public class DefaultController extends Controller {
    public void init () {
    }
}
```

### Adding routes

Routes are added to a controller by calling the get() post() methods
on the controller, usually within the init() method.  These methods
are supplied an IRouteHandler class that defines how the request is handled.

#### Route Handlers

Route handlers must return a subclass of Action, which is appropriately
handled by the framework. Examples are View(), Raw(), and Json().


```java
get ('/', new IRouteHandler () {
    public Action handle (Request request, Response response) {
        return new View ();
    }
});
```

##### View Actions

View actions are used to return HTML content to a browser. Views can take
an empty constructor, which will instruct the framework to look for an HTML
file inside a local directory named 'views' with the class name of that view.

For example, take a view defined as follows:

```java
public class IndexView extends View {
    ...
}
```

Returned from a route handler, the framework will automatically look for
a file named "index.html", and use this for the template HTML that is returned
to the browser.

#### Adding an HTML template

Create a directory inside the project root called views. Place a .html file
inside this directory with the following content:

```html
<html>
<body>
Hello World!
</body>
</html>
```

#### Other action types

You can return an object as JSON, by simple returning a Json() action from
a route handler. The JSON action takes a single parameter, which is the
object that should be rendered as JSON.

See the example below:

```java
get ('/', new IRouteHandler () {
    public Action handle (Request request, Response response) {
        return new Json ("hello");
    }
});
```

This returns the following:

```
{"hello"}
```

#### Route parameters

Route parameters can be defined in the format :<name> inside the route path
definition. Parameters can then be accessed inside the Route Handler itself.

Consider the following example:

```java
get ('/person/:personid', new IRouteHandler () {
    public Action handle (Request request, Response response){
        return new Raw("Person ID: " + request.getRouteParam(":personid"));
    }
});
```

Requesting the following URL:

```
http://localhost:8080/person/1
```

Will return:

```
Person ID: 1
```

### View Models

The framework contains some helper methods to automatically populate
a view model from a request. For example, say you need to capture some form
data and populate a model with the user input.

Calling the getModel() method in a controller will automatically map fields
found in the POST data, to fields of the same name in your model class.

Consider the following example:

```java
post ('/person', new IRouteHandler() {
    public Action handle(Request request, Response response) {
        Person p = getModel(request, Person.class);
        // with a post param called 'name' and having a value of 'James'
        // p.getName () will now return 'James'
        return new Raw (p.getName());
    }
});
```

You can perform the same mapping from a JSON formatter request, by calling
the getModelFromJson() method, as follows:

```java
getModelFromJson (request, Person.class);
```

### Testing

The framework provides and easy way to test your application without needing
to deploy to a server. You can mock requests to a controller and examine the
output from inside a unit test.

You can create a TestClient for a controller under test and invoke HTTP methods
on a route.

See the following example:

```java
public class ControllerTest extends TestCase {
    public void testGet() {
        try {
            TestClient client = new TestClient(Controller.class);
            TestClientResponse response = client.get("/");

            assertEquals(response.getRaw(), "test");
            assertEquals(response.getStatus(), 200);

        } catch (Exception ex) {
            ex.printStackTrace();
            fail();
        }
    }
```

The post() method of the TestClient allows POST parameters to be supplied
to the route.

See the example below:

```java
public class ControllerTest extends TestCase {
    public void testPost() {
        try {
            TestClient client = new TestClient(Controller.class);

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
```
