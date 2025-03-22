package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.IncludePersonCommand;
import seedu.address.model.person.Person;
import seedu.address.model.person.predicates.NameContainsKeywordsPredicate;

public class IncludePersonCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, IncludePersonCommand.MESSAGE_USAGE);

    private IncludePersonCommandParser parser = new IncludePersonCommandParser();

    @Test
    public void parse_noNameArgs_throwsParseException() {
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, " n/", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_validArgs_returnsIncludeCommand() {
        String userInput = " " + PREFIX_NAME + VALID_NAME_AMY;
        List<String> keyword = Collections.singletonList(VALID_NAME_AMY);
        Predicate<Person> namePredicate = new NameContainsKeywordsPredicate(keyword);
        IncludePersonCommand expectedCommand = new IncludePersonCommand(namePredicate);
        assertParseSuccess(parser, userInput, expectedCommand);
    }


}
