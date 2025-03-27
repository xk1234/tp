package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /**
     * Displays text in a separate window with clipboard support.
     * Nothing is displayed if this is null
     **/
    private final String linkWindowText;

    /** The application should exit. */
    private final boolean exit;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, String linkWindowText, boolean exit) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.linkWindowText = linkWindowText;
        this.exit = exit;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, null, false);
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public String getLinkWindowText() {
        return linkWindowText;
    }

    public boolean hasWindowText() {
        return linkWindowText != null;
    }

    public boolean isExit() {
        return exit;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandResult)) {
            return false;
        }

        CommandResult otherCommandResult = (CommandResult) other;
        return feedbackToUser.equals(otherCommandResult.feedbackToUser)
                && Objects.equals(linkWindowText, otherCommandResult.linkWindowText)
                && exit == otherCommandResult.exit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, linkWindowText, exit);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("feedbackToUser", feedbackToUser)
                .add("linkWindowText", linkWindowText)
                .add("exit", exit)
                .toString();
    }

}
