package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.stream.Collectors;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * The {@code MailtoCommand} class generates a formatted {@code mailto} link
 * for the selected contacts in AscendNetwork. This allows users to quickly
 * compose an email to multiple recipients without manually copying email addresses.
 */
public class MailtoCommand extends Command {

    public static final String COMMAND_WORD = "mailto";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Generates a mailto link using the email addresses of selected contacts in AscendNetwork.\n"
        + "Copy the link to your browser's address bar to compose an email to all the selected recipients.\n"
        + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_MAILTO_SUCCESS_FORMAT =
        "Success! Copy this link to your browser's address bar:\n%1$s";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> filteredPersons = model.getFilteredPersonList();
        if (filteredPersons.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_EMPTY_LIST);
        }
        String url = filteredPersons.stream()
                .map(person -> person.getEmail().toString())
                .collect(Collectors.joining(",", "mailto:", ""));
        return new CommandResult(String.format(MESSAGE_MAILTO_SUCCESS_FORMAT, url));
    }
}
