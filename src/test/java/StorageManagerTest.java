import fr.tanchou.storage.StorageManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class StorageManagerTest {

    @TempDir
    Path tempDir;

    @Test
    void testWriteToFileAndReadFromFile() throws IOException {
        // Create a temporary file
        File file = tempDir.resolve("testFile.txt").toFile();
        String filename = file.getAbsolutePath();

        // Test content to write and read
        String content = "Hello, this is a test content!";

        // Write content to the file
        StorageManager.writeToFile(filename, content);

        // Read content from the file
        String readContent = StorageManager.readFromFile(filename);

        // Verify that the content read matches the content written
        assertEquals(content + "\n", readContent);
    }

    @Test
    void testWriteToFileWithEmptyContent() throws IOException {
        // Create a temporary file
        File file = tempDir.resolve("emptyFile.txt").toFile();
        String filename = file.getAbsolutePath();

        // Test empty content
        String content = "";

        // Write empty content to the file
        StorageManager.writeToFile(filename, content);

        // Read content from the file
        String readContent = StorageManager.readFromFile(filename);

        // Verify that the content read is empty
        assertEquals("", readContent);
    }

    @Test
    void testReadFromFileNotFound() {
        // Non-existent file path
        String filename = tempDir.resolve("nonExistentFile.txt").toAbsolutePath().toString();

        // Attempt to read from the non-existent file
        String readContent = StorageManager.readFromFile(filename);

        // Verify that the content is empty (since the file doesn't exist)
        assertEquals("", readContent);
    }

    @Test
    void testWriteToFileInvalidPath() {
        // Invalid file path (directory instead of file)
        String invalidPath = tempDir.toAbsolutePath().toString();

        // Attempt to write to the invalid path
        StorageManager.writeToFile(invalidPath, "Test content");
    }

    @Test
    void testReadFromFileEmptyFile() throws IOException {
        // Create an empty temporary file
        File file = tempDir.resolve("emptyFile.txt").toFile();
        String filename = file.getAbsolutePath();
        file.createNewFile(); // Create an empty file

        // Read content from the empty file
        String readContent = StorageManager.readFromFile(filename);

        // Verify that the content is empty
        assertEquals("", readContent);
    }

    @Test
    void testWriteToFileWithMultipleLines() throws IOException {
        // Create a temporary file
        File file = tempDir.resolve("multiLineFile.txt").toFile();
        String filename = file.getAbsolutePath();

        // Test content with multiple lines
        String content = "Line 1\nLine 2\nLine 3";

        // Write content to the file
        StorageManager.writeToFile(filename, content);

        // Read content from the file
        String readContent = StorageManager.readFromFile(filename);

        // Verify that the content read matches the content written
        assertEquals(content + "\n", readContent);
    }
}