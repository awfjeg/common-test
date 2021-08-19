package com.husher.springboot;

import com.husher.springboot.property.HomeProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot应用启动类
 * @author 776216
 */

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private HomeProperties homeProperties;

    public static void main(String[] args) {

        SpringApplication.run(Application.class,args);

    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("\n" + homeProperties.toString());
        System.out.println();
    }

}