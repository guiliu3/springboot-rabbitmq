package com.atguigu.springbootrabbitmq.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author liugui
 * @version V1.0
 * @Package com.atguigu.springbootrabbitmq.config
 * @date 2022/4/23 16:51
 * @Copyright © 上海卓繁
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket DocketwebApiConfig() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("webApi").apiInfo(webApiInfo()).select().build();
    }

    private ApiInfo webApiInfo() {
        return new ApiInfoBuilder()
                .title("rabbitmq接口文档")
                .description("本文档描述了rabbitmq微服务接口定义")
                .version("1.0")
                .contact(new Contact("guiliu3", "www.baidu.com", "1446264700@qq.com")).build();
    }
}

