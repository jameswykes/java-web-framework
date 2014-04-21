package me.jw.mvc;

import me.jw.mvc.core.*;
import org.apache.commons.io.IOUtils;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

public class DispatchServlet extends HttpServlet {
    @Override
    public void init() {
        try {
            Reflections reflections = new Reflections(
                    new ConfigurationBuilder()
                            .setUrls(ClasspathHelper
                                    .forClassLoader(
                                            ClasspathHelper.classLoaders()))
            );

            Set<Class<? extends Controller>> subTypes =
                    reflections.getSubTypesOf(Controller.class);

            for (Class<?> controller : subTypes) {
                try {
                    Controller c = (Controller) controller.newInstance();
                    c.init();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        dispatch(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        dispatch(request, response);
    }

    public void dispatch(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        Request requestWrapper = new Request();
        requestWrapper.setRaw(request);
        requestWrapper.setBody(IOUtils.toString(request.getInputStream()));
        requestWrapper.setQueryString(request.getQueryString());

        try {
            requestWrapper.parseRequest();

            Response responseWrapper = new Response();
            responseWrapper.setRaw(response);

            IRouteHandler handler = Routes.getInstance().matchRoute(
                    request.getMethod(),
                    request.getPathInfo(),
                    requestWrapper.getRouteParams());

            if (handler != null) {
                responseWrapper.setStatus(200);

                try {
                    Action action = handler.handle(requestWrapper, responseWrapper);

                    if (action != null) {
                        if (action instanceof Json
                                && responseWrapper.getContentType() == null) {
                            responseWrapper.setContentType("application/json");
                        }

                        if (action instanceof View
                                && responseWrapper.getContentType() == null) {
                            responseWrapper.setContentType("text/html");
                        }

                        if (action instanceof Raw
                                && responseWrapper.getContentType() == null) {
                            responseWrapper.setContentType("text/plain");
                        }

                        action.prepare();
                        response.getWriter().println(action.getOutput());
                        response.flushBuffer();
                    }

                    System.out.println(
                            "matched route => " +
                                    request.getMethod() + " " + request.getPathInfo() + " " +
                                    responseWrapper.getStatus() + " " + request.getRemoteAddr()
                    );

                } catch (Exception ex) {
                    responseWrapper.setStatus(500);
                    ex.printStackTrace();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server server = new Server(8080);

        ServletHandler servletHandler = new ServletHandler();
        servletHandler.addServletWithMapping(DispatchServlet.class, "/*");

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase("./static");

        SessionHandler sessionHandler = new SessionHandler();

        HandlerCollection collection = new HandlerCollection();
        collection.addHandler(resourceHandler);
        collection.addHandler(servletHandler);
        collection.addHandler(sessionHandler);
        
        server.setHandler(collection);

        try {
            server.start();
            server.join();

            System.out.println("starting server on port 8080");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
