package com.li.web.boot.entity;

import org.springframework.http.HttpMethod;

import java.util.List;


public class UrlInfo {
    String urlPath;
    List<HttpMethod> allowHttpMethods;

}
