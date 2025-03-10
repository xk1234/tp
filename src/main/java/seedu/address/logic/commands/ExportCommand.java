package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTRIBUTE;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

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

    // Should we change this into a parameter of the export command?
    public static final String DEFAULT_FILE_NAME = "export.csv";

    // We have to use Object here since none of name, email, etc. have a common interface
    private static final EnumMap<Attribute, Function<Person, Object>> PERSON_METHOD_MAP =
            new EnumMap<>(Attribute.class);
    static {
        PERSON_METHOD_MAP.put(Attribute.NAME, Person::getName);
        PERSON_METHOD_MAP.put(Attribute.PHONE, Person::getPhone);
        PERSON_METHOD_MAP.put(Attribute.EMAIL, Person::getEmail);
        PERSON_METHOD_MAP.put(Attribute.ADDRESS, Person::getAddress);
        // TODO PERSON_METHOD_MAP.put(Attribute.COMMISSION, Person::getCommission);
    }

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
            this.attributes = Arrays.stream(Attribute.values()).toList();
        } else {
            this.attributes = attributes;
        }
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (lastShownList.isEmpty()) {
            throw new CommandException(MESSAGE_EMPTY_LIST);
        }

        // TODO discuss if we separate this into a component
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DEFAULT_FILE_NAME))) {
            // CSV header
            writer.write(attributes.stream()
                    .map(Objects::toString)
                    .collect(Collectors.joining(",")));
            writer.newLine();
            // CSV body
            for (Person person : lastShownList) {
                writer.write(attributes.stream()
                        .map(attribute -> PERSON_METHOD_MAP.get(attribute)
                                .apply(person)
                                .toString())
                        .collect(Collectors.joining(",")));
                writer.newLine();
            }
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

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public enum Attribute {
        NAME,
        PHONE,
        EMAIL,
        ADDRESS,
        // TODO COMMISSION,
    }
}
