package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.stream.Collectors;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * The {@code MailtoCommand} class generates a formatted {@code mailto} link
 * for the selected contacts in AscendNetwork. This allows users to quickly
 * compose an email to multiple recipients without manually copying email addresses.
 */
public class MailtoCommand extends Command {

    public static final String COMMAND_WORD = "mailto";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Format contacts into a link\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_MAILTO_SUCCESS_FORMAT =
        "Success! Copy this link to your browser's address bar:\n%1$s";

    public static final String MESSAGE_EMPTY_LIST = "List is empty, nothing to do";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (model.getFilteredPersonList().isEmpty()) {
            throw new CommandException(MESSAGE_EMPTY_LIST);
        }
        String url = model.getFilteredPersonList().stream()
                .map(person -> person.getEmail().toString())
                .collect(Collectors.joining(",", "mailto:", ""));
        return new CommandResult(String.format(MESSAGE_MAILTO_SUCCESS_FORMAT, url));
    }
}
