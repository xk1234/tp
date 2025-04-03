package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.predicates.NameAndTagPredicate;

/**
 * Finds and lists all persons in address book whose name contains any of the
 * name keywords and/or whose tags contain any of the tag keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names contain any of "
            + "the specified name keywords (case-insensitive) and/or whose tags contain any of the specified "
            + "tag keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "At least one name keyword or tag keyword must be provided.\n"
            + "If both name and tag keywords are provided, contacts must match both criteria.\n"
            + "Parameters: {n/NAME_KEYWORD [NAME_KEYWORD]... [t/TAG_KEYWORD...] | "
            + "t/TAG_KEYWORD [TAG_KEYWORD]... [n/NAME_KEYWORD...]}\n"
            + "Example: " + COMMAND_WORD + " n/alice bob t/downline";

    private final NameAndTagPredicate predicate;

    public FindCommand(NameAndTagPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindCommand)) {
            return false;
        }

        FindCommand otherFindCommand = (FindCommand) other;
        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
