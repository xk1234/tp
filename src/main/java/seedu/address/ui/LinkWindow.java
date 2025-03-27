package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.CommandResult;

/**
 * Controller for a window with link
 */
public class LinkWindow extends UiPart<Stage> {
    private static final Logger logger = LogsCenter.getLogger(LinkWindow.class);
    private static final String FXML = "LinkWindow.fxml";

    private CommandResult.LinkedText linkedText;

    @FXML
    private Button copyButton;

    @FXML
    private Label label;

    /**
     * Creates a new LinkWindow.
     *
     * @param root Stage to use as the root of the LinkWindow.
     */
    public LinkWindow(Stage root) {
        super(FXML, root);
    }

    /**
     * Creates a new LinkWindow.
     */
    public LinkWindow() {
        this(new Stage());
    }

    /**
     * Shows the help window.
     * @throws IllegalStateException
     *     <ul>
     *         <li>
     *             if this method is called on a thread other than the JavaFX Application Thread.
     *         </li>
     *         <li>
     *             if this method is called during animation or layout processing.
     *         </li>
     *         <li>
     *             if this method is called on the primary stage.
     *         </li>
     *         <li>
     *             if {@code dialogStage} is already showing.
     *         </li>
     *     </ul>
     */
    public void show() {
        logger.fine("Showing help page about the application.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the help window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the help window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Copies the URL to the user guide to the clipboard.
     */
    @FXML
    private void copyUrl() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(linkedText.url());
        clipboard.setContent(url);
    }

    /**
     * Updates the displayed linked text and sets the label accordingly.
     *
     * @param linkedText the new linked text to be displayed
     */
    public void updateLinkedText(CommandResult.LinkedText linkedText) {
        this.linkedText = linkedText;
        label.setText(this.linkedText.text());
    }
}
