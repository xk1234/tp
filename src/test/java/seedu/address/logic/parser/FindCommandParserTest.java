package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.predicates.NameAndTagPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // empty
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));

        // no prefix
        assertParseFailure(parser, "Alice Bob",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));

        // empty prefix values
        assertParseFailure(parser, " " + PREFIX_NAME + " " + PREFIX_TAG + " ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));

        // whitespace-only prefix values
        assertParseFailure(parser, " " + PREFIX_NAME + "    " + PREFIX_TAG + "   ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validNamePrefixArgs_returnsFindCommand() {
        // one name keyword
        FindCommand expectedFindCommand = new FindCommand(
                new NameAndTagPredicate(Collections.singletonList("Alice"), Collections.emptyList()));
        assertParseSuccess(parser, " " + PREFIX_NAME + "Alice", expectedFindCommand);

        // multiple name keywords
        expectedFindCommand = new FindCommand(
                new NameAndTagPredicate(Arrays.asList("Alice", "Bob"), Collections.emptyList()));
        assertParseSuccess(parser, " " + PREFIX_NAME + "Alice " + PREFIX_NAME + "Bob", expectedFindCommand);

        // keywords with leading, trailing and multiple whitespace between
        assertParseSuccess(parser, "   " + PREFIX_NAME + "Alice        Bob  ",
                expectedFindCommand);
    }

    @Test
    public void parse_validTagPrefixArgs_returnsFindCommand() {
        // one tag keyword
        FindCommand expectedFindCommand = new FindCommand(
                new NameAndTagPredicate(Collections.emptyList(), Collections.singletonList("friends")));
        assertParseSuccess(parser, " " + PREFIX_TAG + "friends", expectedFindCommand);

        // multiple tag keywords
        expectedFindCommand = new FindCommand(
                new NameAndTagPredicate(Collections.emptyList(),
                        Arrays.asList("friends", "colleagues")));
        assertParseSuccess(parser, " " + PREFIX_TAG + "friends " + PREFIX_TAG + "colleagues",
                expectedFindCommand);

        // keywords with leading, trailing and multiple whitespace
        assertParseSuccess(parser, "   " + PREFIX_TAG + "   friends      colleagues   ",
                expectedFindCommand);
    }

    @Test
    public void parse_validMixedPrefixArgs_returnsFindCommand() {
        // both name and tag keywords
        FindCommand expectedFindCommand = new FindCommand(new NameAndTagPredicate(
                Collections.singletonList("Alice"), Collections.singletonList("friends")));
        assertParseSuccess(parser, " " + PREFIX_NAME + "Alice " + PREFIX_TAG + "friends", expectedFindCommand);

        // multiple name and tag keywords
        expectedFindCommand = new FindCommand(new NameAndTagPredicate(Arrays.asList("Alice", "Bob"),
                Arrays.asList("friends", "colleagues")));
        assertParseSuccess(parser, " " + PREFIX_NAME + "Alice Bob " + PREFIX_TAG + "friends colleagues",
                        expectedFindCommand);

        // mixed order of prefixes with extra whitespace
        assertParseSuccess(parser, "   " + PREFIX_TAG + "   friends   " + PREFIX_NAME + "   Alice   "
                + PREFIX_TAG + "   colleagues   " + PREFIX_NAME + "   Bob   ", expectedFindCommand);
    }
}
