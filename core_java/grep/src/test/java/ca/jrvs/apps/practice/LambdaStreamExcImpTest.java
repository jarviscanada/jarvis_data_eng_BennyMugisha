package ca.jrvs.apps.practice;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class LambdaStreamExcImpTest {

    LambdaStreamExcImp lambdaStreamExcImp = new LambdaStreamExcImp();
    @Test
    void createStrStream() {
        Stream<String> a = lambdaStreamExcImp.createStrStream("car", "plane", "table");
        String[] b = {"car", "plane", "table"};
        assertEquals(Arrays.toString(b), Arrays.toString(a.toArray()));
    }

    @Test
    void toUpperCase() {
        Stream<String> a = lambdaStreamExcImp.toUpperCase("car", "plane", "table");
        String[] b = {"CAR", "PLANE", "TABLE"};
        assertEquals(Arrays.toString(b), Arrays.toString(a.toArray()));
    }

    @Test
    void filter() {
        Stream<String> a = lambdaStreamExcImp.filter(lambdaStreamExcImp.createStrStream("car", "plane", "table"), "e");
        String[] b = {"plane", "table"};
        assertEquals(Arrays.toString(b), Arrays.toString(a.toArray()));
    }

    @Test
    void createIntStream() {
        IntStream a = lambdaStreamExcImp.createIntStream(new int[]{1, 3, 4, 5, 6});
        int[] b = {1, 3, 4, 5, 6};
        assertEquals(Arrays.toString(b), Arrays.toString(a.toArray()));
    }

    @Test
    void toList() {
    }

    @Test
    void testToList() {
    }

    @Test
    void testCreateIntStream() {
    }

    @Test
    void squareRootIntStream() {
    }

    @Test
    void getOdd() {
    }

    @Test
    void getLambdaPrinter() {
    }

    @Test
    void printMessages() {
    }

    @Test
    void printOdd() {
    }

    @Test
    void flatNestedInt() {
    }
}