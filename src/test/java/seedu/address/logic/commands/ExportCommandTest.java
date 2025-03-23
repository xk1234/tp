package seedu.address.logic.commands;

import static seedu.address.logic.Messages.MESSAGE_EMPTY_LIST;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Attribute;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class ExportCommandTest {

    @Test
    public void execute_nonEmptyUnfilteredList_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        String expectedMessage = ExportCommand.MESSAGE_EXPORT_SUCCESS;
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        List<Attribute> attributes = List.of(Attribute.NAME);
        Path path = Path.of("export.csv");
        ExportCommand exportCommand = new ExportCommand(attributes, path);
        assertCommandSuccess(exportCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_emptyUnfilteredList_failure() {
        Model model = new ModelManager(new AddressBook(), new UserPrefs());
        List<Attribute> attributes = List.of(Attribute.NAME);
        Path path = Path.of("export.csv");
        ExportCommand exportCommand = new ExportCommand(attributes, path);
        assertCommandFailure(exportCommand, model, MESSAGE_EMPTY_LIST);
    }

    @Test
    public void execute_caughtIoException_failure() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs()) {
            @Override
            public void exportAsCsv(List<Attribute> attributes, Path path) throws IOException {
                throw new IOException();
            }
        };
        String expectedMessage = ExportCommand.MESSAGE_EXPORT_FAILURE;
        List<Attribute> attributes = List.of(Attribute.NAME);
        Path path = Path.of("export.csv");
        ExportCommand exportCommand = new ExportCommand(attributes, path);
        assertCommandFailure(exportCommand, model, expectedMessage);
    }
}
