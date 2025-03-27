package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.nio.file.Path;

/**
 * Represents a valid CsvFileName for export.
 * Guarantees: immutable; is valid as declared in {@link #isValidCsvFileName(String)}
 */
public class CsvFileName {

    public static final String MESSAGE_CONSTRAINTS =
            "A CSV file name should only contain alphanumeric characters, and end with .csv";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "^[a-zA-Z0-9]+\\.csv$";

    public final String csvFileName;

    /**
     * Constructs a {@code CsvFileName}.
     *
     * @param csvFileName A valid CSV file name.
     */
    public CsvFileName(String csvFileName) {
        requireNonNull(csvFileName);
        checkArgument(isValidCsvFileName(csvFileName), MESSAGE_CONSTRAINTS);
        this.csvFileName = csvFileName;
    }

    /**
     * Returns true if a given string is a valid CSV file name.
     */
    public static boolean isValidCsvFileName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public Path asPath() {
        return Path.of(csvFileName);
    }

    @Override
    public String toString() {
        return csvFileName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CsvFileName otherCsvFileName)) {
            return false;
        }

        return csvFileName.equals(otherCsvFileName.csvFileName);
    }

    @Override
    public int hashCode() {
        return csvFileName.hashCode();
    }

}
