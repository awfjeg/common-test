package com.husher.exception;

public class ExceMain {

    public static void main(String[] args) {
        ExceTestInterface service1 = new ExceTest1();
        ExceTestInterface service2 = new ExceTest2();
        service1.service();
        service2.service();
    }
}
