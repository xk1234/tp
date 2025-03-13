package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's commission in the address book.
 */
public class Commission {

    public static final String MESSAGE_CONSTRAINTS = "Commissions can take any nonnegative integer value";

    public final String value;

    /**
     * Constructs a {@code Commission}.
     *
     * @param commission
     */
    public Commission(String commission) {
        requireNonNull(commission);
        checkArgument(isValidCommission(commission), MESSAGE_CONSTRAINTS);
        value = commission;
    }

    /**
     * Returns true if a given string is a valid commission.
     */
    public static boolean isValidCommission(String test) {
        int i;
        try {
            i = Integer.parseInt(test);
        } catch (Exception e) {
            return false;
        }
        if (i < 0) {
            return false;
        }
        return true;
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
    public int hashCode() {
        return value.hashCode();
    }

}
