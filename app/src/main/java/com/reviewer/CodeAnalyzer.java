package com.reviewer;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;

import java.util.List;

public class CodeAnalyzer {
    public void analyze(CompilationUnit cu) {
        List<MethodDeclaration> methods = cu.findAll(MethodDeclaration.class);
        for (MethodDeclaration method : methods) {
            // Example analysis: Check for long methods
            if (method.getBody().isPresent() && method.getBody().get().getStatements().size() > 10) {
                System.out.println("Method " + method.getName() + " is too long.");
            }
        }
    }
}
