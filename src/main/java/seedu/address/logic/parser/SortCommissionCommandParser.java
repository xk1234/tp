package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.SortCommissionCommand.MESSAGE_INVALID_SORT_DIRECTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT;

import seedu.address.logic.commands.SortCommissionCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SortCommissionCommand object.
 */
public class SortCommissionCommandParser implements Parser<SortCommissionCommand> {

    @Override
    public SortCommissionCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_SORT);
        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommissionCommand.MESSAGE_USAGE));
        }
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_SORT);

        if (argMultimap.getValue(PREFIX_SORT).isEmpty()) {
            return new SortCommissionCommand(false, true);
        }

        // A sort value is present
        String sortDirection = argMultimap.getValue(PREFIX_SORT).get().trim().toLowerCase();

        boolean isAscending = switch (sortDirection) {
        case "asc" -> true;
        case "desc" -> false;
        default -> throw new ParseException(MESSAGE_INVALID_SORT_DIRECTION);
        };

        return new SortCommissionCommand(true, isAscending);
    }
}
