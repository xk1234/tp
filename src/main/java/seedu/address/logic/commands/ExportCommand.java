package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTRIBUTE;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Attribute;
import seedu.address.model.Model;
import seedu.address.model.util.ExportDataUtil;

/**
 * Edits the details of an existing person in the address book.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Export selected attributes "
            + "from the displayed person list.\n"
            + "Parameters: "
            + "[" + PREFIX_ATTRIBUTE + "ATTRIBUTE]...\n"
            + "ATTRIBUTE may be any of: name, phone, email, address, commission"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ATTRIBUTE + "phone";

    public static final String MESSAGE_EXPORT_SUCCESS = "Export success";
    public static final String MESSAGE_EXPORT_FAILURE = "Export failed";
    public static final String MESSAGE_EMPTY_LIST = "List is empty, nothing to export";

    // This cannot be Attribute[] due to the given parser
    private final List<Attribute> attributes;

    /**
     * Constructs an {@code ExportCommand} to export the specified attributes from the displayed person list.
     * If no attributes are provided, all available attributes will be exported by default.
     *
     * @param attributes The list of attributes to export. If null or empty, all attributes will be exported.
     */
    public ExportCommand(List<Attribute> attributes) {
        if (attributes == null || attributes.isEmpty()) {
            this.attributes = Arrays.asList(Attribute.values());
        } else {
            this.attributes = attributes;
        }
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (model.getFilteredPersonList().isEmpty()) {
            throw new CommandException(MESSAGE_EMPTY_LIST);
        }
        try {
            ExportDataUtil.exportAsCsv(model, this.attributes);
        } catch (IOException e) {
            throw new CommandException(MESSAGE_EXPORT_FAILURE, e);
        }
        return new CommandResult(MESSAGE_EXPORT_SUCCESS);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("attributes", attributes)
                .toString();
    }
}
