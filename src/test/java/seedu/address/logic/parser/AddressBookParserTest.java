package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RemoveTagCommand;
import seedu.address.logic.commands.TagCommand;
import seedu.address.logic.commands.TotalCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;
import seedu.address.model.person.predicates.NameAndTagPredicate;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> nameKeywords = Arrays.asList("foo", "bar", "baz");
        List<String> tagKeywords = Arrays.asList("friend", "colleague");

        // Construct the command string
        String commandString = FindCommand.COMMAND_WORD + " " + PREFIX_NAME + String.join(" ", nameKeywords) + " "
                + PREFIX_TAG + String.join(" ", tagKeywords);

        FindCommand command = (FindCommand) parser.parseCommand(commandString);

        assertEquals(new FindCommand(new NameAndTagPredicate(nameKeywords, tagKeywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_tag() throws Exception {
        // Single tag
        Set<Tag> tagSet = new HashSet<>();
        tagSet.add(new Tag(VALID_TAG_FRIEND));
        String tagCommand = TagCommand.COMMAND_WORD + " " + VALID_TAG_FRIEND;
        TagCommand command = (TagCommand) parser.parseCommand(tagCommand);
        assertEquals(new TagCommand(tagSet), command);

        // Multiple tags
        Set<Tag> multipleTagSet = new HashSet<>();
        multipleTagSet.add(new Tag(VALID_TAG_FRIEND));
        multipleTagSet.add(new Tag(VALID_TAG_HUSBAND));
        String multipleTagCommand = TagCommand.COMMAND_WORD + " " + VALID_TAG_FRIEND + " " + VALID_TAG_HUSBAND;
        TagCommand multipleCommand = (TagCommand) parser.parseCommand(multipleTagCommand);
        assertEquals(new TagCommand(multipleTagSet), multipleCommand);
    }

    @Test
    public void parseCommand_removeTag() throws Exception {
        // Single tag
        Set<Tag> singleTagSet = new HashSet<>();
        singleTagSet.add(new Tag(VALID_TAG_FRIEND));
        RemoveTagCommand command = (RemoveTagCommand) parser.parseCommand(
                RemoveTagCommand.COMMAND_WORD + " " + VALID_TAG_FRIEND);
        assertEquals(new RemoveTagCommand(singleTagSet), command);

        // Multiple tags
        Set<Tag> multipleTagSet = new HashSet<>();
        multipleTagSet.add(new Tag(VALID_TAG_FRIEND));
        multipleTagSet.add(new Tag(VALID_TAG_HUSBAND));
        command = (RemoveTagCommand) parser.parseCommand(
                RemoveTagCommand.COMMAND_WORD + " " + VALID_TAG_FRIEND + " " + VALID_TAG_HUSBAND);
        assertEquals(new RemoveTagCommand(multipleTagSet), command);
    }

    @Test
    public void parseCommand_total() throws Exception {
        assertTrue(parser.parseCommand(TotalCommand.COMMAND_WORD) instanceof TotalCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
