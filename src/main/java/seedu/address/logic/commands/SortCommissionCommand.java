package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_EMPTY_LIST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT;

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
            + "Parameters: [" + PREFIX_SORT + "asc|" + PREFIX_SORT + "desc]\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_SORT + "asc\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_INVALID_SORT_DIRECTION =
            "Invalid command format! comm " + PREFIX_SORT + "<direction> where <direction> is either asc or desc.\n"
                    + "Example: comm " + PREFIX_SORT + "asc";

    public final String messageSuccess;

    private final boolean isAscending;

    /**
     * Creates a SortCommissionCommand.
     *
     * @param isAscending    True if sorting ascending, false if descending. Ignored if isSortProvided is false.
     */
    public SortCommissionCommand(boolean isAscending) {
        this.isAscending = isAscending;
        this.messageSuccess = "Listed all persons sorted by commission in "
                + (isAscending ? "ascending" : "descending") + " order.";
    }


    public List<Person> getSortedList(List<Person> list) {
        List<Person> sortedList = new ArrayList<>(list);
        Comparator<Person> compareByCommission = Comparator.comparing(p ->
                Integer.parseInt(p.getCommission().value));

        if (!isAscending) {
            compareByCommission = compareByCommission.reversed();
        }

        sortedList.sort(compareByCommission);
        return sortedList;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> sortedList = getSortedList(model.getFilteredPersonList());

        if (sortedList.isEmpty()) {
            return new CommandResult(MESSAGE_EMPTY_LIST);
        }

        StringBuilder msgBuilder = new StringBuilder(messageSuccess).append("\n");
        int idx = 1;
        for (Person person : sortedList) {
            msgBuilder.append(idx).append(". ").append(person.getName()).append(", ")
                    .append(person.getCommission()).append("\n");
            idx++;
        }
        String msg = msgBuilder.toString();
        return new CommandResult(msg);
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
        return isAscending == o.isAscending;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("isAscending", isAscending ? "asc" : "desc")
                .toString();
    }
}
