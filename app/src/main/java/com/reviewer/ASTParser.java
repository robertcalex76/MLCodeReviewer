package com.reviewer;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ASTParser {
    public static void parseFile(String filePath) throws Exception {
        // Get the project root (one level above 'app/')
        Path projectRoot = Paths.get(System.getProperty("user.dir")).getParent();
        
        // Construct the correct absolute path
        Path path = projectRoot.resolve(filePath).toAbsolutePath();
        
        if (!Files.exists(path)) {
            System.err.println("File not found: " + path);
            return;
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
    }
    
}
