package seedu.address.logic.commands;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMMISSION_HUNDRED;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Commission;

public class AddCommissionCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        Commission commission = new Commission(VALID_COMMISSION_HUNDRED);
        AddCommissionCommand addCommissionCommand = new AddCommissionCommand(outOfBoundIndex, commission);

        assertCommandFailure(addCommissionCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        Commission validCommission = new Commission(VALID_COMMISSION_HUNDRED);
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
        Commission validCommission = new Commission(VALID_COMMISSION_HUNDRED);
        AddCommissionCommand command = new AddCommissionCommand(validIndex, validCommission);

        assertEquals(command, new AddCommissionCommand(validIndex, validCommission));
    }

    @Test
    public void execute_commandSuccess() {
        Index validIndex = Index.fromOneBased(1);
        Commission validCommission = new Commission(VALID_COMMISSION_HUNDRED);
        assertCommandSuccess(new AddCommissionCommand(validIndex, validCommission),
                model, AddCommissionCommand.MESSAGE_ADD_COMMISSION_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        Index validIndex = Index.fromOneBased(1);
        Index validIndex2 = Index.fromOneBased(2);
        Commission validCommission = new Commission(VALID_COMMISSION_HUNDRED);

        AddCommissionCommand firstCommand = new AddCommissionCommand(validIndex, validCommission);
        // same object -> return true
        assertTrue(firstCommand.equals(firstCommand));

        // same values -> return true
        assertTrue(firstCommand.equals(new AddCommissionCommand(validIndex, validCommission)));

        // different types -> returns false
        assertFalse(firstCommand.equals(1));

        // null -> returns false
        assertFalse(firstCommand.equals(null));

        // different commissions -> return false
        assertFalse(firstCommand.equals(new AddCommissionCommand(validIndex2, validCommission)));
    }

    @Test
    public void toStringMethod() {
        Index validIndex = Index.fromOneBased(1);
        Commission validCommission = new Commission(VALID_COMMISSION_HUNDRED);
        AddCommissionCommand command = new AddCommissionCommand(validIndex, validCommission);
        String expectedString = new ToStringBuilder(command)
                .add("index", validIndex)
                .add("commission", validCommission)
                .toString();

        assertEquals(expectedString, command.toString());

    }
}
