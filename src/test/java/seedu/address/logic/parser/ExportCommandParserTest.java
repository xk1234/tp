package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTRIBUTE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.model.person.Attribute.MESSAGE_CONSTRAINTS_NO_DUPLICATES;
import static seedu.address.model.person.Attribute.MESSAGE_CONSTRAINTS_VALID_ATTRIBUTES;

import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ExportCommand;
import seedu.address.model.CsvFileName;
import seedu.address.model.person.Attribute;

public class ExportCommandParserTest {
    private final ExportCommandParser parser = new ExportCommandParser();
    private final String goodCsvFileName = "export.csv";

    @Test
    public void parse_validCaseInsensitiveAttribute_success() {
        List<Attribute> expectedAttributes = List.of(Attribute.PHONE);
        assertParseSuccess(parser, PREAMBLE_WHITESPACE
                        + " " + goodCsvFileName
                        + " " + PREFIX_ATTRIBUTE + "pHoNe",
                new ExportCommand(expectedAttributes, Path.of(goodCsvFileName)));
    }

    @Test
    public void parse_validMultipleAttributes_success() {
        List<Attribute> expectedAttributes = List.of(Attribute.NAME, Attribute.EMAIL);
        assertParseSuccess(parser, PREAMBLE_WHITESPACE
                        + " " + goodCsvFileName
                        + " " + PREFIX_ATTRIBUTE + "NAME"
                        + " " + PREFIX_ATTRIBUTE + "EMAIL",
                new ExportCommand(expectedAttributes, Path.of(goodCsvFileName)));
    }

    @Test
    public void parse_repeatedAttribute_failure() {
        assertParseFailure(parser, PREAMBLE_WHITESPACE
                + " " + goodCsvFileName
                + " " + PREFIX_ATTRIBUTE + "name"
                + " " + PREFIX_ATTRIBUTE + "name", MESSAGE_CONSTRAINTS_NO_DUPLICATES);
    }

    @Test
    public void parse_invalidAttribute_failure() {
        assertParseFailure(parser, PREAMBLE_WHITESPACE
                + " " + goodCsvFileName
                + " " + PREFIX_ATTRIBUTE + "invalid", MESSAGE_CONSTRAINTS_VALID_ATTRIBUTES);
    }

    @Test
    public void parse_noAttributeProvided_success() {
        List<Attribute> expectedAttributes = List.of(Attribute.values());
        assertParseSuccess(parser, PREAMBLE_WHITESPACE
                + " " + goodCsvFileName, new ExportCommand(expectedAttributes, Path.of(goodCsvFileName)));
    }

    @Test
    public void parse_noFileNameProvided_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "", expectedMessage);
    }

    @Test
    public void parse_invalidPrefixProvided_failure() {
        String badCsvFileName = "%%.csv";
        assertParseFailure(parser, PREAMBLE_WHITESPACE
                + " " + badCsvFileName, CsvFileName.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidSuffixProvided_failure() {
        String badCsvFileName = "export";
        assertParseFailure(parser, PREAMBLE_WHITESPACE
                + " " + badCsvFileName, CsvFileName.MESSAGE_CONSTRAINTS);
    }
}
