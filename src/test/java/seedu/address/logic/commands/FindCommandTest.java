package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.predicates.NameAndTagPredicate;

/**
 * Contains integration tests (interaction with the Model) for
 * {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        List<String> firstNameKeywords = Collections.singletonList("first");
        List<String> secondNameKeywords = Arrays.asList("first", "second");
        List<String> firstTagKeywords = Collections.singletonList("tag1");
        List<String> secondTagKeywords = Arrays.asList("tag1", "tag2");

        NameAndTagPredicate firstPredicate = new NameAndTagPredicate(firstNameKeywords, firstTagKeywords);
        NameAndTagPredicate secondPredicate = new NameAndTagPredicate(secondNameKeywords, firstTagKeywords);
        NameAndTagPredicate thirdPredicate = new NameAndTagPredicate(firstNameKeywords, secondTagKeywords);
        NameAndTagPredicate fourthPredicate = new NameAndTagPredicate(secondNameKeywords, secondTagKeywords);
        NameAndTagPredicate fifthPredicate = new NameAndTagPredicate(firstNameKeywords, Collections.emptyList());
        NameAndTagPredicate sixthPredicate = new NameAndTagPredicate(Collections.emptyList(), firstTagKeywords);

        FindCommand findFirstCommand = new FindCommand(firstPredicate);
        FindCommand findSecondCommand = new FindCommand(secondPredicate);
        FindCommand findThirdCommand = new FindCommand(thirdPredicate);
        FindCommand findFourthCommand = new FindCommand(fourthPredicate);
        FindCommand findFifthCommand = new FindCommand(fifthPredicate);
        FindCommand findSixthCommand = new FindCommand(sixthPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same names -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // same tags -> returns true
        FindCommand findFourthCommandCopy = new FindCommand(fourthPredicate);
        assertTrue(findFourthCommand.equals(findFourthCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different predicates -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
        assertFalse(findFirstCommand.equals(findThirdCommand));
        assertFalse(findFirstCommand.equals(findFourthCommand));
        assertFalse(findFirstCommand.equals(findFifthCommand));
        assertFalse(findFirstCommand.equals(findSixthCommand));
    }

    @Test
    public void execute_zeroKeyword_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        NameAndTagPredicate predicate = preparePredicate(" ");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleNameKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        NameAndTagPredicate predicate = preparePredicate(" n/Kurz n/Elle n/Kunz");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleTagKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        NameAndTagPredicate predicate = preparePredicate(" t/friends t/owesMoney");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, DANIEL), model.getFilteredPersonList());
    }

    @Test
    public void execute_nameAndTagKeywords_onePersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        NameAndTagPredicate predicate = preparePredicate("n/Benson t/friends");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BENSON), model.getFilteredPersonList());
    }

    @Test
    public void execute_nameAndTagKeywordsNoMatch_noPersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        NameAndTagPredicate predicate = preparePredicate("n/Alice t/owesMoney");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        NameAndTagPredicate predicate = preparePredicate("n/Alice t/friends");
        FindCommand findCommand = new FindCommand(predicate);
        String expected = FindCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code NameAndTagPredicate}.
     * Uses ArgumentTokenizer to extract name and tag keywords.
     */
    private NameAndTagPredicate preparePredicate(String userInput) {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(" " + userInput.trim(), PREFIX_NAME, PREFIX_TAG);
        List<String> nameKeywords = argMultimap.getAllValues(PREFIX_NAME);
        List<String> tagKeywords = argMultimap.getAllValues(PREFIX_TAG);

        return new NameAndTagPredicate(nameKeywords, tagKeywords);
    }
}
