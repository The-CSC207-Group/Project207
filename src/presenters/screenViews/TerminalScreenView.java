package presenters.screenViews;

import java.util.List;

/**
 * Presenter for the terminal.
 */
public class TerminalScreenView extends ScreenView {

    /**
     * Shows list of available command.
     * @param helpCommands List of strings representing command
     */
    public void showHelpView(List<String> helpCommands) {
        infoMessage("Available Commands:");
        for (int i = 0; i < helpCommands.size(); i++) {
            infoMessage((i + 1) + ". " + helpCommands.get(i));
        }
    }

    /**
     * Ask user to input command.
     * @return string representing stripped input.
     */
    public String showCommandPrompt() {
        return input(">>> ");
    }

    /**
     * Display error when the command user types is not in command list.
     * @param command A command represented as a string.
     */
    public void showInvalidCommandError(String command) {
        errorMessage("Invalid command: " + command);
    }

}
