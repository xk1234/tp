package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

        Commission totalCommission = lastShownList.stream()
                .map(Person::getCommission)
                .reduce(new Commission("0"), Commission::addValue);

        assertEquals(new Commission("200"), totalCommission);
    }

    @Test
    public void execute_commandSuccess() {
        assertCommandSuccess(new TotalCommand(),
                model, TotalCommand.MESSAGE_TOTAL_SUCCESS, expectedModel);
    }
}
