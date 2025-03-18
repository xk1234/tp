package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.logic.commands.IncludePersonCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;
import seedu.address.model.person.predicates.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new IncludePersonCommand object
 */
public class IncludePersonCommandParser implements Parser<IncludePersonCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the IncludePersonCommand
     * and returns an IncludePersonCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public IncludePersonCommand parse(String args) throws ParseException {
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME);
        argumentMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME);

        ParseException pe = new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                IncludePersonCommand.MESSAGE_USAGE));
        if (argumentMultimap.getValue(PREFIX_NAME).isEmpty()) {
            throw pe;
        }

        String nameKeyword = argumentMultimap.getValue(PREFIX_NAME).get();
        nameKeyword = nameKeyword.trim();
        if (nameKeyword.isEmpty()) {
            throw pe;
        }
        List<String> keyword = List.of(nameKeyword);
        Predicate<Person> namePredicate = new NameContainsKeywordsPredicate(keyword);

        return new IncludePersonCommand(namePredicate);
    }

}
