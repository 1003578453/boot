package com.li.web.boot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
class BootApplicationTests {

    @Autowired
    RestTemplate restTemplate;

    @Test
    void contextLoads() {
        String url="https://jrzhfw.zjjf.org.cn//open-platform//mortgagePerson/estate/details";
        //restTemplate.postForEntity();
    }

}
