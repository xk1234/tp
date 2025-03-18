package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_EMPTY_LIST;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * RemoveTagCommand.
 */
public class RemoveTagCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(new AddressBook(), new UserPrefs());
    }

    @Test
    public void constructor_nullTags_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new RemoveTagCommand(null));
    }

    @Test
    public void execute_emptyList_throwsCommandException() {
        Set<Tag> tagsToRemove = new HashSet<>();
        tagsToRemove.add(new Tag(VALID_TAG_FRIEND));
        RemoveTagCommand removeTagCommand = new RemoveTagCommand(tagsToRemove);

        assertCommandFailure(removeTagCommand, model, MESSAGE_EMPTY_LIST);
    }

    @Test
    public void execute_validTagsSubsetOfPersons_success() throws Exception {
        Tag friendTag = new Tag(VALID_TAG_FRIEND);
        Set<Tag> tagsToRemove = new HashSet<>();
        tagsToRemove.add(friendTag);

        Person firstPerson = new PersonBuilder(AMY).withTags(VALID_TAG_FRIEND).build();
        Person secondPerson = new PersonBuilder(BOB).withTags(VALID_TAG_HUSBAND).build();

        model.addPerson(firstPerson);
        model.addPerson(secondPerson);

        Model expectedTestModel = new ModelManager(new AddressBook(), new UserPrefs());
        expectedTestModel.addPerson(new PersonBuilder(firstPerson).withTags().build());
        expectedTestModel.addPerson(new PersonBuilder(secondPerson).withTags(VALID_TAG_HUSBAND).build());

        RemoveTagCommand removeTagCommand = new RemoveTagCommand(tagsToRemove);

        assertCommandSuccess(removeTagCommand, model, String.format(RemoveTagCommand.MESSAGE_REMOVE_TAG_SUCCESS, 1),
                expectedTestModel);
    }

    @Test
    public void execute_validTagsMultiplePersons_success() throws Exception {
        Tag friendTag = new Tag(VALID_TAG_FRIEND);
        Set<Tag> tagsToRemove = new HashSet<>();
        tagsToRemove.add(friendTag);

        Person firstPerson = new PersonBuilder(AMY).withTags(VALID_TAG_FRIEND).build();
        Person secondPerson = new PersonBuilder(BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND).build();

        model.addPerson(firstPerson);
        model.addPerson(secondPerson);

        Model expectedTestModel = new ModelManager(new AddressBook(), new UserPrefs());
        expectedTestModel.addPerson(new PersonBuilder(firstPerson).withTags().build());
        expectedTestModel.addPerson(new PersonBuilder(secondPerson).withTags(VALID_TAG_HUSBAND).build());

        RemoveTagCommand removeTagCommand = new RemoveTagCommand(tagsToRemove);

        assertCommandSuccess(removeTagCommand, model, String.format(RemoveTagCommand.MESSAGE_REMOVE_TAG_SUCCESS, 2),
                expectedTestModel);
    }

    @Test
    public void execute_multipleTagsRemoved_success() throws Exception {
        Tag friendTag = new Tag(VALID_TAG_FRIEND);
        Tag husbandTag = new Tag(VALID_TAG_HUSBAND);
        Set<Tag> tagsToRemove = new HashSet<>();
        tagsToRemove.add(friendTag);
        tagsToRemove.add(husbandTag);

        Person person = new PersonBuilder(BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND).build();

        model.addPerson(person);

        Model expectedTestModel = new ModelManager(new AddressBook(), new UserPrefs());
        expectedTestModel.addPerson(new PersonBuilder(person).withTags().build());

        RemoveTagCommand removeTagCommand = new RemoveTagCommand(tagsToRemove);

        assertCommandSuccess(removeTagCommand, model, String.format(RemoveTagCommand.MESSAGE_REMOVE_TAG_SUCCESS, 1),
                expectedTestModel);
    }

    @Test
    public void equals() {
        Set<Tag> firstTagSet = new HashSet<>(Arrays.asList(new Tag(VALID_TAG_FRIEND)));
        Set<Tag> secondTagSet = new HashSet<>(Arrays.asList(new Tag(VALID_TAG_HUSBAND)));

        RemoveTagCommand removeFirstCommand = new RemoveTagCommand(firstTagSet);
        RemoveTagCommand removeSecondCommand = new RemoveTagCommand(secondTagSet);

        // same object -> returns true
        assertTrue(removeFirstCommand.equals(removeFirstCommand));

        // same values -> returns true
        RemoveTagCommand removeFirstCommandCopy = new RemoveTagCommand(firstTagSet);
        assertTrue(removeFirstCommand.equals(removeFirstCommandCopy));

        // different types -> returns false
        assertFalse(removeFirstCommand.equals(1));

        // null -> returns false
        assertFalse(removeFirstCommand.equals(null));

        // different tags -> returns false
        assertFalse(removeFirstCommand.equals(removeSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Set<Tag> tagSet = new HashSet<>(Arrays.asList(new Tag(VALID_TAG_FRIEND)));
        RemoveTagCommand removeTagCommand = new RemoveTagCommand(tagSet);
        String expected = RemoveTagCommand.class.getCanonicalName()
                + "{tagsToRemove=" + tagSet + "}";
        assertEquals(expected, removeTagCommand.toString());
    }
}
