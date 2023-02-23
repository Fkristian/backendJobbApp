package se.kth.iv1201.appserv.jobapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class JobAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(JobAppApplication.class, args);
    }

//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/admin").allowedOrigins("http://localhost:3000");
//                registry.addMapping("/").allowedOrigins("http://localhost:3000");
//
//            }
//        };
//    }

}
//fixa cors i denna och