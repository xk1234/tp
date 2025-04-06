package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.CsvFileName;
import seedu.address.model.person.Address;
import seedu.address.model.person.Attribute;
import seedu.address.model.person.Commission;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        Optional<BigInteger> valueOptional = StringUtil.getNonZeroUnsignedInteger(trimmedIndex);
        if (valueOptional.isEmpty()) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        BigInteger value = valueOptional.get();
        if (value.compareTo(BigInteger.valueOf(Integer.MAX_VALUE)) > 0) {
            return Index.fromOneBased(Integer.MAX_VALUE);
        } else {
            return Index.fromOneBased(value.intValue());
        }
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }


    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses {@code Collection<String> attributes} into a {@code List<Attribute>}.
     */
    public static List<Attribute> parseAttributes(Collection<String> attributes) throws ParseException {
        requireNonNull(attributes);
        if (attributes.isEmpty()) {
            return Arrays.asList(Attribute.values());
        }
        try {
            List<Attribute> result = attributes.stream()
                    .map(String::trim)
                    .map(String::toUpperCase)
                    .map(Attribute::valueOf)
                    .toList();
            // copyOf could throw if we don't make sure attributes is non-empty
            EnumSet<Attribute> uniqueValues = EnumSet.copyOf(result);
            if (uniqueValues.size() != result.size()) {
                throw new ParseException(Attribute.MESSAGE_CONSTRAINTS_NO_DUPLICATES);
            }
            return result;
        } catch (IllegalArgumentException e) {
            throw new ParseException(Attribute.MESSAGE_CONSTRAINTS_VALID_ATTRIBUTES);
        }
    }

    /**
     * Parses a {@code String commission} into a {@code Commission}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code commission} is invalid.
     */
    public static Commission parseCommission(String commission) throws ParseException {
        requireNonNull(commission);
        String trimmedCommission = commission.trim();
        if (!Commission.isValidCommission(trimmedCommission)) {
            throw new ParseException(Commission.MESSAGE_CONSTRAINTS);
        }
        return new Commission(trimmedCommission);
    }

    /**
     * Parses a {@code String fileName} into a {@code Path}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code String fileName} is invalid.
     */
    public static CsvFileName parseCsvFileName(String csvFileName) throws ParseException {
        requireNonNull(csvFileName);
        String trimmedCsvFileName = csvFileName.trim();
        if (!CsvFileName.isValidCsvFileName(trimmedCsvFileName)) {
            throw new ParseException(CsvFileName.MESSAGE_CONSTRAINTS);
        }
        return new CsvFileName(trimmedCsvFileName);
    }
}
