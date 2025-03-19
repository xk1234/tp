package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.MailtoCommand.MESSAGE_MAILTO_SUCCESS_FORMAT;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class MailtoCommandTest {
    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_mailto_success() {
        String url = model.getFilteredPersonList().stream()
                .map(person -> person.getEmail().toString())
                .collect(Collectors.joining(",", "mailto:", ""));
        CommandResult expectedCommandResult = new CommandResult(String.format(MESSAGE_MAILTO_SUCCESS_FORMAT, url));
        assertCommandSuccess(new MailtoCommand(), model, expectedCommandResult, expectedModel);
    }
}
