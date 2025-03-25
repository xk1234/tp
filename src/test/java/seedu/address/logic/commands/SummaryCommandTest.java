package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

public class SummaryCommandTest {

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
        SummaryCommand summaryCommand = new SummaryCommand();
        Commission totalCommission = summaryCommand.getTotal(lastShownList);

        Integer total = Integer.parseInt(ALICE.getCommission().value) + Integer.parseInt(BENSON.getCommission().value);
        assertEquals(new Commission(total.toString()), totalCommission);
    }

    @Test
    void getNumContacts_returnsCorrectCount() {
        List<Person> lastShownList = List.of(ALICE, BENSON);
        SummaryCommand summaryCommand = new SummaryCommand();
        int numContacts = summaryCommand.getNumContacts(lastShownList);
        assertEquals(2, numContacts);
    }

    @Test
    void getHighest_returnsPersonWithHighestCommission() {
        List<Person> lastShownList = List.of(ALICE, BENSON);
        SummaryCommand summaryCommand = new SummaryCommand();
        Person highest = summaryCommand.getHighest(lastShownList);
        assertNotNull(highest);
        assertEquals(BENSON, highest);
    }

    @Test
    void getLowest_returnsPersonWithLowestCommission() {
        List<Person> lastShownList = List.of(ALICE, BENSON);
        SummaryCommand summaryCommand = new SummaryCommand();
        Person lowest = summaryCommand.getLowest(lastShownList);
        // ALICE should have the lowest commission ("100" < "200")
        assertNotNull(lowest);
        assertEquals(ALICE, lowest);
    }

    @Test
    void calculateAverageCommission_correctlyAveragesValues() {
        List<Person> lastShownList = List.of(ALICE, BENSON);
        SummaryCommand summaryCommand = new SummaryCommand();
        double averageCommission = summaryCommand.getAverage(lastShownList);

        double expectedAverage = (Integer.parseInt(ALICE.getCommission().value)
                + Integer.parseInt(BENSON.getCommission().value)) / 2.0;
        // Allow a small delta for floating point comparison
        assertEquals(expectedAverage, averageCommission, 0.0001);
    }


}
