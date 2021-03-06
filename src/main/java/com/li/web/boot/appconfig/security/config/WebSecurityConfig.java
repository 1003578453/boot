package com.li.web.boot.appconfig.security.config;

import com.li.web.boot.filter.ReqParamFilter;
import com.li.web.boot.filter.UrlAuthFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.SecurityContextConfigurer;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Slf4j
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)//开启方法级安全验证
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDatailService;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;


    //@Value("#{'${security.allow.paths}'.split(',')}")
    @Value("${security.allow.paths}")
    private String[] allowPaths;

    @Value("${security.login.path}")
    private String loginPath;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 从数据库读取的用户进行身份认证
        log.info("Security初始化......WebSecurityConfig is ready");
        auth.authenticationProvider(authenticationProvider)/*.userDetailsService(userDatailService).passwordEncoder(passwordEncoder)*/;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.info("Security初始化......Security allow paths:{}",Arrays.asList(allowPaths));
        http
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, allowPaths).permitAll() // 允许post请求/add-user，而无需认证
                .anyRequest()//任何请求
                .authenticated(); // 所有请求都需要验证
        http
                .formLogin()
                .loginPage("/loginPage")// 自定义登录页面
                .loginProcessingUrl(loginPath)// 自定义登录路径
                // 定义登录时，用户名的 key，默认为 username
                .usernameParameter("uname")
                // 定义登录时，用户密码的 key，默认为 password
                .passwordParameter("passwd")
                .successHandler(new SecurityLoginSuccessHandler())
                //.successForwardUrl("/bbbbbbbb")
                .failureHandler(new SecurityLoginFailHandler());
                //.failureForwardUrl("/aaa")

        http
                .logout()//登出处理
                .logoutUrl("/logOut")//登出请求地址
                .logoutSuccessUrl("/loginPage")//登出后跳转的页面
                .logoutSuccessHandler(new SecurityLoginOutHandler());

    /*    http
                .sessionManagement().sessionAuthenticationFailureHandler(new AuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                log.info("------session失败处理");
            }
        });*/

        http
                .csrf().disable();// post请求要关闭csrf验证,不然访问报错；实际开发中开启，需要前端配合传递其他参数

        //添加自定义拦截器
         http.addFilterBefore(new ReqParamFilter(), WebAsyncManagerIntegrationFilter.class);
         http.addFilterBefore(new UrlAuthFilter(), WebAsyncManagerIntegrationFilter.class);
    }


}
