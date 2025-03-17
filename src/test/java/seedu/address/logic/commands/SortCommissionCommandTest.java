package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

public class SortCommissionCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_commission_success() {
        SortCommissionCommand scc = new SortCommissionCommand(false, true);
        assertCommandSuccess(scc, model, scc.messageSuccess, expectedModel);
    }

    @Test
    void execute_commissionNoSort_success() {
        List<Person> testList = List.of(
                ALICE,
                BENSON,
                CARL
        );
        SortCommissionCommand scc = new SortCommissionCommand(false, true);
        List<Person> sortedTestList = scc.getSortedList(testList);
        assertEquals(sortedTestList, testList);
    }

    @Test
    void execute_commissionAscendingSort_success() {
        List<Person> testList = List.of(
                ALICE,
                BENSON,
                CARL
        );
        SortCommissionCommand scc = new SortCommissionCommand(true, true);
        List<Person> sortedTestList = scc.getSortedList(testList);
        List<Person> expectedList = List.of(
                ALICE,
                CARL,
                BENSON
        );
        assertEquals(sortedTestList, expectedList);
    }

    @Test
    void execute_commissionDescendingSort_success() {
        List<Person> testList = List.of(
                ALICE,
                BENSON,
                CARL
        );
        SortCommissionCommand scc = new SortCommissionCommand(true, false);
        List<Person> sortedTestList = scc.getSortedList(testList);
        List<Person> expectedList = new java.util.ArrayList<>(List.of(
                ALICE,
                CARL,
                BENSON
        ));
        Collections.reverse(expectedList);
        assertEquals(sortedTestList, expectedList);
    }

    @Test
    public void equals() {
        SortCommissionCommand commissionFirstCommand = new SortCommissionCommand(true, true);
        SortCommissionCommand commissionSecondCommand = new SortCommissionCommand(false, true);
        SortCommissionCommand commissionThirdCommand = new SortCommissionCommand(true, false);

        assertTrue(commissionFirstCommand.equals(commissionFirstCommand));
        assertTrue(commissionSecondCommand.equals(commissionSecondCommand));

        assertFalse(commissionFirstCommand.equals(commissionSecondCommand));
        assertFalse(commissionFirstCommand.equals(commissionThirdCommand));

        assertFalse(commissionFirstCommand.equals(null));
    }
}
