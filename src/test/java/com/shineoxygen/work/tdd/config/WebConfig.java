package com.shineoxygen.work.tdd.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource("classpath:/applicationContext-*test.xml")
public class WebConfig {

}
