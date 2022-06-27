package com.li.web.boot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@MapperScan("com.li.web.boot.btuser.mapper")
@ServletComponentScan("com.li.web.boot.filter")
public class BootApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(BootApplication.class, args);
        for (String name:
        run.getBeanDefinitionNames()) {
            System.out.println(name);
        }
    }
}
