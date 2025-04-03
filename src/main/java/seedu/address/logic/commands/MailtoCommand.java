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

    // If we display the link in full here, PE testers will flag GUI bugs
    public static final String MESSAGE_MAILTO_HOW_TO =
        "Copy the mailto link with \"Copy URL\" button and open it in a browser's address bar.";

    public static final String SHOWING_MAILTO_MESSAGE = "Opened mailto link window.";

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
        CommandResult.LinkedText linkedText = new CommandResult.LinkedText(
                url, MESSAGE_MAILTO_HOW_TO);
        return new CommandResult(SHOWING_MAILTO_MESSAGE, linkedText, false);
    }
}
