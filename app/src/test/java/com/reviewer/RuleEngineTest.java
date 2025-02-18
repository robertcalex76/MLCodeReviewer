package com.reviewer;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RuleEngineTest {
    private RuleEngine ruleEngine = new RuleEngine();

    @Test
    public void testMethodLengthRule() {
        String code = "public class TestClass { " +
                      "public void longMethod() { " +
                      "int a = 0; int b = 1; int c = 2; int d = 3; int e = 4; " +
                      "int f = 5; int g = 6; int h = 7; int i = 8; int j = 9; int k = 10; " +
                      "} }";
        CompilationUnit cu = new JavaParser().parse(code).getResult().orElse(null);
        assertNotNull(cu);

        List<MethodDeclaration> methods = cu.findAll(MethodDeclaration.class);
        assertFalse(methods.isEmpty());

        MethodDeclaration method = methods.get(0);
        ruleEngine.applyRules(method);
        // Check console output manually or use a logging framework to capture and assert the output
    }

    @Test
    public void testMethodNameLengthRule() {
        String code = "public class TestClass { " +
                      "public void thisIsAVeryLongMethodNameThatShouldTriggerTheRule() { } }";
        CompilationUnit cu = new JavaParser().parse(code).getResult().orElse(null);
        assertNotNull(cu);

        List<MethodDeclaration> methods = cu.findAll(MethodDeclaration.class);
        assertFalse(methods.isEmpty());

        MethodDeclaration method = methods.get(0);
        ruleEngine.applyRules(method);
        // Check console output manually or use a logging framework to capture and assert the output
    }

    @Test
    public void testParameterCountRule() {
        String code = "public class TestClass { " +
                      "public void methodWithTooManyParameters(int a, int b, int c, int d, int e, int f) { } }";
        CompilationUnit cu = new JavaParser().parse(code).getResult().orElse(null);
        assertNotNull(cu);

        List<MethodDeclaration> methods = cu.findAll(MethodDeclaration.class);
        assertFalse(methods.isEmpty());

        MethodDeclaration method = methods.get(0);
        ruleEngine.applyRules(method);
        // Check console output manually or use a logging framework to capture and assert the output
    }

    // Add more tests for other rules
}
