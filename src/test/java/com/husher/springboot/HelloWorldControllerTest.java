package com.husher.springboot;

import junit.framework.TestCase;
import org.junit.Test;

public class HelloWorldControllerTest extends TestCase {

    @Test
    public void testSayHello() {
        System.out.println(System.getProperty("home.province"));
        assertEquals("Hello,World!",new HelloWorldController().sayHello());
    }
}