package com.reviewer;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class CodeAnalyzerTest {
    @Test
    public void testAnalyze() throws Exception {
        Path path = Paths.get("../data/SampleCode.java");
        assertTrue(Files.exists(path));

        String code = new String(Files.readAllBytes(path));
        CompilationUnit cu = new JavaParser().parse(code).getResult().orElse(null);
        assertNotNull(cu);

        CodeAnalyzer analyzer = new CodeAnalyzer();
        analyzer.analyze(cu);
    }
}
