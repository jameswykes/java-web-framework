package me.jw.mvc;

import me.jw.mvc.core.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.reflections.Reflections;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Set;

public class DispatchServlet extends HttpServlet {
    private boolean initialised;

    private synchronized void initControllers() throws Exception {
        String controllerPackageNames = getInitParameter("controllerPackages");
        String[] names = controllerPackageNames.split(",");

        for (String packageName : names) {
            Reflections reflections = new Reflections(packageName);

            // find subtypes of AbstractController and call init() on them to add the routes
            Set<Class<? extends AbstractController>> subTypes =
                    reflections.getSubTypesOf(AbstractController.class);

            for (Class<?> controller : subTypes) {
                try {
                    AbstractController c = (AbstractController) controller.newInstance();
                    c.init();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        initialised = true;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (!initialised) {
            try {
                initControllers();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        dispatch(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (!initialised) {
            try {
                initControllers();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

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
                response.setContentType("text/html");
                response.setStatus(200);

                try {
                    Action action = handler.handle(requestWrapper, responseWrapper);
                    if (action != null) {
                        action.prepare();
                        response.getWriter().println(action.getOutput());
                        response.flushBuffer();
                    }
                } catch (Exception ex) {
                    response.setStatus(500);
                    ex.printStackTrace();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
