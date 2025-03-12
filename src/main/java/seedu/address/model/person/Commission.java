package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
/**
 * Represents a Person's commission in the address book.
 */
public class Commission {

    private String value;

    /**
     * Constructs a {@code Commission}.
     *
     * @param commission
     */
    public Commission(String commission) {
        requireNonNull(commission);
        value = commission;
    }

    public String getValue() {
        return value;
    }

}
