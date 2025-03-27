package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
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
        // Arrange: Define test data
        Path tempFile = tempDir.resolve("test.csv");
        Stream<Stream<?>> tokensStream = Stream.of(
                Stream.of("Alice", "24", "123 Main St, Apt 4B"),
                Stream.of("Bob", ",,,", "Designer"),
                Stream.of("Charlie", "28", "Doctor")
        );

        // Act: Write to CSV
        CsvUtil.writeToCsv(tokensStream, tempFile);

        // Assert: Read the file and verify content
        List<String> lines = Files.readAllLines(tempFile);
        assertEquals(3, lines.size());
        assertEquals("\"Alice\",\"24\",\"123 Main St, Apt 4B\"", lines.get(0));
        assertEquals("\"Bob\",\",,,\",\"Designer\"", lines.get(1));
        assertEquals("\"Charlie\",\"28\",\"Doctor\"", lines.get(2));
    }
}
