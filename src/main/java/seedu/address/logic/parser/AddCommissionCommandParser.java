package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMMISSION;
import seedu.address.logic.commands.AddCommissionCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class AddCommissionCommandParser implements Parser<AddCommissionCommand> {
    public AddCommisionCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_COMMISSION);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT), pe);
        }

        return new AddCommissionCommand(index, )
    }
}