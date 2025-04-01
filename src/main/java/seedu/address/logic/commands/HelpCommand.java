package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";

    static final String URL = "https://ay2425s2-cs2103t-t14-4.github.io/tp/UserGuide.html";

    public static final CommandResult.LinkedText HELP_TEXT = new CommandResult.LinkedText(
            URL, String.format("Refer to the user guide: %s", URL));

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(SHOWING_HELP_MESSAGE, HELP_TEXT, false);
    }
}
