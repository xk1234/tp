package seedu.address.model.util;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.model.Attribute;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ExportDataUtilTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @TempDir
    public Path testFolder;

    @Test
    public void exportAsCsv_multipleAttributes_success() {
        List<Attribute> attributes = List.of(Attribute.NAME, Attribute.EMAIL);
        Path exportPath = testFolder.resolve("export.csv");
        AtomicReference<String> actualCsv = new AtomicReference<>();
        assertDoesNotThrow(() -> {
            ExportDataUtil.exportAsCsv(model, attributes, exportPath);
            actualCsv.set(Files.readString(exportPath));
        });
        String expectedCsv = String.join(System.lineSeparator(),
            "NAME,EMAIL",
            "Alice Pauline,alice@example.com",
            "Benson Meier,johnd@example.com",
            "Carl Kurz,heinz@example.com",
            "Daniel Meier,cornelia@example.com",
            "Elle Meyer,werner@example.com",
            "Fiona Kunz,lydia@example.com",
            "George Best,anna@example.com") + System.lineSeparator();
        assertEquals(expectedCsv, actualCsv.get());
    }
}
