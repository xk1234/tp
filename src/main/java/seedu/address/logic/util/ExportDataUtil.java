package seedu.address.logic.util;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.EnumMap;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import seedu.address.model.Model;
import seedu.address.model.person.Attribute;
import seedu.address.model.person.Person;

/**
 * Contains utility methods for exporting data from {@code AddressBook}.
 */
public class ExportDataUtil {
    // We have to use Object here since none of name, email, etc. have a common interface
    private static final EnumMap<Attribute, Function<Person, Object>> PERSON_METHOD_MAP =
            new EnumMap<>(Attribute.class);
    static {
        PERSON_METHOD_MAP.put(Attribute.NAME, Person::getName);
        PERSON_METHOD_MAP.put(Attribute.PHONE, Person::getPhone);
        PERSON_METHOD_MAP.put(Attribute.EMAIL, Person::getEmail);
        PERSON_METHOD_MAP.put(Attribute.ADDRESS, Person::getAddress);
        PERSON_METHOD_MAP.put(Attribute.COMMISSION, Person::getCommission);
    }

    /**
     * Exports the selected attributes of all persons in the given model to a CSV file at the specified path.
     * The CSV file will have a header row containing the names of the attributes, followed by rows
     * representing each person's data for the specified attributes.
     *
     * @param model      The model containing the list of persons to be exported.
     * @param attributes The list of attributes to be included in the CSV file. Each attribute corresponds
     *                  to a column in the CSV file.
     * @param path       The file path where the CSV file will be created. If the file already exists,
     *                  it will be overwritten.
     * @throws IOException If an I/O error occurs while writing to the file, such as if the path is invalid
     *                     or the file cannot be created or written to.
     */
    public static void exportAsCsv(Model model, List<Attribute> attributes, Path path) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            // CSV header
            writer.write(attributes.stream()
                    .map(Objects::toString)
                    .collect(Collectors.joining(",")));
            writer.newLine();
            // CSV body
            for (Person person : model.getFilteredPersonList()) {
                writer.write(attributes.stream()
                        .map(attribute -> PERSON_METHOD_MAP.get(attribute)
                                .apply(person)
                                .toString())
                        .collect(Collectors.joining(",")));
                writer.newLine();
            }
        }
    }
}
