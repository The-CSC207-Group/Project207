package presenter.screenViews;

import dataBundles.ClinicData;
import presenter.entityViews.ClinicView;

import java.util.List;

public class TerminalScreenView extends ScreenView {

    /**
     * Shows list of available command.
     * @param helpCommands List of strings representing command
     */
    public void showHelpView(List<String> helpCommands) {
        infoMessage("Available Commands:");
        for (String command : helpCommands) {
            infoMessage(command);
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
