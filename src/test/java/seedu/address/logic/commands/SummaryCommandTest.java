package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Commission;
import seedu.address.model.person.Person;

public class SummaryCommandTest {

    private Model model;
    private Model expectedModel;
    private Model modelWithNoPersons;
    private Model expectedModelWithNoPersons;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        modelWithNoPersons = new ModelManager(new AddressBook(), new UserPrefs());
        expectedModelWithNoPersons = new ModelManager(modelWithNoPersons.getAddressBook(), new UserPrefs());
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

    @Test
    public void execute_summaryWithTypicalPersons_success() {
        // Arrange
        SummaryCommand summaryCommand = new SummaryCommand(); // Use correct command name

        // --- Calculate Expected Values Manually Based on TypicalPersons ---
        List<Person> personList = model.getFilteredPersonList();
        BigDecimal totalCommissionValue = BigDecimal.ZERO;
        Person highestCommissionPerson = null;
        Person lowestCommissionPerson = null;
        BigDecimal highestCommission = BigDecimal.valueOf(Double.MIN_VALUE);
        // Initialize lowestCommission with a very large value or the first person's commission
        BigDecimal lowestCommission = null;

        for (Person person : personList) {
            BigDecimal currentCommission = new BigDecimal(person.getCommission().value);
            totalCommissionValue = totalCommissionValue.add(currentCommission);

            if (currentCommission.compareTo(highestCommission) > 0) {
                highestCommission = currentCommission;
                highestCommissionPerson = person;
            }
            // Initialize lowest on first iteration or compare
            if (lowestCommission == null || currentCommission.compareTo(lowestCommission) < 0) {
                lowestCommission = currentCommission;
                lowestCommissionPerson = person;
            }
        }

        BigDecimal averageCommissionValue = BigDecimal.ZERO;
        if (!personList.isEmpty()) {
            averageCommissionValue = totalCommissionValue.divide(
                    new BigDecimal(personList.size()), 2, RoundingMode.HALF_UP);
        }
        // --- End Calculation ---


        // --- Construct Expected Feedback Message ---
        String expectedStatsMessage = "==== General Statistics ====\n"
                + String.format("Number of Contacts : %d\n", personList.size())
                + String.format("Total Commission   : %s\n", totalCommissionValue.toString()) // Use BigDecimal string
                + String.format("Highest Commission : %s\n",
                highestCommissionPerson != null ? highestCommissionPerson.getCommission().toString() : "N/A")
                + String.format("Lowest Commission  : %s\n",
                lowestCommissionPerson != null ? lowestCommissionPerson.getCommission().toString() : "N/A")
                + String.format("Average Commission : %s\n", averageCommissionValue.toString())
                + "============================\n";
        // --- End Message Construction ---

        // Act & Assert using assertCommandSuccess
        // The model state doesn't change, so expectedModel is the same as the initial model.
        assertCommandSuccess(summaryCommand, model, expectedStatsMessage, expectedModel);
    }

    @Test
    public void execute_summaryWithNoPersons_success() {
        // Arrange
        SummaryCommand summaryCommand = new SummaryCommand();

        // Expected values and message for an empty list
        int expectedNumContacts = 0;
        BigDecimal expectedTotalCommission = BigDecimal.ZERO;
        String expectedHighest = "N/A";
        String expectedLowest = "N/A";
        String expectedAverage = "0.00";

        String expectedStatsMessage = "==== General Statistics ====\n"
                + String.format("Number of Contacts : %d\n", expectedNumContacts)
                + String.format("Total Commission   : %s\n", expectedTotalCommission)
                + String.format("Highest Commission : %s\n", expectedHighest)
                + String.format("Lowest Commission  : %s\n", expectedLowest)
                + String.format("Average Commission : %s\n", expectedAverage)
                + "============================\n";

        assertCommandSuccess(summaryCommand, modelWithNoPersons, expectedStatsMessage, expectedModelWithNoPersons);
    }


}
