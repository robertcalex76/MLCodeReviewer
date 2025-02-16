package com.reviewer;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ASTParserTest {
    @Test
    public void testParseFile() throws Exception {
        Path path = Paths.get("../data/SampleCode.java");
        assertTrue(Files.exists(path));

        ASTParser.parseFile(path.toString());
    }
}
