# MLCodeReviewer — Project Assessment & Execution Plan

> Last updated: 2026-02-22

---

## Current State Assessment

The project is a Java code review CLI tool using AST (Abstract Syntax Tree) analysis to detect
code quality issues. Despite the "ML" name, the current implementation is entirely rule-based
using the [JavaParser](https://javaparser.org/) library.

### What exists
- Working CLI entry point (`Main.java`)
- AST parsing pipeline (`ASTParser.java`)
- Rule engine with 10 rules, 4 of which are stubs (`RuleEngine.java`)
- Unit tests for all components
- GitHub Actions for automated PR review and testing

### Core gaps
1. 4 unimplemented rule stubs: `checkCommentDensity`, `checkMagicNumbers`,
   `checkUnusedVariables`, `checkExceptionHandling`
2. No structured output — violations are printed as raw strings with no severity or categorization
3. No actual ML component (the `model/` directory is empty)
4. GitHub Action posts raw stdout as PR comments, which is unformatted
5. JDK version mismatch — `code-review.yml` uses JDK 11, `tests.yml` uses JDK 17

---

## Execution Plan

### Phase 1 — Complete the rule engine (immediate priority)
Status: **Complete**

Four stub rules exist in `RuleEngine.java` and need to be implemented:

- [x] `checkMagicNumbers()` — traverse method body for integer/float literals that aren't 0 or 1
- [x] `checkExceptionHandling()` — detect empty catch blocks via JavaParser's `CatchClause` visitor
- [x] `checkUnusedVariables()` — track declared local variables vs referenced names in scope
- [x] `checkCommentDensity()` — compare comment count to statement count using `CommentsCollection`
- [x] Fix JDK version mismatch (standardized both workflows on JDK 17)
- [x] Upgraded Gradle wrapper from 8.12.1 → 8.14.4 (required for JDK 25 compatibility)

### Phase 2 — Structured output and severity levels
Right now violations are unstructured text. Before adding more complexity, the output model
needs to be formalized.

- [ ] Create a `Violation` model: `{ rule, severity (INFO/WARN/ERROR), methodName, line, message }`
- [ ] Update `RuleEngine` to return `List<Violation>` instead of printing directly
- [ ] Update `CodeAnalyzer` to aggregate and return violations
- [ ] Implement a `Reporter` class that formats output (plain text and JSON modes)
- [ ] Update the GitHub Action to post a clean, formatted markdown comment on PRs

### Phase 3 — Expand rule coverage
With a clean output model in place, add more detection rules:

- [ ] Naming convention checks (classes, constants)
- [ ] `System.out.println` detection (should use a logger)
- [ ] Duplicate code detection (identical method bodies)
- [ ] Null return detection
- [ ] Missing `@Override` annotation detection

### Phase 4 — ML integration
This is the stretch goal from the README. Two options:

**Option A — LLM integration (lower effort)**
Call an LLM API (e.g., OpenAI or Anthropic) with the method source and prompt for a review.
Low engineering effort, high output quality.

**Option B — Train a classifier (closer to "ML")**
Collect labeled Java code samples (good vs bad patterns), extract AST features, and train a
simple classifier (logistic regression or gradient boosting) to predict violation likelihood.
The empty `model/` and `scripts/` directories were reserved for this path.

---

## Architecture Notes

```
app/src/main/java/com/reviewer/
├── Main.java          CLI entry point — parses args, calls CodeAnalyzer
├── CodeAnalyzer.java  Orchestrator — finds methods, applies rules
├── ASTParser.java     Wraps JavaParser — parses a .java file into a CompilationUnit
├── RuleEngine.java    Contains all rule implementations
└── LoggerUtil.java    Java logging setup
```

### How a rule should work
Each rule method in `RuleEngine` receives a `MethodDeclaration` from JavaParser's AST.
It inspects the node (and its children via visitors or direct API calls) and logs a warning
if a violation is found. After Phase 2, rules will return `List<Violation>` instead.

### Key JavaParser APIs used
- `MethodDeclaration.getBody()` — get method body statements
- `MethodDeclaration.getParameters()` — get parameter list
- `NodeList<Statement>` — iterable list of statements
- `IfStmt`, `ForStmt`, `WhileStmt`, `TryStmt` — specific statement types
- `IntegerLiteralExpr`, `DoubleLiteralExpr` — literal value nodes
- `NameExpr` — variable/name references
- `VariableDeclarator` — local variable declarations
- `CommentsCollection` — collected comments from a parsed file
