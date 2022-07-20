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

    private TerminalScreenView terminalScreenView = new TerminalScreenView();
    private Context context;

    /**
     * Creates a new controller for handling the state of the program where commands are being passed into the terminal.
     * @param context a reference to the context object, which stores the current controller and allows for switching
     *                between controllers.
     */
    public TerminalController(Context context) {
        this.context = context;
    }

    /**
     * Gets the context (necessary for the state command).
     * @return Context of the current program.
     */
    Context getContext() {
        return context;
    }

    /**
     * Changes the current controller in the context to new controller.
     * @param newController the controller we are switching to.
     */
    void changeCurrentController(TerminalController newController){
        context.changeController(newController);
    }

    /**
     * Returns the database.
     * @return Database of the program.
     */
    Database getDatabase() {
        return context.database;
    }

    /**
     * Exits the program.
     */
    private void exit() {
        context.exit();
    }

    /**
     * Creates a hashmap of the string representations of commands mapped to the method that each command calls.
     * @return HashMap of strings mapped to their respective commands.
     */
    public HashMap<String, Command> AllCommands() {
        HashMap<String, Command> commands = new HashMap<>();
        commands.put("help", Help());
        commands.put("exit", Exit());
        return commands;
    }

    /**
     * Prompts the user to enter a single command and processes that command.
     */
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

    /**
     * Runs the process of the related controller.
     */
    public void run() {
        ProcessCommands();
    }

    /**
     * Hands control of the program to the previous controller.
     * @param previousController the object of the previous controller.
     * @return Command that goes back to the previous controller.
     */
    protected Command back(TerminalController previousController){
        return (x) -> {
            changeCurrentController(previousController);
        };
    }


    /**
     * Returns the command that displays a list of options that the user can use.
     * @return presenter output of help commands.
     */
    private Command Help() {
        return (x) -> {
            List<String> helpCommands = new ArrayList<>(AllCommands().keySet());
            terminalScreenView.showHelpView(helpCommands);
        };
    }

    /**
     * Returns the exit command which allows you to exit the program.
     * @return exit method call.
     */
    protected Command Exit() {
        return (x) -> {
            exit();
        };
    }
}
