package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

public class AddCommissionCommand extends Command {

    public static final String COMMAND_WORD = "addc";

    public static final String MESSAGE_ADD_COMMISSION_SUCCESS = "Commission added on person;";

    private final Index index;
    private final double amount;


    public AddCommissionCommand(Index index, double amount) {
        requireNonNull(index);
        requireNonNull(amount);

        this.index = index;
        this.amount = amount;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return new CommandResult(MESSAGE_ADD_COMMISSION_SUCCESS);
    }

}