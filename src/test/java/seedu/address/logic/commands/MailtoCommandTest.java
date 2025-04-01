package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.MailtoCommand.MESSAGE_MAILTO_SUCCESS_FORMAT;
import static seedu.address.logic.commands.MailtoCommand.SHOWING_MAILTO_MESSAGE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class MailtoCommandTest {
    @Test
    public void execute_emptyList_throwsCommandException() {
        final Model model = new ModelManager();
        assertCommandFailure(new MailtoCommand(), model, Messages.MESSAGE_EMPTY_LIST);
    }

    @Test
    public void execute_nonEmptyList_success() {
        final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        final Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        String url = model.getFilteredPersonList().stream()
                .map(person -> person.getEmail().toString())
                .collect(Collectors.joining(",", "mailto:", ""));
        String text = String.format(MESSAGE_MAILTO_SUCCESS_FORMAT, url);
        CommandResult.LinkedText linkedText = new CommandResult.LinkedText(url, text);
        CommandResult expectedCommandResult = new CommandResult(SHOWING_MAILTO_MESSAGE, linkedText, false);
        assertCommandSuccess(new MailtoCommand(), model, expectedCommandResult, expectedModel);
    }
}
