package com.li.web.boot.filter;

import com.alibaba.fastjson.JSONObject;
import com.li.web.boot.util.SpringBootApplicationUtil;
import com.li.web.boot.util.UrlHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.pattern.PathPattern;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


/**
 * 打印请求地址,参数信息
 * 打印请求对应spring bean name,controller允许的方法类型
 */
//@WebFilter(filterName = "myFilter")
//@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class ReqParamFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("-----------ReqParamFilter初始化-------------");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request=(HttpServletRequest) servletRequest;
        Map<String, String[]> parameterMap = request.getParameterMap();
        Boolean pathPatternFlag=false;
        Boolean requestMethodFlag=false;
        Set<RequestMethod> methods = null;
        String randomId=request.toString().substring(request.toString().length()-8);

        //静态资源无需处理
        if (UrlHelper.passStaticObject(request)){
            chain.doFilter(servletRequest,response);
            return;
        }

        String params = JSONObject.toJSONString(parameterMap,true);
        log.info(randomId+"收到请求地址:{},请求方式为:{}",request.getRequestURI(),request.getMethod());
        log.info(randomId+"收到请求参数:{}",params.replace("[","").replace("]",""));

        RequestMappingHandlerMapping bean = SpringBootApplicationUtil.getApplicationContext().getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = bean.getHandlerMethods();

        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry: handlerMethods.entrySet()) {
            RequestMappingInfo requestMappingInfo = entry.getKey();
            HandlerMethod handlerMethod = entry.getValue();

            Set<PathPattern> patterns = requestMappingInfo.getPathPatternsCondition().getPatterns();
            Iterator<PathPattern> iterator = patterns.iterator();
            while (iterator.hasNext()){

                PathPattern pattern = iterator.next();
                String patternString = pattern.getPatternString();

                if (patternString.equals(request.getRequestURI())){
                    pathPatternFlag=true;
                    log.info("Url:"+patternString+"成功匹配!开始匹配方法类型");

                    methods = requestMappingInfo.getMethodsCondition().getMethods();
                    log.info("对应Url路径允许的方法类型为：{}",methods.size()==0?"ALL":methods);
                    Iterator<RequestMethod> methodIterator = methods.iterator();

                    if (methods.size()==0){
                        //methods size为0,说明允许所有请求类型
                        requestMethodFlag=true;
                    }else {
                        while (methodIterator.hasNext()){

                            RequestMethod requestMethod = methodIterator.next();
                            String name = requestMethod.name();

                            if (name.equals(request.getMethod())){
                                requestMethodFlag=true;
                                log.info("url:{},httpMethod:{},对应Controller BeanName:{},成功匹配!",patternString,name,handlerMethod.getBean());
                                break;
                            }
                        }
                    }

                    if (requestMethodFlag){
                        break;
                    }
                }
            }
            if (requestMethodFlag){
                break;
            }
        }

        if (!pathPatternFlag){
            log.error("未匹配到Url路径!跳转至指定404页面");
        }
        if (pathPatternFlag && !requestMethodFlag){
            log.error("对应Url路径允许的方法类型为：{},与请求方式不匹配",methods);
        }
        chain.doFilter(servletRequest,response);
    }

    @Override
    public void destroy() {
        log.info("-----------ReqParamFilter销毁-------------");
    }
}