package seedu.address.model.person.predicates;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * Combines the {@code NameContainsKeywordsPredicate} and
 * {@code TagContainsKeywordsPredicate}.
 * A person matches if they match both predicates, or if only one predicate is
 * active, they match that predicate.
 */
public class NameAndTagPredicate implements Predicate<Person> {
    private final NameContainsKeywordsPredicate namePredicate;
    private final TagContainsKeywordsPredicate tagPredicate;
    private final boolean hasNameKeywords;
    private final boolean hasTagKeywords;

    /**
     * Constructs a {@code NameAndTagPredicate}.
     *
     * @param nameKeywords the list of name keywords to match
     * @param tagKeywords  the list of tag keywords to match
     */
    public NameAndTagPredicate(List<String> nameKeywords, List<String> tagKeywords) {
        this.namePredicate = new NameContainsKeywordsPredicate(nameKeywords);
        this.tagPredicate = new TagContainsKeywordsPredicate(tagKeywords);
        this.hasNameKeywords = !nameKeywords.isEmpty();
        this.hasTagKeywords = !tagKeywords.isEmpty();
    }

    @Override
    public boolean test(Person person) {
        if (!hasNameKeywords && !hasTagKeywords) {
            return false;
        }

        boolean matchesName = !hasNameKeywords || namePredicate.test(person);
        boolean matchesTag = !hasTagKeywords || tagPredicate.test(person);

        return matchesName && matchesTag;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NameAndTagPredicate)) {
            return false;
        }

        NameAndTagPredicate otherPredicate = (NameAndTagPredicate) other;
        return namePredicate.equals(otherPredicate.namePredicate)
                && tagPredicate.equals(otherPredicate.tagPredicate)
                && hasNameKeywords == otherPredicate.hasNameKeywords
                && hasTagKeywords == otherPredicate.hasTagKeywords;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("namePredicate", namePredicate)
                .add("tagPredicate", tagPredicate)
                .add("hasNameKeywords", hasNameKeywords)
                .add("hasTagKeywords", hasTagKeywords)
                .toString();
    }
}
