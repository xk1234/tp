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
public class IncludePersonCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the IncludePersonCommand
     * and returns an IncludePersonCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public IncludePersonCommand parse(String args) throws ParseException {
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME);
        argumentMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME);

        if (argumentMultimap.getValue(PREFIX_NAME).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, IncludePersonCommand.MESSAGE_USAGE));
        }

        String nameKeyWord = argumentMultimap.getValue(PREFIX_NAME).get();
        nameKeyWord = nameKeyWord.trim();
        if (nameKeyWord.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, IncludePersonCommand.MESSAGE_USAGE));
        }
        List<String> keyWord = List.of(nameKeyWord);
        Predicate<Person> namePredicate = new NameContainsKeywordsPredicate(keyWord);

        return new IncludePersonCommand(namePredicate);
    }

}
