package com.reviewer;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;

import java.util.List;

public class CodeAnalyzer {
    private RuleEngine ruleEngine = new RuleEngine();

    public void analyze(CompilationUnit cu) {
        List<MethodDeclaration> methods = cu.findAll(MethodDeclaration.class);
        for (MethodDeclaration method : methods) {
            ruleEngine.applyRules(method);
        }
    }
}
