package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.SortCommissionCommand.MESSAGE_INVALID_SORT_DIRECTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.SortCommissionCommand;

public class SortCommissionCommandParserTest {

    private SortCommissionCommandParser parser = new SortCommissionCommandParser();

    @Test
    public void parse_emptyArg_returnsSortCommissionCommandWithoutSort() {
        assertParseSuccess(parser, "", new SortCommissionCommand(false, true));
    }

    @Test
    public void parse_sortAsc_returnsSortCommissionCommandAscending() {
        assertParseSuccess(parser, " s/asc", new SortCommissionCommand(true, true));
    }

    @Test
    public void parse_sortDesc_returnsSortCommissionCommandDescending() {
        assertParseSuccess(parser, " s/desc", new SortCommissionCommand(true, false));
    }

    @Test
    public void parse_invalidSortDirection_throwsParseException() {
        assertParseFailure(parser, " s/invalid", MESSAGE_INVALID_SORT_DIRECTION);
    }

    @Test
    public void parse_preamble_throwsParseException() {
        assertParseFailure(parser, " preamble s/asc",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommissionCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_multipleSortPrefixes_throwsParseException() {
        assertParseFailure(parser, " s/asc s/desc", Messages.MESSAGE_DUPLICATE_FIELDS + PREFIX_SORT);
    }
}
