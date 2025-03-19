package seedu.address.logic.commands;

import java.util.stream.Collectors;

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

    public static final String MESSAGE_MAILTO_SUCCESS_FORMAT = "Success! Copy this link to your browser's address bar: %1$s";

    @Override
    public CommandResult execute(Model model) {
        String url = model.getFilteredPersonList().stream()
                .map(person -> person.getEmail().toString())
                .collect(Collectors.joining(",", "mailto:", ""));
        return new CommandResult(String.format(MESSAGE_MAILTO_SUCCESS_FORMAT, url), false, false);
    }
}
