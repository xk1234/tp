package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_HUBBY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.RemoveTagCommand;
import seedu.address.model.tag.Tag;

public class RemoveTagCommandParserTest {
    private RemoveTagCommandParser parser = new RemoveTagCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsRemoveTagCommand() throws Exception {
        // Single tag
        Tag friendTag = new Tag(VALID_TAG_FRIEND);
        Set<Tag> singleTagSet = new HashSet<>();
        singleTagSet.add(friendTag);
        RemoveTagCommand expectedSingleTagCommand = new RemoveTagCommand(singleTagSet);
        assertParseSuccess(parser, VALID_TAG_FRIEND, expectedSingleTagCommand);

        // Multiple tags
        Tag husbandTag = new Tag(VALID_TAG_HUSBAND);
        Set<Tag> multipleTagSet = new HashSet<>();
        multipleTagSet.add(friendTag);
        multipleTagSet.add(husbandTag);
        RemoveTagCommand expectedMultipleTagCommand = new RemoveTagCommand(multipleTagSet);
        assertParseSuccess(parser, VALID_TAG_FRIEND + " " + VALID_TAG_HUSBAND, expectedMultipleTagCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Invalid tag (contains non-alphanumeric characters)
        assertParseFailure(parser, INVALID_TAG_HUBBY, Tag.MESSAGE_CONSTRAINTS);

        // Mix of valid and invalid tags
        assertParseFailure(parser, VALID_TAG_FRIEND + " " + INVALID_TAG_HUBBY, Tag.MESSAGE_CONSTRAINTS);
    }
}
