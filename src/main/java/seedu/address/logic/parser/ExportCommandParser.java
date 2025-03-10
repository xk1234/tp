package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTRIBUTE;

import java.util.List;

import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ExportCommand object
 */
public class ExportCommandParser implements Parser<ExportCommand> {
    public static final String MESSAGE_INVALID_ATTRIBUTE = "Invalid attribute";

    /**
     * Parses the given {@code String} of arguments in the context of the ExportCommand
     * and returns an ExportCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ExportCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ATTRIBUTE);
        try {
            List<ExportCommand.Attribute> attributes = argMultimap.getAllValues(PREFIX_ATTRIBUTE).stream()
                    .map(ExportCommand.Attribute::valueOf).toList();
            return new ExportCommand(attributes);
        } catch (IllegalArgumentException e) {
            throw new ParseException(MESSAGE_INVALID_ATTRIBUTE, e);
        }
    }
}
