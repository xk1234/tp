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

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Commission)) {
            return false;
        }

        Commission otherCommission = (Commission) other;
        return otherCommission.getValue().equals(getValue());
    }

    @Override
    public int hashCode() { return value.hashCode(); }

}
