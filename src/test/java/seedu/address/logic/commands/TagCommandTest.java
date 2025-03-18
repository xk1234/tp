package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.TagCommand.MESSAGE_TAG_PERSONS_SUCCESS;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

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
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validTags_success() throws Exception {
        Set<Tag> tagsToAdd = new HashSet<>();
        tagsToAdd.add(new Tag(VALID_TAG_HUSBAND));
        TagCommand tagCommand = new TagCommand(tagsToAdd);

        List<Person> lastShownList = model.getFilteredPersonList();
        lastShownList.stream()
                .forEach(personToEdit -> {
                    Set<Tag> updatedTags = new HashSet<>(personToEdit.getTags());
                    updatedTags.add(new Tag(VALID_TAG_HUSBAND));
                    Person editedPerson = new PersonBuilder(personToEdit).withTags(
                            updatedTags.stream()
                                    .map(tag -> tag.tagName)
                                    .toArray(String[]::new))
                            .build();
                    expectedModel.setPerson(personToEdit, editedPerson);
                });

        String expectedMessage = String.format(MESSAGE_TAG_PERSONS_SUCCESS, lastShownList.size());
        assertCommandSuccess(tagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_emptyList_throwsCommandException() {
        model.updateFilteredPersonList(p -> false);
        Set<Tag> tagsToAdd = Collections.singleton(new Tag(VALID_TAG_FRIEND));
        TagCommand tagCommand = new TagCommand(tagsToAdd);

        assertCommandFailure(tagCommand, model, TagCommand.MESSAGE_EMPTY_LIST);
    }

    @Test
    public void execute_duplicateTags_stillSuccess() throws Exception {
        // Create a tag that some people already have
        Set<Tag> tagsToAdd = new HashSet<>();
        tagsToAdd.add(new Tag(VALID_TAG_FRIEND));
        TagCommand tagCommand = new TagCommand(tagsToAdd);

        List<Person> lastShownList = expectedModel.getFilteredPersonList();
        lastShownList.stream()
                .forEach(personToEdit -> {
                    Set<Tag> updatedTags = new HashSet<>(personToEdit.getTags());
                    updatedTags.add(new Tag(VALID_TAG_FRIEND));
                    Person editedPerson = new PersonBuilder(personToEdit).withTags(
                            updatedTags.stream()
                                    .map(tag -> tag.tagName)
                                    .toArray(String[]::new))
                            .build();
                    expectedModel.setPerson(personToEdit, editedPerson);
                });

        String expectedMessage = String.format(MESSAGE_TAG_PERSONS_SUCCESS, lastShownList.size());
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
