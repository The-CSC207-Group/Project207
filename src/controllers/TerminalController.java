package controllers;

import database.Database;
import presenter.screenViews.TerminalScreenView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


abstract public class TerminalController {

    private TerminalScreenView terminalScreenView = new TerminalScreenView();
    private Context context;

    /**
     * creates a new controller
     * @param parent the backreference to the context (neccessary for the state command)
     */
    public TerminalController(Context parent) {
        this.context = parent;
    }

    /**
     * gets the context (neccesary for the state command)
     * @return context
     */
    Context getContext() {
        return context;
    }

    /**
     * changes the current controller in the context to new controller
     * @param newController the controller we are switching to
     */
    void changeCurrentController(TerminalController newController){
        context.changeController(newController);
    }

    /**
     * returns the database
     * @return database of the program
     */
    Database getDatabase() {
        return context.database;
    }

    private void exit() {
        context.exit();
    }

    /**
     * a Hashmap of commands that can be invoked through their equivalent strings.
     * @return HashMap from strings to Commands
     */
    public HashMap<String, Command> AllCommands() {
        HashMap<String, Command> commands = new HashMap<>();
        commands.put("help", Help());
        commands.put("exit", Exit());
        return commands;
    }

    /**
     * processes a single command
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
     * runs the related controller
     */
    public void run() {
        ProcessCommands();
    }

    /**
     * hands control of the program to prev
     * @param prev previus controller
     * @return Command that goes back
     */
    protected Command back(TerminalController prev){
        return (x) -> {
            changeCurrentController(prev);
        };
    }


    /**
     * returns the command that displays a list of options that the user can use
     * @return help command
     */
    private Command Help() {
        return (x) -> {
            List<String> helpCommands = new ArrayList<>(AllCommands().keySet());
            terminalScreenView.showHelpView(helpCommands);
        };
    }

    /**
     * returns the exit a Command which allows you to exit the program
     * @return
     */
    protected Command Exit() {
        return (x) -> {
            exit();
        };
    }
}
