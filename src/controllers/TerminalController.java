package controllers;

import database.Database;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.text.similarity.LevenshteinDistance;
import presenters.screenViews.TerminalScreenView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Controller class for processing the commands passed in to the terminal.
 */
abstract public class TerminalController {

    private final TerminalScreenView terminalScreenView = new TerminalScreenView();
    private final Context context;

    /**
     * Creates a new controller for handling the state of the program where commands are being passed into the terminal.
     *
     * @param context Context - a reference to the context object, which stores the current controller and allows for
     *                switching between controllers.
     */
    public TerminalController(Context context) {
        this.context = context;
    }

    /**
     * Displays a welcome message to the user.
     */
    public void welcomeMessage() {
        terminalScreenView.showHelpPrompt();
    }

    /**
     * Gets the context (necessary for the state command).
     *
     * @return Context - reference to the context object, which stores the current controller and allows for switching
     * between controllers.
     */
    public Context getContext() {
        return context;
    }

    /**
     * Changes the current controller in the context to new controller.
     *
     * @param newController TerminalController - the controller we are switching to.
     */
    public void changeCurrentController(TerminalController newController) {
        context.changeController(newController);
    }

    /**
     * Returns the database.
     *
     * @return Database - collection of all the entity databases of the program.
     */
    public Database getDatabase() {
        return context.getDatabase();
    }

    /**
     * Creates a Linked hashmap of the string representations of commands mapped to the method that each command calls.
     *
     * @return LinkedHashMap<String, Command> - ordered HashMap of strings mapped to their respective commands.
     */
    public LinkedHashMap<String, Command> AllCommands() {
        LinkedHashMap<String, Command> commands = new LinkedHashMap<>();
        commands.put("help", Help());
        commands.put("exit", Exit());
        return commands;
    }

    /**
     * Runs the process of the related controller.
     */
    public void run() {
        ProcessCommands();
    }

    private void exit() {
        context.exit();
    }

    private String processSpelling(String inputtedCommand) {
        int maxDistance = (int) Math.ceil(inputtedCommand.length() / 6f);
        LevenshteinDistance levenshteinDistance = new LevenshteinDistance();
        int currentMin = Integer.MAX_VALUE;
        String newCommand = null;
        for (String command : AllCommands().keySet()) {
            int dist = levenshteinDistance.apply(command, inputtedCommand);
            if (dist <= maxDistance && dist < currentMin) {
                newCommand = command;
                currentMin = dist;
            }
        }
        return newCommand;
    }

    private Integer processNumber(String inputtedCommand) {
        try {
            int number = NumberUtils.createInteger(inputtedCommand) - 1;
            if (number <= AllCommands().size() - 1 & number >= 0) {
                return number;
            }
        } catch (NumberFormatException ignored) {
        }
        return null;
    }

    private String getCommand(String inputtedCommand) {
        if (AllCommands().containsKey(inputtedCommand)) {
            return inputtedCommand;
        }

        Integer numberCommand = processNumber(inputtedCommand);
        if (numberCommand != null) {
            return new ArrayList<>(AllCommands().keySet()).get(numberCommand);
        }

        String correctSpelling = processSpelling(inputtedCommand);
        if (correctSpelling != null && terminalScreenView.showCorrectSpellingPrompt(correctSpelling)) {
            return correctSpelling;
        }

        return null;
    }

    private void ProcessCommands() {
        getDatabase().save();
        String command = terminalScreenView.showCommandPrompt();

        if (command.isBlank()) {
            return;
        }

        String correctedCommand = getCommand(command);
        if (correctedCommand != null) {
            AllCommands().get(correctedCommand).execute(new ArrayList<>());
            getDatabase().save();
        } else {
            terminalScreenView.showInvalidCommandError(command);
        }
    }

    /**
     * Command for exiting the program.
     */
    protected Command Exit() {
        return (x) -> exit();
    }

    private Command Help() {
        return (x) -> {
            List<String> helpCommands = new ArrayList<>(AllCommands().keySet());
            terminalScreenView.showHelpView(helpCommands);
        };
    }

}
