package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommissionCommand;

public class AddCommissionCommandParserTest {

    private AddCommissionCommandParser parser = new AddCommissionCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddCommissionCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " 0 c/100", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddCommissionCommand.MESSAGE_USAGE));
    }
}
