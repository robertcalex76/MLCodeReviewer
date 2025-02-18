package com.reviewer;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MainTest {
    @Test
    public void testMainWithNoArguments() {
        String[] args = {};
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Main.run(args);
        });
        assertEquals("Usage: java -jar MLCodeReviewer.jar <path-to-java-file>", exception.getMessage());
    }

    @Test
    public void testMainWithInvalidFilePath() {
        String[] args = {"invalid/path/to/file.java"};
        Exception exception = assertThrows(RuntimeException.class, () -> {
            Main.run(args);
        });
        assertTrue(exception.getMessage().contains("Error processing the file"));
    }

    // Add more tests as needed
}
