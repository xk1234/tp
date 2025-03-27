package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's commission in the address book.
 */
public class Commission {

    public static final String MESSAGE_CONSTRAINTS = "Commission should only contain numbers, "
            + "not start with 0, "
            + "at most 9 digits long, "
            + "and it should not be blank";
    public static final String VALIDATION_REGEX = "^[1-9]\\d{0,8}$";

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
        return (test.equals("0") || test.matches(VALIDATION_REGEX));
    }

    /**
     * Returns a string type of the integer.
     */
    public static String getStringValue(int value) {
        return String.valueOf(value);
    }

    /**
     * Returns an integer type of the string.
     */
    public int getIntegerValue() {
        return Integer.parseInt(value);
    }

    /**
     * Adds commission to the current one.
     * @return the added commission.
     */
    public Commission addValue(Commission commission) throws IllegalValueException {
        int value = this.getIntegerValue() + commission.getIntegerValue();
        String commissionValue = getStringValue(value);
        try {
            return new Commission(commissionValue);
        } catch (IllegalArgumentException e) {
            throw new IllegalValueException(e.getMessage());
        }
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
