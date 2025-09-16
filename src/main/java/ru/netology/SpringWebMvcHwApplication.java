package ru.netology;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("ru.netology")
public class SpringWebMvcHwApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringWebMvcHwApplication.class, args);
    }

}