package com.li.web.boot.appconfig.application.config;

import com.li.web.boot.interceptor.ReqParamInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer{

    /**
     * 指定加密方式
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        // 使用BCrypt加密密码
        return new BCryptPasswordEncoder();
    }

    /**
     * 非注解扫描,添加原生过滤器
     */
   /* @Bean
    public FilterRegistrationBean filterRegistrationBean(){
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new MyFilter());
        bean.addUrlPatterns("/*");
        return bean;
    }*/


    //重写方法，注册拦截器（InterceptorRegistry）
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //创建拦截器类
        HandlerInterceptor interceptor = new ReqParamInterceptor();

        List<String> patterns = new ArrayList<>();
        patterns.add("/css/**");
        patterns.add("/images/**");
        patterns.add("/js/**");
        patterns.add("/fonts/**");
        //注册拦截器类，添加黑名单(addPathPatterns("/**")),‘/*’只拦截一个层级，'/**'拦截全部
        // 和白名单(excludePathPatterns("List类型参数"))，将不必拦截的路径添加到List列表中
        registry.addInterceptor(interceptor).addPathPatterns("/**").excludePathPatterns(patterns).order(1);
    }

}
