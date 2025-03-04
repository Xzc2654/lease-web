package com.xzc.lease.common.mybatisplus;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.xzc.lease.web.*.mapper")
public class MybatisPlusConfiguration {

}
