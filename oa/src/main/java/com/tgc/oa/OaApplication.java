package com.tgc.oa;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//扫描dao
@MapperScan("com.tgc.oa.dao")
@SpringBootApplication
public class OaApplication{
    public static void main(String[] args) {
        SpringApplication.run(OaApplication.class, args);
    }

}
