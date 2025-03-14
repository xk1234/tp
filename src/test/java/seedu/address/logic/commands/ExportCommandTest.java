package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Attribute;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

class MockIoFailExportCommand extends ExportCommand {
    /**
     * Constructs an {@code ExportCommand} to export the specified attributes from the displayed person list.
     * If no attributes are provided, all available attributes will be exported by default.
     *
     * @param attributes The list of attributes to export. If null or empty, all attributes will be exported.
     */
    public MockIoFailExportCommand(List<Attribute> attributes) {
        super(attributes);
    }

    @Override
    protected void exportAsCsv(Model model, List<Attribute> attributes) throws IOException {
        throw new IOException();
    }
}

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class ExportCommandTest {
    @Test
    public void execute_nonEmptyUnfilteredList_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        String expectedMessage = "Export success";
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        List<Attribute> attributes = List.of(Attribute.NAME);
        ExportCommand exportCommand = new ExportCommand(attributes);
        assertCommandSuccess(exportCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_emptyUnfilteredList_failure() {
        Model model = new ModelManager(new AddressBook(), new UserPrefs());
        String expectedMessage = "List is empty, nothing to export";
        List<Attribute> attributes = List.of(Attribute.NAME);
        ExportCommand exportCommand = new ExportCommand(attributes);
        assertCommandFailure(exportCommand, model, expectedMessage);
    }

    @Test
    public void execute_caughtIoException_failure() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        String expectedMessage = "Export failed";
        List<Attribute> attributes = List.of(Attribute.NAME);
        ExportCommand exportCommand = new MockIoFailExportCommand(attributes);
        assertCommandFailure(exportCommand, model, expectedMessage);
    }
}
