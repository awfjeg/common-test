package com.husher.junit;

import junit.framework.Test;
import junit.framework.TestResult;
import junit.framework.TestSuite;

import java.io.IOException;

/**
 * @author 776216
 */
public class FileReaderTest implements Test {
    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTest(new FileReaderTest());
        suite.addTest(new FileReaderTest());
        return suite;
    }

    @org.junit.Test
    public void testRead() throws IOException {
        char ch ='&';
//        for(int i = 0; i<4; i++)
            ch = (char) (ch +  100 * Math.random());
        assert('m' == ch);
    }

    @Override public int countTestCases() {
        return 0;
    }

    @Override public void run(TestResult result) {

    }
}
