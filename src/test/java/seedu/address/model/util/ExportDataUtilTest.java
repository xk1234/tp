package seedu.address.model.util;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPersons.getTypicalPersons;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Attribute;

public class ExportDataUtilTest {

    @TempDir
    public Path testFolder;

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void exportAsCsv_multipleAttributes_success() {
        List<Attribute> attributes = List.of(Attribute.NAME, Attribute.EMAIL);
        Path exportPath = testFolder.resolve("export.csv");
        AtomicReference<String> actualCsv = new AtomicReference<>();
        assertDoesNotThrow(() -> {
            ExportDataUtil.exportAsCsv(model, attributes, exportPath);
            actualCsv.set(Files.readString(exportPath));
        });
        String expectedCsv = Stream.concat(
                Stream.of("NAME,EMAIL"),
                getTypicalPersons().stream()
                        .map(person -> person.getName() + "," + person.getEmail())
        ).collect(Collectors.joining(System.lineSeparator()))
                + System.lineSeparator();
        assertEquals(expectedCsv, actualCsv.get());
    }
}
