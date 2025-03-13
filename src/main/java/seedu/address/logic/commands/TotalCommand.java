package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.model.Model;
import seedu.address.model.person.Commission;
import seedu.address.model.person.Person;

/**
 * Gives the total commission of the shown list.
 */
public class TotalCommand extends Command {

    public static final String COMMAND_WORD = "total";

    public static final String MESSAGE_TOTAL_SUCCESS = "Totalled the all commissions";

    public CommandResult execute(Model model) {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        Commission totalCommission = lastShownList.stream()
                .map(Person::getCommission)
                .reduce(new Commission("0"), Commission::addValue);

        return new CommandResult(String.format(MESSAGE_TOTAL_SUCCESS, totalCommission));

    }
}
