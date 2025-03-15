package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ExportCommand;
import seedu.address.model.person.Attribute;

public class ExportCommandParserTest {
    private final ExportCommandParser parser = new ExportCommandParser();

    @Test
    public void parse_validCaseInsensitiveAttribute_success() {
        List<Attribute> expectedAttributes = List.of(Attribute.PHONE);
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + " a/pHoNe",
                new ExportCommand(expectedAttributes));
    }

    @Test
    public void parse_validMultipleAttributes_success() {
        List<Attribute> expectedAttributes = List.of(Attribute.NAME, Attribute.EMAIL);
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + " a/NAME a/EMAIL",
                new ExportCommand(expectedAttributes));
    }

    @Test
    public void parse_repeatedAttribute_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE);
        assertParseFailure(parser, PREAMBLE_WHITESPACE + " a/name a/name", expectedMessage);
    }

    @Test
    public void parse_invalidAttribute_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE);
        assertParseFailure(parser, PREAMBLE_WHITESPACE + " a/invalid", expectedMessage);
    }

    @Test
    public void parse_noAttributeProvided_success() {
        List<Attribute> expectedAttributes = List.of(Attribute.values());
        assertParseSuccess(parser, "", new ExportCommand(expectedAttributes));
    }
}
