package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Comparator;
import java.util.List;

import seedu.address.model.Model;
import seedu.address.model.person.Commission;
import seedu.address.model.person.Person;

/**
 * Gives the total commission of the shown list.
 */
public class SummaryCommand extends Command {

    public static final String COMMAND_WORD = "stat";

    /**
     * Calculates the total commission of the people.
     */
    public Commission getTotal(List<Person> people) {
        return people.stream()
                .map(Person::getCommission)
                .reduce(new Commission("0"), Commission::addValue);
    }

    public int getNumContacts(List<Person> people) {
        return people.size();
    }

    public Person getHighest(List<Person> people) {
        return people.stream()
                .max(Comparator.comparing(person -> person.getCommission().value))
                .orElse(null);
    }

    public Person getLowest(List<Person> people) {
        return people.stream()
                .min(Comparator.comparing(person -> person.getCommission().value))
                .orElse(null);
    }

    public double getAverage(List<Person> people) {
        BigDecimal total = new BigDecimal(getTotal(people).value);
        BigDecimal count = BigDecimal.valueOf(people.size());
        BigDecimal average = total.divide(count, MathContext.DECIMAL128);
        return average.doubleValue();
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        Commission totalCommission = getTotal(lastShownList);
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
