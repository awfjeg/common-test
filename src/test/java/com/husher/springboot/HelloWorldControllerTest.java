package com.husher.springboot;

import junit.framework.TestCase;
import org.junit.Test;

public class HelloWorldControllerTest extends TestCase {

    @Test
    public void testSayHello() {
        assertEquals("Hello,World!",new HelloWorldController().sayHello());
    }
}