package com.husher.jansi;

import static org.fusesource.jansi.Ansi.*;
import static org.fusesource.jansi.Ansi.Color.*;

public class JansiTest {
    public static void main(String[] args) {

        int i = 1 % 7;
        System.out.println( ansi().eraseScreen().fg(RED).a(i).fg(YELLOW).a(" World").reset() );
        System.out.println( ansi().eraseScreen().fg(RED).a(i).fg(GREEN).a(" World") );
    }
}
