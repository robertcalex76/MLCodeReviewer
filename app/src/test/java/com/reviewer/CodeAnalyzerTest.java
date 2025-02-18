package com.reviewer;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CodeAnalyzerTest {
    private CodeAnalyzer codeAnalyzer = new CodeAnalyzer();

    @Test
    public void testAnalyzeSimpleClass() {
        String code = "public class TestClass { " +
                      "public void simpleMethod() { " +
                      "int a = 0; " +
                      "} }";
        CompilationUnit cu = new JavaParser().parse(code).getResult().orElse(null);
        assertNotNull(cu);

        codeAnalyzer.analyze(cu);
        // Check console output manually or use a logging framework to capture and assert the output
    }

    @Test
    public void testAnalyzeClassWithLongMethod() {
        String code = "public class TestClass { " +
                      "public void longMethod() { " +
                      "int a = 0; int b = 1; int c = 2; int d = 3; int e = 4; " +
                      "int f = 5; int g = 6; int h = 7; int i = 8; int j = 9; int k = 10; " +
                      "} }";
        CompilationUnit cu = new JavaParser().parse(code).getResult().orElse(null);
        assertNotNull(cu);

        codeAnalyzer.analyze(cu);
        // Check console output manually or use a logging framework to capture and assert the output
    }

    @Test
    public void testAnalyzeClassWithComplexMethods() {
        String code = "public class TestClass { " +
                      "public void complexMethod() { " +
                      "if (true) { for (int i = 0; i < 10; i++) { while (true) { } } } " +
                      "} }";
        CompilationUnit cu = new JavaParser().parse(code).getResult().orElse(null);
        assertNotNull(cu);

        codeAnalyzer.analyze(cu);
        // Check console output manually or use a logging framework to capture and assert the output
    }

    // Add more tests for different scenarios
}
