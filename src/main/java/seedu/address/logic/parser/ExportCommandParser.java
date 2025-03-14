package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTRIBUTE;

import java.util.EnumSet;
import java.util.List;

import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Attribute;

/**
 * Parses input arguments and creates a new ExportCommand object
 */
public class ExportCommandParser implements Parser<ExportCommand> {
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
            List<Attribute> attributes = argMultimap.getAllValues(PREFIX_ATTRIBUTE).stream()
                    .map(String::toUpperCase)
                    .map(Attribute::valueOf)
                    .toList();

            // Check if there are duplicates
            if (!attributes.isEmpty()) {
                // copyOf could throw if we don't make sure attributes is non-empty
                EnumSet<Attribute> uniqueValues = EnumSet.copyOf(attributes);
                if (uniqueValues.size() != attributes.size()) {
                    throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));
                }
            }
            return new ExportCommand(attributes);
        } catch (IllegalArgumentException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));
        }
    }
}
