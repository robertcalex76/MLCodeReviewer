package com.reviewer;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class RuleEngine {
    public void applyRules(MethodDeclaration method) {
        checkMethodLength(method);
        checkMethodNameLength(method);
        checkParameterCount(method);
        checkCommentDensity(method);
        checkMagicNumbers(method);
        checkNestedBlockDepth(method);
        checkUnusedVariables(method);
        checkMethodReturnType(method);
        checkMethodVisibility(method);
        checkExceptionHandling(method);
        // Add more rules here
    }

    private void checkMethodLength(MethodDeclaration method) {
        if (method.getBody().isPresent() && method.getBody().get().getStatements().size() > 10) {
            System.out.println("Method " + method.getName() + " is too long.");
        }
    }

    private void checkMethodNameLength(MethodDeclaration method) {
        if (method.getNameAsString().length() > 20) {
            System.out.println("Method name " + method.getName() + " is too long.");
        }
    }

    private void checkParameterCount(MethodDeclaration method) {
        if (method.getParameters().size() > 5) {
            System.out.println("Method " + method.getName() + " has too many parameters.");
        }
    }

    private void checkCommentDensity(MethodDeclaration method) {
        // Implement logic to check comment density
    }

    private void checkMagicNumbers(MethodDeclaration method) {
        // Implement logic to detect magic numbers
    }

    private void checkNestedBlockDepth(MethodDeclaration method) {
        method.accept(new VoidVisitorAdapter<Void>() {
            private int depth = 0;
            private int maxDepth = 0;

            @Override
            public void visit(BlockStmt n, Void arg) {
                depth++;
                if (depth > maxDepth) {
                    maxDepth = depth;
                }
                super.visit(n, arg);
                depth--;
            }

            @Override
            public void visit(MethodDeclaration n, Void arg) {
                super.visit(n, arg);
                if (maxDepth > 3) {
                    System.out.println("Method " + n.getName() + " has too many nested blocks.");
                }
            }
        }, null);
    }

    private void checkUnusedVariables(MethodDeclaration method) {
        // Implement logic to detect unused variables
    }

    private void checkMethodReturnType(MethodDeclaration method) {
        if (method.getType().isVoidType()) {
            System.out.println("Method " + method.getName() + " has a void return type.");
        }
    }

    private void checkMethodVisibility(MethodDeclaration method) {
        if (!method.isPublic()) {
            System.out.println("Method " + method.getName() + " is not public.");
        }
    }

    private void checkExceptionHandling(MethodDeclaration method) {
        // Implement logic to check exception handling
    }

    // Add more rule methods here
}
