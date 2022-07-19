package presenter.screenViews;

import java.util.List;

public class TerminalScreenView extends ScreenView {

    public void showHelpView(List<String> helpCommands) {
        infoMessage("Available Commands:");
        for (String command : helpCommands) {
            infoMessage(command);
        }
    }

    public String showCommandPrompt() {
        return input(">>> ");
    }
}
