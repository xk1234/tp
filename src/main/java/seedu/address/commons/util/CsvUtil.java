package seedu.address.commons.util;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Writes CSV files
 */
public class CsvUtil {
    /**
     * Writes the given stream of token streams to a CSV file at the specified path.
     * Each inner stream represents a row in the CSV file, and its elements are joined by commas.
     * Double quotes surround all entries to deal with commas in the input.
     *
     * @param tokensStream A stream of streams where each inner stream represents a row in the CSV file.
     * @param path The file path where the CSV should be written.
     * @throws IOException If an I/O error occurs while writing to the file.
     */
    public static void writeToCsv(Stream<Stream<?>> tokensStream, Path path) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(path, CREATE_NEW)) {
            // Write CSV body
            for (Stream<?> tokens : (Iterable<Stream<?>>) tokensStream::iterator) {
                writer.write(tokens.map(token -> String.format("\"%s\"", token)).collect(Collectors.joining(",")));
                writer.newLine(); // Add a new line after each row
            }
        }
    }
}
