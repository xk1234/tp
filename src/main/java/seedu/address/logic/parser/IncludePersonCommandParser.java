package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME);
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME);

        ParseException pe = new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                IncludePersonCommand.MESSAGE_USAGE));
        if (argMultimap.getValue(PREFIX_NAME).isEmpty()) {
            throw pe;
        }

        List<String> keywords = processKeywords(argMultimap.getValue(PREFIX_NAME));

        if (keywords.isEmpty()) {
            throw pe;
        }
        Predicate<Person> namePredicate = new NameContainsKeywordsPredicate(keywords);

        return new IncludePersonCommand(namePredicate);
    }

    /**
     * Processes keywords from the ArgumentMultimap. Each keyword value is split by
     * whitespace.
     *
     * @param value List of keyword values
     */
    private List<String> processKeywords(Optional<String> value) {
        return Arrays.asList(value.get().split("\\s+"));
    }
}
