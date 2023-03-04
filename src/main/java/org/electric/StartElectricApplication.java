package org.electric;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author Administrator
 * @version 1.0
 **/
@EnableFeignClients
@SpringBootApplication
public class StartElectricApplication {

    public static void main(String[] args) {
        SpringApplication.run(StartElectricApplication.class, args);
    }

//    @Bean
////    public WebMvcConfigurer corsConfigurer() {
////        return new WebMvcConfigurerAdapter() {
////            public void addCorsMappings(CorsRegistry registry) {
////                registry.addMapping("/**")
////                        .allowedOrigins("127.0.0.1") //可访问ip，ip最好从配置文件中获取，
////                        .allowedMethods("PUT", "DELETE", "GET", "POST")
////                        .allowedHeaders("*")
////                        .exposedHeaders("access-control-allow-headers",
////                                "access-control-allow-methods",
////                                "access-control-allow-origin",
////                                "access-control-max-age",
////                                "X-Frame-Options")
////                        .allowCredentials(false).maxAge(3600);
////            }
////        };
////
////    }
}
