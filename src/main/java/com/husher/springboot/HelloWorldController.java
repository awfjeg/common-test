package com.husher.springboot;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Spring Boot HelloWorld案例
 *
 * @author 776216
 */
@RestController
public class HelloWorldController {
    @RequestMapping("/")
    public String sayHello() {
        return "Hello,World!";
    }
}
