package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {
    /**
     * Represents the data for a clickable text element that links to a specified URL.
     * The text is displayed in a separate window with clipboard support.
     * If the URL is {@code null}, nothing will be displayed.
     *
     * @param url  the URL to which the text links; if {@code null}, the link is not displayed
     * @param text the text to be displayed and copied to the clipboard
     */
    public record LinkedText(String url, String text) {}

    private final String feedbackToUser;

    /**
     * The text to be displayed in a separate window with clipboard support.
     * If {@code linkWindowText} is {@code null}, nothing will be displayed.
     */
    private final LinkedText linkWindowText;

    /** The application should exit. */
    private final boolean exit;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, LinkedText linkWindowText, boolean exit) {
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

    public LinkedText getLinkWindowText() {
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
