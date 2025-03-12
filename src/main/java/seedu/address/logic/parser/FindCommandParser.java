package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.predicates.NameAndTagPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the
     * FindCommand and returns a FindCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_TAG);

        List<String> nameKeywords = processKeywords(argMultimap.getAllValues(PREFIX_NAME));
        List<String> tagKeywords = processKeywords(argMultimap.getAllValues(PREFIX_TAG));

        if (nameKeywords.isEmpty() && tagKeywords.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        return new FindCommand(new NameAndTagPredicate(nameKeywords, tagKeywords));
    }

    /**
     * Processes keywords from the ArgumentMultimap.
     * Each keyword value is split by whitespace and filtered for empty strings.
     *
     * @param values List of keyword values
     */
    private List<String> processKeywords(List<String> values) {
        return values.stream().filter(s -> !s.isEmpty()).flatMap(s -> Arrays.stream(s.trim().split("\\s+"))).toList();
    }
}
