package com.husher;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Jdk8Stream {
    public static void main(String[] args) throws IOException {
        Stream<String> lines = Files.lines(Paths.get("F:\\GSMS1.txt"), StandardCharsets.UTF_8);
        Files.lines(Paths.get("F:\\GSMS1.txt"), StandardCharsets.UTF_8).forEach(System.out::println);
        System.out.println("=========================================");
        Stream<String> words = lines.flatMap(line -> Stream.of(line.split(" +")));
        words.forEach(System.out::println);
        testFlatMap();
        testFilter();
    }

    private static void testFilter() {
        // 留下偶数
        Integer[] sixNums = {1, 2, 3, 4, 5, 6};
        Integer[] evens =
                Stream.of(sixNums).filter(n -> n%2 == 0).toArray(Integer[]::new);
        Stream.of(sixNums).filter(n -> n%2 == 0).forEach(System.out::print);
    }

    private static void testFlatMap() {
        Stream<List<Integer>> inputStream = Stream.of(Arrays.asList(1), Arrays.asList(2, 3), Arrays.asList(4, 5, 6));
        Stream<Integer> outputStream = inputStream.flatMap((childList) -> childList.stream());
        outputStream.forEach(System.out::println);
    }
}
