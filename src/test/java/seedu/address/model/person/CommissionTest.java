package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;

public class CommissionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Commission(null));
    }

    @Test
    public void constructor_invalidCommission_throwsIllegalArgumentException() {
        String invalidCommission = "";
        assertThrows(IllegalArgumentException.class, () -> new Commission(invalidCommission));
    }

    @Test
    public void addValue_throwsIllegalArgumentException() {
        String bigCommission = "900000000";
        Commission commission = new Commission(bigCommission);
        Commission otherCommission = new Commission(bigCommission);
        assertThrows(IllegalValueException.class, () -> commission.addValue(otherCommission));
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
