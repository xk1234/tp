package seedu.address.model.person.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class NameAndTagPredicateTest {

    @Test
    public void equals() {
        List<String> firstNameKeywords = Collections.singletonList("first");
        List<String> secondNameKeywords = Arrays.asList("first", "second");
        List<String> firstTagKeywords = Collections.singletonList("tag1");
        List<String> secondTagKeywords = Arrays.asList("tag1", "tag2");

        NameAndTagPredicate firstPredicate = new NameAndTagPredicate(firstNameKeywords, firstTagKeywords);
        NameAndTagPredicate secondPredicate = new NameAndTagPredicate(secondNameKeywords, firstTagKeywords);
        NameAndTagPredicate thirdPredicate = new NameAndTagPredicate(firstNameKeywords, secondTagKeywords);
        NameAndTagPredicate fourthPredicate = new NameAndTagPredicate(secondNameKeywords, secondTagKeywords);
        NameAndTagPredicate fifthPredicate = new NameAndTagPredicate(firstNameKeywords, Collections.emptyList());
        NameAndTagPredicate sixthPredicate = new NameAndTagPredicate(Collections.emptyList(), firstTagKeywords);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        NameAndTagPredicate firstPredicateCopy = new NameAndTagPredicate(firstNameKeywords, firstTagKeywords);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different predicates -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
        assertFalse(firstPredicate.equals(thirdPredicate));
        assertFalse(firstPredicate.equals(fourthPredicate));
        assertFalse(firstPredicate.equals(fifthPredicate));
        assertFalse(firstPredicate.equals(sixthPredicate));
    }

    @Test
    public void test_onlyNameKeywordsProvided_returnsTrue() {
        // One name keyword, matches name
        NameAndTagPredicate predicate = new NameAndTagPredicate(Collections.singletonList("Alice"),
                Collections.emptyList());
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Multiple matching keyword out of many
        predicate = new NameAndTagPredicate(Arrays.asList("Alice", "Bob"), Collections.emptyList());
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Only one matching keyword out of many
        predicate = new NameAndTagPredicate(Arrays.asList("Alice", "Charlie"), Collections.emptyList());
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Mixed-case keywords
        predicate = new NameAndTagPredicate(Arrays.asList("aLiCe", "bOB"), Collections.emptyList());
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_onlyTagKeywordsProvided_returnsTrue() {
        // One tag keyword, matches tag
        NameAndTagPredicate predicate = new NameAndTagPredicate(Collections.emptyList(),
                Collections.singletonList("friends"));
        assertTrue(predicate.test(new PersonBuilder().withTags("friends").build()));

        // Multiple tag keywords, both match tags
        predicate = new NameAndTagPredicate(Collections.emptyList(), Arrays.asList("friends", "owesMoney"));
        assertTrue(predicate.test(new PersonBuilder().withTags("friends", "owesMoney").build()));

        // Multiple tag keywords, at least one matches
        predicate = new NameAndTagPredicate(Collections.emptyList(), Arrays.asList("friends", "colleagues"));
        assertTrue(predicate.test(new PersonBuilder().withTags("friends", "family").build()));

        // Mixed-case tag keywords
        predicate = new NameAndTagPredicate(Collections.emptyList(), Arrays.asList("FrIeNdS", "oWeSmOnEy"));
        assertTrue(predicate.test(new PersonBuilder().withTags("friends", "owesMoney").build()));
    }

    @Test
    public void test_bothNameAndTagKeywordsProvided_returnsTrue() {
        // Matches both name and tag
        NameAndTagPredicate predicate = new NameAndTagPredicate(Collections.singletonList("Alice"),
                Collections.singletonList("friends"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").withTags("friends").build()));

        // Multiple keywords for both, matches all
        predicate = new NameAndTagPredicate(Arrays.asList("Alice", "Bob"), Arrays.asList("friends", "family"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").withTags("family", "friends").build()));

        // Multiple keywords for both, matches at least one from each
        predicate = new NameAndTagPredicate(Arrays.asList("Alice", "Bob"), Arrays.asList("friends", "family"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").withTags("family").build()));

        // With non-matching words in person's name and tags
        predicate = new NameAndTagPredicate(Arrays.asList("Alice", "Charlie"), Arrays.asList("friends", "colleagues"));
        assertTrue(predicate.test(
                new PersonBuilder().withName("Alice David Bob").withTags("friends", "classmate", "family").build()));
    }

    @Test
    public void test_nameAndTagDoesNotContainKeywords_returnsFalse() {
        // No keywords provided
        NameAndTagPredicate predicate = new NameAndTagPredicate(Collections.emptyList(), Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withTags("friends").build()));

        // Matches name but not tag
        predicate = new NameAndTagPredicate(Collections.singletonList("Alice"),
                Collections.singletonList("friends"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withTags("family").build()));

        // Matches tag but not name
        predicate = new NameAndTagPredicate(Collections.singletonList("Alice"), Collections.singletonList("friends"));
        assertFalse(predicate.test(new PersonBuilder().withName("Bob").withTags("friends").build()));

        // Matches neither name nor tag
        predicate = new NameAndTagPredicate(Collections.singletonList("Alice"), Collections.singletonList("friends"));
        assertFalse(predicate.test(new PersonBuilder().withName("Bob").withTags("family").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> nameKeywords = List.of("name1", "name2");
        List<String> tagKeywords = List.of("tag1", "tag2");
        NameAndTagPredicate predicate = new NameAndTagPredicate(nameKeywords, tagKeywords);

        String expected = NameAndTagPredicate.class.getCanonicalName()
                + "{namePredicate=" + new NameContainsKeywordsPredicate(nameKeywords)
                + ", tagPredicate=" + new TagContainsKeywordsPredicate(tagKeywords)
                + ", hasNameKeywords=true"
                + ", hasTagKeywords=true}";
        assertEquals(expected, predicate.toString());
    }
}
