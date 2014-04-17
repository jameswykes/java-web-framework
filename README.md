Lightweight Java Web Framework

### Setting up your project

Create a new project with maven with the archetype maven-archetype-webapp.
The structure of the application should be generated as follows:

```
src /
    main/
        java/
        resources/
        webapp/
            WEB-INF/
                web.xml
    test/
        java/
```

### Adding dependencies

Add the JAR mvc-x.x.x-all.jar to the project in the lib directory inside WEB-INF
as follows:

```
src/main/webapp/WEB-INF/lib/mvc-x.x.x-all.jar
```

and reference from the pom.xml. Make sure that you
reference the correct version.

```xml
<dependency>
    <groupId>me.jw</groupId>
    <artifactId>mvc</artifactId>
    <version>0.0.1</version>
    <scope>system</scope>
    <systemPath>${project.basedir}/src/main/webapp/WEB-INF/lib/mvc-0.0.1-all.jar</systemPath>
</dependency>
```

If you're using Jetty, add the plugin to your pom.xml file

```xml
<build>
    <finalName>my-application</finalName>
    <plugins>
        <plugin>
            <groupId>org.eclipse.jetty</groupId>
            <artifactId>jetty-maven-plugin</artifactId>
            <version>9.0.5.v20130815</version>
            <configuration>
                <scanIntervalSeconds>1</scanIntervalSeconds>
            </configuration>
        </plugin>
    </plugins>
</build>
```

### Setting up the web.xml

Forward all requests to the framework by editing the web.xml as follows:

```xml
<servlet>
    <servlet-name>dispatch</servlet-name>
    <servlet-class>me.jw.mvc.DispatchServlet</servlet-class>
    <init-param>
        <param-name>controllerPackages</param-name>
        <param-value>com.package.controllers</param-value>
    </init-param>
</servlet>

<servlet-mapping>
    <servlet-name>dispatch</servlet-name>
    <url-pattern>/*</url-pattern>
</servlet-mapping>
```

### Adding a controller

2. Define a controller by extending AbstractController

```java
public class DefaultController extends AbstractController {
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

### Build / Run

This documentation assumes you are running with Jetty and Maven. You can
quickly build and run your project by first compiling with the following:

```
mvn compile
```

Then, launch Jetty via the Maven plugin

```
mvn jetty:run
```

You can then view your project at http://localhost:8080

Additional Jetty configuration can take place in a jetty-web.xml file
placed inside the WEB-INF directory.

