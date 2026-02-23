package com.reviewer;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.DoubleLiteralExpr;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.TryStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        int statementCount = method.getBody()
                .map(b -> b.getStatements().size())
                .orElse(0);
        if (statementCount < 5) return;

        long commentCount = method.getAllContainedComments().size();
        if (commentCount == 0) {
            System.out.println("Method " + method.getName()
                    + " has no comments despite having " + statementCount + " statements.");
        }
    }

    private void checkMagicNumbers(MethodDeclaration method) {
        method.findAll(IntegerLiteralExpr.class).forEach(literal -> {
            int value = literal.asInt();
            if (value != 0 && value != 1) {
                System.out.println("Method " + method.getName()
                        + " contains a magic number: " + value);
            }
        });
        method.findAll(DoubleLiteralExpr.class).forEach(literal -> {
            double value = literal.asDouble();
            if (value != 0.0 && value != 1.0) {
                System.out.println("Method " + method.getName()
                        + " contains a magic number: " + value);
            }
        });
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
        List<String> declaredVars = method.findAll(VariableDeclarator.class).stream()
                .map(VariableDeclarator::getNameAsString)
                .collect(Collectors.toList());

        Set<String> usedNames = method.findAll(NameExpr.class).stream()
                .map(NameExpr::getNameAsString)
                .collect(Collectors.toSet());

        declaredVars.forEach(varName -> {
            if (!usedNames.contains(varName)) {
                System.out.println("Method " + method.getName()
                        + " has an unused variable: " + varName);
            }
        });
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
        method.findAll(TryStmt.class).forEach(tryStmt ->
                tryStmt.getCatchClauses().forEach(catchClause -> {
                    if (catchClause.getBody().getStatements().isEmpty()) {
                        System.out.println("Method " + method.getName()
                                + " has an empty catch block.");
                    }
                })
        );
    }

    // Add more rule methods here
}
