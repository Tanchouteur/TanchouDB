import fr.tanchou.structure.utils.IStorageManager;
import fr.tanchou.structure.utils.LocalStorageManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class LocalStorageManagerTest {

    @TempDir
    Path tempDir;

    @Test
    void testWriteToFileAndReadFromFile() throws IOException {
        // Create a temporary file
        File file = tempDir.resolve("testFile.txt").toFile();
        String filename = file.getAbsolutePath();

        // Test content to write and read
        String content = "Hello, this is a test content!";

        IStorageManager storageManager = new LocalStorageManager();

        // Write content to the file
        storageManager.writeToFile(filename, content);

        // Read content from the file
        String readContent = storageManager.readFromFile(filename);

        // Verify that the content read matches the content written
        assertEquals(content + "\n", readContent);
    }

    @Test
    void testWriteToFileWithEmptyContent() throws IOException {
        // Create a temporary file
        File file = tempDir.resolve("emptyFile.txt").toFile();
        String filename = file.getAbsolutePath();

        IStorageManager storageManager = new LocalStorageManager();

        // Test empty content
        String content = "";

        // Write empty content to the file
        storageManager.writeToFile(filename, content);

        // Read content from the file
        String readContent = storageManager.readFromFile(filename);

        // Verify that the content read is empty
        assertEquals("", readContent);
    }

    @Test
    void testReadFromFileNotFound() {
        // Non-existent file path
        String filename = tempDir.resolve("nonExistentFile.txt").toAbsolutePath().toString();

        IStorageManager storageManager = new LocalStorageManager();

        // Attempt to read from the non-existent file
        String readContent = storageManager.readFromFile(filename);

        // Verify that the content is empty (since the file doesn't exist)
        assertEquals("", readContent);
    }

    @Test
    void testWriteToFileInvalidPath() {
        // Invalid file path (directory instead of file)
        String invalidPath = tempDir.toAbsolutePath().toString();

        IStorageManager storageManager = new LocalStorageManager();

        // Attempt to write to the invalid path
        storageManager.writeToFile(invalidPath, "Test content");
    }

    @Test
    void testReadFromFileEmptyFile() throws IOException {
        // Create an empty temporary file
        File file = tempDir.resolve("emptyFile.txt").toFile();
        String filename = file.getAbsolutePath();

        IStorageManager storageManager = new LocalStorageManager();

        // Read content from the empty file
        String readContent = storageManager.readFromFile(filename);

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

        IStorageManager storageManager = new LocalStorageManager();

        // Write content to the file
        storageManager.writeToFile(filename, content);

        // Read content from the file
        String readContent = storageManager.readFromFile(filename);

        // Verify that the content read matches the content written
        assertEquals(content + "\n", readContent);
    }
}