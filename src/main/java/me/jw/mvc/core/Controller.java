package me.jw.mvc.core;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import me.jw.mvc.helpers.ReflectionHelper;

import com.google.gson.Gson;

public abstract class Controller {
    // reads POST vars and populates the model
    public <T> T getModel(Request request, Class<T> type) {
        T output = null;

        try {
            output = type.newInstance();
            List<Field> temp = new ArrayList<Field>();
            List<Field> fields = ReflectionHelper.getDeclaredFields(temp, type);

            for (Field field : fields) {
                try {
                    field.setAccessible(true);

                    if (field.getType().getName().equals("int")) {
                        field.set(output, Integer.parseInt(request.getPostParam(field.getName())));
                    } else {
                        field.set(output, request.getPostParam(field.getName()));
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return output;
    }

    public <T> T getModelFromJson(Request request, Class<T> type) {
        Gson g = new Gson();
        return g.fromJson(request.getBody(), type);
    }

    private void addRoute(String method, String route, IRouteHandler handler) {
        Routes.getInstance().addRoute(method, route, handler);
    }

    protected void get(String route, IRouteHandler handler) {
        addRoute("GET", route, handler);
    }

    protected void post(String route, IRouteHandler handler) {
        addRoute("POST", route, handler);
    }

    protected void put(String route, IRouteHandler handler) {
        addRoute("PUT", route, handler);
    }

    protected void delete(String route, IRouteHandler handler) {
        addRoute("DELETE", route, handler);
    }

    public abstract void init();
}
