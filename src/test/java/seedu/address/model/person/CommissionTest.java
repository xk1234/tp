package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class CommissionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Commission(null));
    }

    @Test
    public void equals() {
        Commission comm = new Commission("12332");

        // same values -> returns true
        assertTrue(comm.equals(new Commission("12332")));

        // same object -> returns true
        assertTrue(comm.equals(comm));

        // null -> returns false
        assertFalse(comm.equals(null));

        // different types -> returns false
        assertFalse(comm.equals(12332));

        // different values -> returns false
        assertFalse(comm.equals(new Commission("12331")));
    }
}
