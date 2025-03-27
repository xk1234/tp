package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Commission;
import seedu.address.model.person.Person;

/**
 * Gives the total commission of the shown list.
 */
public class TotalCommand extends Command {

    public static final String COMMAND_WORD = "total";

    public static final String MESSAGE_TOTAL_SUCCESS = "Total commission is: ";

    public static final String MESSAGE_TOTAL_OVERFLOW = "Total commission has overflowed the limit: 1'000'000'000!";
    /**
     * Calculates the total commission of the people.
     */
    public Commission getTotal(List<Person> people) throws CommandException {
        Commission totalCommission = new Commission("0");
        for (Person person : people) {
            Commission commission = person.getCommission();
            try {
                totalCommission = totalCommission.addValue(commission);
            } catch (IllegalValueException e) {
                throw new CommandException(MESSAGE_TOTAL_OVERFLOW);
            }
        }
        return totalCommission;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        Commission totalCommission = getTotal(lastShownList);
        return new CommandResult(MESSAGE_TOTAL_SUCCESS + totalCommission.toString());
    }
}
