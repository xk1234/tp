package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.List;
import java.util.function.Predicate;

import javafx.collections.transformation.FilteredList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Includes the person to the list.
 */
public class IncludePersonCommand extends Command {

    public static final String COMMAND_WORD = "incl";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Includes the existing people"
            + " with a keyword (case-insensitive) in their names to the currently displayed list.\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John";

    public static final String MESSAGE_SUCCESS = "People with a keyword are now in the address book";
    public static final String MESSAGE_PERSON_NOT_FOUND = "No person with a keyword exists in the address book";

    private final Predicate<Person> namePredicate;

    /**
     * Creates an IncludePersonCommand to add the person to the list.
     */
    public IncludePersonCommand(Predicate<Person> namePredicate) {
        requireNonNull(namePredicate);
        this.namePredicate = namePredicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> allPeopleList = model.getAddressBook().getPersonList();
        boolean isContactExisting = allPeopleList.stream()
                .anyMatch(namePredicate);
        if (!isContactExisting) {
            throw new CommandException(MESSAGE_PERSON_NOT_FOUND);
        }

        Predicate<? super Person> currentPredicate = ((FilteredList<Person>) model
                    .getFilteredPersonList()).getPredicate();
        Predicate<? super Person> effectivePredicate = (currentPredicate != null)
                ? currentPredicate
                : person -> true;
        Predicate<Person> newPredicate = person -> effectivePredicate.test(person) || namePredicate.test(person);
        model.updateFilteredPersonList(newPredicate);

        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof IncludePersonCommand otherCommand)) {
            return false;
        }
        return otherCommand.namePredicate.equals(namePredicate);
    }
}
