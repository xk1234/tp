package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class CsvUtilTest {

    @TempDir
    Path tempDir;

    @Test
    void writeToCsv_validInput_writesCorrectly() throws IOException {
        Path tempFile = tempDir.resolve("test.csv");
        Stream<Stream<?>> tokensStream = Stream.of(
                Stream.of("Alice", "24", "123 Main St, Apt 4B"),
                Stream.of("Bob", ",,,", "Designer"),
                Stream.of("Charlie", "28", "Doctor")
        );

        CsvUtil.writeToCsv(tokensStream, tempFile);

        List<String> lines = Files.readAllLines(tempFile);
        assertEquals(3, lines.size());
        assertEquals("\"Alice\",\"24\",\"123 Main St, Apt 4B\"", lines.get(0));
        assertEquals("\"Bob\",\",,,\",\"Designer\"", lines.get(1));
        assertEquals("\"Charlie\",\"28\",\"Doctor\"", lines.get(2));
    }

    @Test
    void writeToCsv_emptyStream_writesCorrectly() throws IOException {
        Path tempFile = tempDir.resolve("test.csv");
        Stream<Stream<?>> tokensStream = Stream.empty();

        CsvUtil.writeToCsv(tokensStream, tempFile);

        List<String> lines = Files.readAllLines(tempFile);
        assertEquals(0, lines.size());
    }

    @Test
    void writeToCsv_fileAlreadyExists_throwsFileAlreadyExistsException() throws IOException {
        Path tempFile = tempDir.resolve("test.csv");
        Files.createFile(tempFile);
        Stream<Stream<?>> tokensStream = Stream.empty();
        assertTrue(Files.exists(tempFile));
        assertThrows(FileAlreadyExistsException.class, () -> CsvUtil.writeToCsv(tokensStream, tempFile));
    }
}
