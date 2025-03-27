package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.JAMES;
import static seedu.address.testutil.TypicalPersons.KEITH;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Commission;
import seedu.address.model.person.Person;

public class TotalCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_totalIsNotFiltered_showsSameValue() {
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    void calculateTotalCommission_correctlySumsValues() {
        List<Person> lastShownList = List.of(
                ALICE,
                BENSON
        );
        TotalCommand totalCommand = new TotalCommand();
        Commission totalCommission = new Commission("0");
        try {
            totalCommission = totalCommand.getTotal(lastShownList);
        } catch (CommandException e) {
            e.printStackTrace();
        }

        Integer total = Integer.parseInt(ALICE.getCommission().value) + Integer.parseInt(BENSON.getCommission().value);
        assertEquals(new Commission(total.toString()), totalCommission);
    }

    @Test
    void execute_returnsCorrectValue() {
        AddressBook ab = new AddressBook();
        ab.addPerson(ALICE);
        ab.addPerson(BENSON);
        model = new ModelManager(ab, new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        Integer total = Integer.parseInt(ALICE.getCommission().value) + Integer.parseInt(BENSON.getCommission().value);
        String expectedMessage = String.format(TotalCommand.MESSAGE_TOTAL_SUCCESS + total);

        TotalCommand totalCommand = new TotalCommand();
        assertCommandSuccess(totalCommand, model, expectedMessage, expectedModel);
    }

    @Test
    void getTotal_throwsOverflowException() {
        List<Person> shownList = List.of(JAMES, KEITH);
        TotalCommand totalCommand = new TotalCommand();
        assertThrows(CommandException.class, () -> totalCommand.getTotal(shownList));
    }

    @Test
    void execute_throwsOverflowException() {
        AddressBook ab = new AddressBook();
        ab.addPerson(JAMES);
        ab.addPerson(KEITH);
        model = new ModelManager(ab, new UserPrefs());
        TotalCommand totalCommand = new TotalCommand();
        assertThrows(CommandException.class, () -> totalCommand.execute(model));
    }
}
