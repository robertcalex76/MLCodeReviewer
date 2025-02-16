package com.reviewer;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ASTParser {
    public static CompilationUnit parseFile(String filePath) throws Exception {
        Path path = Paths.get(filePath).toAbsolutePath();
        System.out.println("Resolved file path: " + path);
        
        if (!Files.exists(path)) {
            System.err.println("File not found: " + path);
            return null;
        }
    
        String code = new String(Files.readAllBytes(path));
    
        CompilationUnit cu = new JavaParser().parse(code).getResult().orElse(null);
        if (cu != null) {
            cu.findAll(MethodDeclaration.class).forEach(method ->
                System.out.println("Found method: " + method.getName())
            );
        } else {
            System.err.println("Failed to parse the Java file.");
        }
        return cu;
    }
}
