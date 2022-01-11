package com.husher.exception;

import org.apache.log4j.Logger;

/**
 * 异常test1
 *
 * @author caiming
 * @version 1.0.0
 * @date 2022/01/04
 */
public class ExceTest1 implements ExceTestInterface{
    Logger logger = Logger.getLogger(ExceTest1.class);
    @Override
    public void service() {
        logger.error("org.apache.log4j.Logger", new Exception("org.apache.log4j.Logger test!"));
    }
}
