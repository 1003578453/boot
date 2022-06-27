package com.li.web.boot.util;

import com.li.web.boot.entity.UrlInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class UrlHelper {
    private static Map<String, UrlInfo> urlInfoMap;

    public static Boolean passStaticObject(HttpServletRequest request){
        if (request.getRequestURI().startsWith("/css/")
                || request.getRequestURI().startsWith("/fonts/")
                || request.getRequestURI().startsWith("/images/")
                || request.getRequestURI().startsWith("/js/")
                || request.getRequestURI().startsWith("/favicon.ico")){
            return true;
        }else {
            return false;
        }
    };

}
