package controllers;

import database.Database;
import presenter.screenViews.TerminalScreenView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Controller class for processing the commands passed in to the terminal.
 */
abstract public class TerminalController {

    private final TerminalScreenView terminalScreenView = new TerminalScreenView();
    private final Context context;

    /**
     * Creates a new controller for handling the state of the program where commands are being passed into the terminal.
     * @param context Context - a reference to the context object, which stores the current controller and allows for
     *                switching between controllers.
     */
    public TerminalController(Context context) {
        this.context = context;
    }

    public abstract void welcomeMessage();

    /**
     * Gets the context (necessary for the state command).
     * @return Context - reference to the context object, which stores the current controller and allows for switching
     * between controllers.
     */
    public Context getContext() {
        return context;
    }

    /**
     * Changes the current controller in the context to new controller.
     * @param newController TerminalController - the controller we are switching to.
     */
    public void changeCurrentController(TerminalController newController){
        context.changeController(newController);
    }

    /**
     * Returns the database.
     * @return Database - collection of all the entity databases of the program.
     */
    public Database getDatabase() {
        return context.getDatabase();
    }

    /**
     * Creates a hashmap of the string representations of commands mapped to the method that each command calls.
     * @return HashMap<String, Command> - HashMap of strings mapped to their respective commands.
     */
    public HashMap<String, Command> AllCommands() {
        HashMap<String, Command> commands = new HashMap<>();
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

    private void ProcessCommands() {
        getDatabase().save();
        String command = terminalScreenView.showCommandPrompt();
        if (!AllCommands().containsKey(command)) {
            terminalScreenView.showInvalidCommandError(command);
        } else {
            AllCommands().get(command).execute(new ArrayList<>());
            getDatabase().save();
        }
    }

    protected Command back(TerminalController previousController){
        return (x) -> changeCurrentController(previousController);
    }


    private Command Help() {
        return (x) -> {
            List<String> helpCommands = new ArrayList<>(AllCommands().keySet());
            terminalScreenView.showHelpView(helpCommands);
        };
    }

    protected Command Exit() {
        return (x) -> exit();
    }

}
