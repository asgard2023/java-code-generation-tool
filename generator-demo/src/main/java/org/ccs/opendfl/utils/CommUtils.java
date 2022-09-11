package org.ccs.opendfl.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

public class CommUtils {
    private CommUtils() {

    }

    /**
     * 取数据前maxLength位
     *
     * @param str
     * @param maxLength
     * @return
     */
    public static String getStringLimit(String str, int maxLength) {
        return str != null && str.length() > maxLength ? str.substring(0, maxLength) : str;
    }

    public static String getStringFirst(String str, String split) {
        if (str == null) {
            return null;
        }
        int idx = str.indexOf(split);
        if (idx > 0) {
            return str.substring(0, idx);
        }
        return str;
    }

    public static String getString(Object obj) {
        if (obj == null) {
            return null;
        } else if (obj instanceof String) {
            return (String) obj;
        } else {
            return "" + obj;
        }
    }

    public static Long getLong(Object obj){
        if(obj==null){
            return null;
        }
        else if(obj instanceof Integer){
            return ((Integer)obj).longValue();
        }
        else if(obj instanceof Long){
            return (Long)obj;
        }
        else{
            return Long.parseLong(""+obj);
        }
    }

    /**
     * @return
     */
    public static boolean startWithChar(String str, char ch) {
        if (str == null || str.length() < 1) {
            return false;
        }
        return str.charAt(0) == ch;
    }

    /**
     * @return
     */
    public static boolean endWithChar(String str, char ch) {
        if (str == null || str.length() < 1) {
            return false;
        }
        return str.charAt(str.length() - 1) == ch;
    }

    /**
     * 只要确保你的编码输入是正确的,就可以忽略掉 UnsupportedEncodingException
     */
    public static String asUrlParams(Map<String, String> source) {
        Iterator<String> it = source.keySet().iterator();
        StringBuilder paramStr = new StringBuilder();
        while (it.hasNext()) {
            String key = it.next();
            String value = source.get(key);
            if (StringUtils.isBlank(value)) {
                continue;
            }
            try {
                // URL 编码
                value = URLEncoder.encode(value, "utf-8");
            } catch (UnsupportedEncodingException e) {
                // do nothing
            }
            paramStr.append("&").append(key).append("=").append(value);
        }
        // 去掉第一个&
        return paramStr.substring(1);
    }

    public static String appendUrl(String url, String path) {
        if (url == null) {
            return null;
        }
        if (path == null) {
            path = "";
        }
        if (endWithChar(url, '/') && startWithChar(path, '/')) {//相当于startsWith
            return url + path.substring(1);
        } else if (endWithChar(url, '/') || startWithChar(path, '/')) {//相当于startsWith
            return url + path;
        }

        return url + "/" + path;
    }

    public static String getBaseUrl(String url) {
        int idx = url.indexOf("://");
        if (idx > 0) {
            int idxEnd = idx + 3;
            String tmp = url.substring(idxEnd);
            idx = tmp.indexOf('/');
            if (idx > 0) {
                tmp = tmp.substring(0, idx);
                idxEnd += tmp.length();
            }
            return url.substring(0, idxEnd);
        }
        return url;
    }

    public static String trimUrlDomainAndParam(String uri) {
        if (uri == null) {
            return null;
        }
        if (uri.indexOf("http") >= 0) {
            String baseUrl = getBaseUrl(uri);
            String path = uri.substring(baseUrl.length());
            uri = path;
        }
        int idx = uri.indexOf("?");
        if (idx > 0) {
            uri = uri.substring(0, idx);
        }
        return uri;
    }

    public static String trimUrlParam(String uri) {
        if (uri == null) {
            return null;
        }
        if (uri.indexOf('?') >= 0) {
            return uri.substring(0, uri.indexOf('?'));
        }
        return uri;
    }

    public static String getUrlWithQueryString(String url, Map<String, String> params) {
        if (params == null) {
            return url;
        }

        StringBuilder builder = new StringBuilder(url);
        if (url.contains("?")) {
            builder.append("&");
        } else {
            builder.append("?");
        }

        int i = 0;
        for (String key : params.keySet()) {
            String value = params.get(key);
            if (value == null) { // 过滤空的key
                continue;
            }

            if (i != 0) {
                builder.append('&');
            }


            builder.append(key);
            builder.append('=');
            builder.append(value);
            i++;
        }

        return builder.toString();
    }

    public static String encode(String input) {
        if (input == null) {
            return "";
        }

        try {
            return URLEncoder.encode(input, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return input;
    }
}
