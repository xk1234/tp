package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Name;

public class CsvFileNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Name(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new CsvFileName(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> CsvFileName.isValidCsvFileName(null));

        // invalid name
        assertFalse(CsvFileName.isValidCsvFileName("")); // empty string
        assertFalse(CsvFileName.isValidCsvFileName("x .csv")); // contains space
        assertFalse(CsvFileName.isValidCsvFileName(".csv")); // missing prefix
        assertFalse(CsvFileName.isValidCsvFileName("csv.")); // missing suffix
        assertFalse(CsvFileName.isValidCsvFileName("*.csv")); // contains non-alphanumeric characters
        assertFalse(CsvFileName.isValidCsvFileName(".csv*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(CsvFileName.isValidCsvFileName("peterjack.csv")); // alphabets only
        assertTrue(CsvFileName.isValidCsvFileName("12345.csv")); // numbers only
    }

    @Test
    public void equals() {
        CsvFileName name = new CsvFileName("valid.csv");

        // same values -> returns true
        assertTrue(name.equals(new CsvFileName("valid.csv")));

        // same object -> returns true
        assertTrue(name.equals(name));

        // null -> returns false
        assertFalse(name.equals(null));

        // different types -> returns false
        assertFalse(name.equals(5.0f));

        // different values -> returns false
        assertFalse(name.equals(new CsvFileName("othervalid.csv")));
    }
}
