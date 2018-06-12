package com.shyam.advertiserservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@SpringBootApplication
@EnableSwagger2
public class AdvertiserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdvertiserServiceApplication.class, args);
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("com.shyam.advertiserservice.controller"))
                .build()
                .apiInfo(new ApiInfo(
                        "Advertiser server",
                        "Advertiser microservice",
                        "1.0",
                        "",
                        new Contact("", "", ""),
                        "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0", Collections.emptyList()));
    }
}
