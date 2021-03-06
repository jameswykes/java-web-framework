package me.jw.mvc.helpers;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;

public class HttpHelper {
    @SuppressWarnings("deprecation")
    public static HashMap<String, String> parseQueryString(String queryString) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (queryString == null) {
            return map;
        }

        if (queryString.startsWith("?")) {
            queryString = queryString.substring(1);
        }

        String[] parts = queryString.split("&");
        for (String part : parts) {
            String[] pair = part.split("=");
            if (pair.length == 2) {
                map.put(pair[0], URLDecoder.decode(pair[1]));
            } else if (pair.length == 1) {
                map.put(pair[0], "");
            }
        }
        return map;
    }

    public static String toQueryString(HashMap<String, String> params) {
        StringBuilder sb = new StringBuilder();
        sb.append("?");
        for (String key : params.keySet()) {
            sb.append(key);
            sb.append("=");
            sb.append(URLEncoder.encode(params.get(key)));
            sb.append("&");
        }
        String output = sb.toString();
        return output.substring(0, output.length() - 1);
    }
}
