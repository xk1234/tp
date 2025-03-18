package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Adds specified tags to all persons in the current filtered person list.
 */
public class TagCommand extends Command {

    public static final String COMMAND_WORD = "tag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds the specified tag(s) to all persons "
            + "currently in the filtered list. "
            + "Parameters: TAG [TAG]...\n"
            + "Example: " + COMMAND_WORD + " downline friends";

    public static final String MESSAGE_TAG_PERSONS_SUCCESS = "Added tag(s) to %1$d persons";
    public static final String MESSAGE_EMPTY_LIST = "No persons in the filtered list";

    private final Set<Tag> tagsToAdd;

    /**
     * Creates a TagCommand to add the specified {@code tags}
     *
     * @param tags The list of unique tags to add.
     */
    public TagCommand(Set<Tag> tags) {
        requireNonNull(tags);
        this.tagsToAdd = tags;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (lastShownList.isEmpty()) {
            throw new CommandException(MESSAGE_EMPTY_LIST);
        }

        lastShownList.stream()
                .forEach(personToEdit -> {
                    Person taggedPerson = createTaggedPerson(personToEdit, tagsToAdd);
                    model.setPerson(personToEdit, taggedPerson);
                });

        return new CommandResult(String.format(MESSAGE_TAG_PERSONS_SUCCESS, lastShownList.size()));
    }

    /**
     * Creates and returns a {@code Person} with the tags from {@code personToEdit}
     * combined with the specified {@code tagsToAdd}.
     */
    private static Person createTaggedPerson(Person personToEdit, Set<Tag> tagsToAdd) {
        Set<Tag> updatedTags = new HashSet<>(personToEdit.getTags());
        updatedTags.addAll(tagsToAdd);

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
        if (!(other instanceof TagCommand)) {
            return false;
        }

        TagCommand otherTagCommand = (TagCommand) other;
        return tagsToAdd.equals(otherTagCommand.tagsToAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("tagsToAdd", tagsToAdd)
                .toString();
    }
}
