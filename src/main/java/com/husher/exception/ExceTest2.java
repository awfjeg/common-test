package com.husher.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 异常test2
 *
 * @author caiming
 * @version 1.0.0
 * @date 2022/01/04
 */
public class ExceTest2 implements ExceTestInterface{
    Logger logger = LoggerFactory.getLogger(ExceTest2.class);
    @Override
    public void service() {
        logger.error("org.slf4j.LoggerFactory", new Exception("org.slf4j.LoggerFactory test!"));
    }
}
