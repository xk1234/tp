package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;

import seedu.address.model.Model;
import seedu.address.model.person.Commission;
import seedu.address.model.person.Person;

/**
 * Gives the total commission of the shown list.
 */
public class SummaryCommand extends Command {

    public static final String COMMAND_WORD = "summary";

    /**
     * Calculates the total commission of the people.
     */
    public BigInteger getTotal(List<Person> people) {
        BigInteger total = BigInteger.valueOf(0);
        for (Person person : people) {
            Commission commission = person.getCommission();
            total = total.add(new BigInteger(String.valueOf(commission.value)));
        }
        return total;
    }

    public int getNumContacts(List<Person> people) {
        return people.size();
    }

    public Person getHighest(List<Person> people) {
        return people.stream()
                .max(Comparator.comparing(person -> Integer.parseInt(person.getCommission().value)))
                .orElse(null);
    }

    public Person getLowest(List<Person> people) {
        return people.stream()
                .min(Comparator.comparing(person -> Integer.parseInt(person.getCommission().value)))
                .orElse(null);
    }

    public double getAverage(List<Person> people) {
        if (people.isEmpty()) {
            return 0.00;
        }
        BigDecimal total = new BigDecimal(getTotal(people));
        BigDecimal count = BigDecimal.valueOf(people.size());
        BigDecimal average = total.divide(count, 2, RoundingMode.HALF_UP);
        return average.doubleValue();
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        BigInteger totalCommission = getTotal(lastShownList);
        Person highestPerson = getHighest(lastShownList);
        Person lowestPerson = getLowest(lastShownList);
        double averageCommission = getAverage(lastShownList);
        int numContacts = getNumContacts(lastShownList);

        String statsMessage = "==== General Statistics ====\n"
                + String.format("Number of Contacts : %d\n", numContacts)
                + String.format("Total Commission   : %s\n", totalCommission.toString())
                + String.format("Highest Commission : %s\n",
                        highestPerson != null ? highestPerson.getCommission().toString() : "N/A")
                + String.format("Lowest Commission  : %s\n",
                        lowestPerson != null ? lowestPerson.getCommission().toString() : "N/A")
                + String.format("Average Commission : %.2f\n", averageCommission)
                + "============================\n";

        return new CommandResult(statsMessage);
    }
}
