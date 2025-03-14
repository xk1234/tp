package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMMISSION;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Commission;
import seedu.address.model.person.Person;

/**
 * Adds commission to an existing person in the address book.
 */
public class AddCommissionCommand extends Command {

    public static final String COMMAND_WORD = "addc";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a commission to the person identified "
            + "by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_COMMISSION + "COMMISSION]\n"
            + "Example: " + COMMAND_WORD + " 2 "
            + PREFIX_COMMISSION + "12";
    public static final String MESSAGE_ADD_COMMISSION_SUCCESS = "New commission added";

    private final Index index;
    private final Commission commission;

    /**
     * @param index of the person in the filtered person list to edit
     * @param commission commission value to add
     */
    public AddCommissionCommand(Index index, Commission commission) {
        requireNonNull(index);
        requireNonNull(commission);

        this.index = index;
        this.commission = commission;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToAddCommission = lastShownList.get(index.getZeroBased());
        Commission commissionToBeAdded = personToAddCommission.getCommission();
        Commission addedCommission = commissionToBeAdded.addValue(commission);
        Person commissionedPerson = personToAddCommission.setCommission(addedCommission);
        model.setPerson(personToAddCommission, commissionedPerson);

        return new CommandResult(String.format(MESSAGE_ADD_COMMISSION_SUCCESS, Messages.format(commissionedPerson)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddCommissionCommand)) {
            return false;
        }

        AddCommissionCommand otherAddCommissionCommand = (AddCommissionCommand) other;
        return index.equals(otherAddCommissionCommand.index)
                && commission.equals(otherAddCommissionCommand.commission);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("commission", commission)
                .toString();
    }
}
