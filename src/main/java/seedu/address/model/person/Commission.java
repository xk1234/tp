package seedu.address.model.person;

/**
 * Represents a Person's commission in the address book.
 */
public class Commission {

    public String value;
    /**
     * Constructs a {@code Commission}.
     *
     * @param commission
     */
    public Commission(String commission) {
        requireNonNull(commission);
        value = commission;
    }

}