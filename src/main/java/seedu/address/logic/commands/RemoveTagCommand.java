package seedu.address.logic.commands;

import static java.util.Collections.disjoint;
import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_EMPTY_LIST;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Removes specified tags from all persons in the filtered person list.
 */
public class RemoveTagCommand extends Command {

    public static final String COMMAND_WORD = "rmtag";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes the specified tag(s) from all persons in the current filtered list.\n"
            + "Parameters: TAG [TAGS]...\n"
            + "Example: " + COMMAND_WORD + " friend colleague";

    public static final String MESSAGE_REMOVE_TAG_SUCCESS = "Removed tag(s) from %1$d person(s)";

    private final Set<Tag> tagsToRemove;

    /**
     * Creates a RemoveTagCommand to remove the specified {@code Tag}s from all
     * persons.
     *
     * @param tagsToRemove Set of unique tags to remove from all persons
     */
    public RemoveTagCommand(Set<Tag> tagsToRemove) {
        requireNonNull(tagsToRemove);
        this.tagsToRemove = tagsToRemove;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> lastShownList = model.getFilteredPersonList();

        if (lastShownList.isEmpty()) {
            throw new CommandException(MESSAGE_EMPTY_LIST);
        }

        List<Person> personsToEdit = lastShownList.stream()
                .filter(person -> !disjoint(person.getTags(), tagsToRemove))
                .toList();

        personsToEdit.forEach(person -> {
            Person updatedPerson = createUntaggedPerson(person, tagsToRemove);
            model.setPerson(person, updatedPerson);
        });

        return new CommandResult(String.format(MESSAGE_REMOVE_TAG_SUCCESS, personsToEdit.size()));
    }

    /**
     * Creates a new {@code Person} object with the tags from {@code tagsToRemove}
     * filtered out from {@code personToEdit}.
     */
    private static Person createUntaggedPerson(Person personToEdit, Set<Tag> tagsToRemove) {
        Set<Tag> updatedTags = new HashSet<>(personToEdit.getTags());
        updatedTags.removeAll(tagsToRemove);

        return new Person(
                personToEdit.getName(),
                personToEdit.getPhone(),
                personToEdit.getEmail(),
                personToEdit.getAddress(),
                personToEdit.getCommission(),
                updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RemoveTagCommand)) {
            return false;
        }

        RemoveTagCommand otherRemoveTagCommand = (RemoveTagCommand) other;
        return tagsToRemove.equals(otherRemoveTagCommand.tagsToRemove);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("tagsToRemove", tagsToRemove)
                .toString();
    }
}
