package com.husher.junit;

import static com.husher.junit.FileReaderTest.suite;

public class JunitSuite {
    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }
}
