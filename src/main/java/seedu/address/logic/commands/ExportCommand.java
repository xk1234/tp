package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_EMPTY_LIST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTRIBUTE;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Path;
import java.util.List;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Attribute;

/**
 * Edits the details of an existing person in the address book.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Export selected attributes "
            + "from the displayed person list.\n"
            + "Parameters: FILE_PATH (Alphanumeric, ending with \".csv\") "
            + "[" + PREFIX_ATTRIBUTE + "ATTRIBUTE]...\n"
            + "ATTRIBUTE may be any of: name, phone, email, address, commission and should not have duplicates\n"
            + "Example: " + COMMAND_WORD + " "
            + "data.csv "
            + PREFIX_ATTRIBUTE + "phone";

    public static final String MESSAGE_EXPORT_SUCCESS_FORMAT = "Data is exported successfully to: %1$s.";
    public static final String MESSAGE_EXPORT_FAILURE_FILE_EXISTS_FORMAT = "Export failed: %1$s already exists!";
    public static final String MESSAGE_EXPORT_FAILURE_FORMAT = "Export to %1$s has failed.";

    // This cannot be Attribute[] due to the given parser
    private final List<Attribute> attributes;
    private final Path path;

    /**
     * Constructs an {@code ExportCommand} to export the specified attributes from
     * the displayed person list.
     * If no attributes are provided, all available attributes will be exported by
     * default.
     *
     * @param attributes The list of attributes to export. If null or empty, all
     *                   attributes will be exported.
     */
    public ExportCommand(List<Attribute> attributes, Path path) {
        requireNonNull(attributes);

        // The case where the input is empty is handled at parsing
        assert (!attributes.isEmpty());
        this.attributes = attributes;
        this.path = path;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (model.getFilteredPersonList().isEmpty()) {
            throw new CommandException(MESSAGE_EMPTY_LIST);
        }
        try {
            model.exportAsCsv(attributes, path);
        } catch (FileAlreadyExistsException e) {
            throw new CommandException(String.format(MESSAGE_EXPORT_FAILURE_FILE_EXISTS_FORMAT, path.getFileName()));
        } catch (IOException e) {
            throw new CommandException(String.format(MESSAGE_EXPORT_FAILURE_FORMAT, path.getFileName()));
        }
        return new CommandResult(String.format(MESSAGE_EXPORT_SUCCESS_FORMAT, path.getFileName()));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("attributes", attributes)
                .add("path", path)
                .toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ExportCommand otherExportCommand)) {
            return false;
        }

        return attributes.equals(otherExportCommand.attributes);
    }
}
