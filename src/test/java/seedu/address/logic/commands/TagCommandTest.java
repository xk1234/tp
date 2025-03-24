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
import static seedu.address.logic.commands.TagCommand.MESSAGE_TAG_PERSONS_SUCCESS;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.util.Collections;
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
 * Contains integration tests (interaction with the Model) for
 * {@code TagCommand}.
 */
public class TagCommandTest {
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(new AddressBook(), new UserPrefs());
        expectedModel = new ModelManager(new AddressBook(), new UserPrefs());
    }

    @Test
    public void execute_emptyList_throwsCommandException() {
        model.updateFilteredPersonList(p -> false);
        Set<Tag> tagsToAdd = Collections.singleton(new Tag(VALID_TAG_FRIEND));
        TagCommand tagCommand = new TagCommand(tagsToAdd);

        assertCommandFailure(tagCommand, model, MESSAGE_EMPTY_LIST);
    }

    @Test
    public void constructor_nullTags_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TagCommand(null));
    }

    @Test
    public void execute_validTagsSubsetOfPersons_success() throws Exception {
        Set<Tag> tagsToAdd = new HashSet<>();
        tagsToAdd.add(new Tag(VALID_TAG_HUSBAND));
        TagCommand tagCommand = new TagCommand(tagsToAdd);

        Person firstPerson = new PersonBuilder(AMY).withTags(VALID_TAG_FRIEND).build();
        Person secondPerson = new PersonBuilder(BOB).withTags(VALID_TAG_HUSBAND).build();

        model.addPerson(firstPerson);
        model.addPerson(secondPerson);

        expectedModel.addPerson(new PersonBuilder(firstPerson).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND).build());
        expectedModel.addPerson(secondPerson);

        String expectedMessage = String.format(MESSAGE_TAG_PERSONS_SUCCESS, 1);
        assertCommandSuccess(tagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validTagsMultiplePersons_success() throws Exception {
        Set<Tag> tagsToAdd = new HashSet<>();
        tagsToAdd.add(new Tag(VALID_TAG_FRIEND));
        TagCommand tagCommand = new TagCommand(tagsToAdd);

        Person firstPerson = new PersonBuilder(AMY).withTags().build();
        Person secondPerson = new PersonBuilder(BOB).withTags(VALID_TAG_HUSBAND).build();

        model.addPerson(firstPerson);
        model.addPerson(secondPerson);

        expectedModel.addPerson(new PersonBuilder(firstPerson).withTags(VALID_TAG_FRIEND).build());
        expectedModel.addPerson(new PersonBuilder(secondPerson).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build());

        String expectedMessage = String.format(MESSAGE_TAG_PERSONS_SUCCESS, 2);
        assertCommandSuccess(tagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    void execute_multipleTagsAdded_success() throws Exception {
        Set<Tag> tagsToAdd = new HashSet<>();
        tagsToAdd.add(new Tag(VALID_TAG_FRIEND));
        tagsToAdd.add(new Tag(VALID_TAG_HUSBAND));
        TagCommand tagCommand = new TagCommand(tagsToAdd);

        Person firstPerson = new PersonBuilder(AMY).withTags().build();

        model.addPerson(firstPerson);

        expectedModel.addPerson(new PersonBuilder(firstPerson).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND).build());

        String expectedMessage = String.format(MESSAGE_TAG_PERSONS_SUCCESS, 1);
        assertCommandSuccess(tagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        Set<Tag> firstTags = Collections.singleton(new Tag(VALID_TAG_FRIEND));
        Set<Tag> secondTags = Collections.singleton(new Tag(VALID_TAG_HUSBAND));

        TagCommand tagFirstCommand = new TagCommand(firstTags);
        TagCommand tagSecondCommand = new TagCommand(secondTags);

        // same object -> returns true
        assertTrue(tagFirstCommand.equals(tagFirstCommand));

        // same values -> returns true
        TagCommand tagFirstCommandCopy = new TagCommand(firstTags);
        assertTrue(tagFirstCommand.equals(tagFirstCommandCopy));

        // different types -> returns false
        assertFalse(tagFirstCommand.equals(1));

        // null -> returns false
        assertFalse(tagFirstCommand.equals(null));

        // different tag -> returns false
        assertFalse(tagFirstCommand.equals(tagSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Set<Tag> tags = Collections.singleton(new Tag(VALID_TAG_FRIEND));
        TagCommand tagCommand = new TagCommand(tags);
        String expected = TagCommand.class.getCanonicalName() + "{tagsToAdd=" + tags + "}";
        assertEquals(expected, tagCommand.toString());
    }
}
