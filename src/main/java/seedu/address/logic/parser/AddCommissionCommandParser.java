package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMMISSION;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddCommissionCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Commission;

/**
 * Parses input argumets and creates a new AddCommissionCommand object
 */
public class AddCommissionCommandParser implements Parser<AddCommissionCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommissionCommand
     * and returns an AddCommissionCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommissionCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_COMMISSION);

        Index index;

        Commission commission;

        if (argMultimap.getValue(PREFIX_COMMISSION).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommissionCommand.MESSAGE_USAGE));
        }

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommissionCommand.MESSAGE_USAGE));
        }

        commission = ParserUtil.parseCommission(argMultimap.getValue(PREFIX_COMMISSION).get());

        return new AddCommissionCommand(index, commission);
    }
}
