package seedu.address.model.person;

/**
 * Stores the details to edit the person with. Each non-empty field value will replace the
 * corresponding field value of the person.
 */
public enum Attribute {
    NAME,
    PHONE,
    EMAIL,
    ADDRESS,
    COMMISSION;

    public static final String MESSAGE_CONSTRAINTS_VALID_ATTRIBUTES =
            "ATTRIBUTE may be: name, phone, email, address, or commission.";
    public static final String MESSAGE_CONSTRAINTS_NO_DUPLICATES =
            "There are duplicates in the provided attributes.";
}
