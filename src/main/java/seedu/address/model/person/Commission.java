package seedu.address.model.person;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's commission in the address book.
 */
public class Commission {

    public final String value;

    /**
     * Constructs a {@code Commission}.
     *
     * @param commission
     */
    public Commission(String commission) {
        requireNonNull(commission);
        // if not integer, should throw exception
        int i = Integer.parseInt(commission);
        checkArgument(i < Integer.MAX_VALUE, "Commission cannot be handled");
        value = commission;
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
        return otherCommission.value.equals(value);
    }

    @Override
    public int hashCode() { return value.hashCode(); }

}
