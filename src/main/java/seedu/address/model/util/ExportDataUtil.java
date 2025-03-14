package seedu.address.model.util;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.EnumMap;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import seedu.address.model.Attribute;
import seedu.address.model.Model;
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
