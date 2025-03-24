package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.predicates.NameContainsKeywordsPredicate;

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
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(List.of("alice")));
        int initialSize = model.getFilteredPersonList().size();
        int expectedSize = model.getAddressBook().getPersonList().size();
        assertCommandSuccess(new IncludePersonCommand(validPredicate), model,
                String.format(Messages.MESSAGE_PERSONS_ADDED_OVERVIEW, expectedSize - initialSize), expectedModel);
    }

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new IncludePersonCommand(null));
    }

    @Test
    public void execute_personNotInAddressBook_showsOverview() {
        String expectedMessage = String.format(
                Messages.MESSAGE_PERSONS_ADDED_OVERVIEW, 0);
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

    @Test
    public void equals() {
        Predicate<Person> firstPredicate = person -> true;
        IncludePersonCommand firstCommand = new IncludePersonCommand(firstPredicate);
        IncludePersonCommand secondCommand = new IncludePersonCommand(firstPredicate);
        assertTrue(firstCommand.equals(firstCommand));
        assertEquals(firstCommand, secondCommand);
    }
}
