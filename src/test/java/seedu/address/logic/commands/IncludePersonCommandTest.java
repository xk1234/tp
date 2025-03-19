package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

public class IncludePersonCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_commandSuccess() {
        Predicate<Person> validPredicate = person -> true;
        assertCommandSuccess(new IncludePersonCommand(validPredicate),
                model, IncludePersonCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new IncludePersonCommand(null));
    }

    @Test
    public void execute_personNotInAddressBook_showsOverview() {
        String expectedMessage =  String.format(
                Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size());
        Predicate<Person> predicate = person -> person.getName().fullName
                .equals("NonExistentNameInTypicalAddressBook");
        IncludePersonCommand includePersonCommand = new IncludePersonCommand(predicate);
        assertCommandSuccess(includePersonCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        Predicate<Person> dummyPredicate = person -> true;
        IncludePersonCommand includePersonCommand = new IncludePersonCommand(dummyPredicate);
        assertThrows(NullPointerException.class, () -> includePersonCommand.execute(null));
    }
}
