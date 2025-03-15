package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Lists all persons and their commissions. Can optionally sort by commission ascending or descending.
 */
public class SortCommissionCommand extends Command {

    public static final String COMMAND_WORD = "comm";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays the list of people along with their "
            + "lifetime commissions. Optionally sorts by commission.\n"
            + "Parameters: [s/asc|s/desc]\n"
            + "Example: " + COMMAND_WORD + " s/asc\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_INVALID_SORT_DIRECTION =
            "Invalid command format! comm s/<direction> where <direction> is either asc or desc.\n"
                    + "Example: comm s/asc";

    public final String messageSuccess;

    private final boolean isSortProvided;
    private final boolean isAscending;

    /**
     * Creates a SortCommissionCommand.
     *
     * @param isSortProvided Whether the user provided a sort flag (s/asc or s/desc).
     * @param isAscending    True if sorting ascending, false if descending. Ignored if isSortProvided is false.
     */
    public SortCommissionCommand(boolean isSortProvided, boolean isAscending) {
        this.isSortProvided = isSortProvided;
        this.isAscending = isAscending;
        this.messageSuccess = "Listed all persons sorted by commission in "
                + (isSortProvided ? (isAscending ? "ascending" : "descending") : "default")
                + " order.";
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (!isSortProvided) {
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            return new CommandResult(messageSuccess);
        }

        List<Person> currentList = model.getFilteredPersonList();
        List<Person> sortedList = new ArrayList<>(currentList);
        Comparator<Person> compareByCommission = Comparator.comparing(p ->
                Integer.parseInt(p.getCommission().value));

        if (!isAscending) {
            compareByCommission = compareByCommission.reversed();
        }

        sortedList.sort(compareByCommission);
        model.setPersons(sortedList);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(messageSuccess);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof SortCommissionCommand)) {
            return false;
        }

        SortCommissionCommand o = (SortCommissionCommand) other;
        return isSortProvided == o.isSortProvided
                && isAscending == o.isAscending;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("isAscending", isAscending ? "asc" : "desc")
                .toString();
    }
}
