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

    public static final String MESSAGE_TOTAL_OVERFLOW = "Total commission is overflowed the limit: 1'000'000'000!";
    /**
     * Calculates the total commission of the people.
     */
    public Commission getTotal(List<Person> people) {
        return people.stream()
                .map(Person::getCommission)
                .reduce(new Commission("0"), (c1, c2) -> {
                    try {
                        return c1.addValue(c2);
                    } catch (IllegalValueException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        Commission totalCommission;
        try {
            totalCommission = getTotal(lastShownList);
        } catch (RuntimeException e) {
            if (e.getCause() instanceof IllegalValueException) {
                throw new CommandException(MESSAGE_TOTAL_OVERFLOW);
            }
            throw e;
        }

        return new CommandResult(String.format(MESSAGE_TOTAL_SUCCESS + totalCommission.toString()));

    }
}
