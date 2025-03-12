package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_TAG);
        List<String> nameKeywords = processKeywords(argMultimap.getValue(PREFIX_NAME));
        List<String> tagKeywords = processKeywords(argMultimap.getValue(PREFIX_TAG));

        if (nameKeywords.isEmpty() && tagKeywords.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        return new FindCommand(new NameAndTagPredicate(nameKeywords, tagKeywords));
    }

    /**
     * Processes keywords from the ArgumentMultimap. Each keyword value is split by
     * whitespace.
     *
     * @param values List of keyword values
     */
    private List<String> processKeywords(Optional<String> value) {
        if (!value.isPresent() || value.get().isBlank()) {
            return Collections.emptyList();
        }

        return Arrays.asList(value.get().split("\\s+"));
    }
}
