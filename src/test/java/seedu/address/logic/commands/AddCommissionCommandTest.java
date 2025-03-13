package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.model.person.Commission;

public class AddCommissionCommandTest {

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        Commission validCommission = new Commission("100");
        assertThrows(NullPointerException.class, () ->
                new AddCommissionCommand(null, validCommission));
    }

    @Test
    public void constructor_nullCommission_throwsNullPointerException() {
        Index validIndex = Index.fromOneBased(1);
        assertThrows(NullPointerException.class, () ->
                new AddCommissionCommand(validIndex, null));
    }

    @Test
    public void constructor_validArguments_success() {
        Index validIndex = Index.fromOneBased(1);
        Commission validCommission = new Commission("100");
        AddCommissionCommand command = new AddCommissionCommand(validIndex, validCommission);

        assertEquals(command, new AddCommissionCommand(validIndex, validCommission));
    }
}
