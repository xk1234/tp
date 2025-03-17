package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.TagCommand;
import seedu.address.model.tag.Tag;

public class TagCommandParserTest {
    private static final String INVALID_TAG = "hubby*"; // '*' not allowed in tags
    private TagCommandParser parser = new TagCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsTagCommand() {
        // Single tag
        Set<Tag> expectedTagSet = new HashSet<>();
        expectedTagSet.add(new Tag(VALID_TAG_FRIEND));
        TagCommand expectedCommand = new TagCommand(expectedTagSet);
        assertParseSuccess(parser, VALID_TAG_FRIEND, expectedCommand);

        // Multiple tags
        Set<Tag> expectedMultipleTagSet = new HashSet<>();
        expectedMultipleTagSet.add(new Tag(VALID_TAG_FRIEND));
        expectedMultipleTagSet.add(new Tag(VALID_TAG_HUSBAND));
        TagCommand expectedMultipleCommand = new TagCommand(expectedMultipleTagSet);
        assertParseSuccess(parser, VALID_TAG_FRIEND + " " + VALID_TAG_HUSBAND, expectedMultipleCommand);
    }

    @Test
    public void parse_invalidTagFormat_throwsParseException() {
        // Tag with invalid characters
        assertParseFailure(parser, INVALID_TAG, Tag.MESSAGE_CONSTRAINTS);
    }
}
