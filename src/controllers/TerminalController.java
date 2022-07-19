package controllers;

import database.Database;
import presenter.screenViews.TerminalScreenView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


abstract public class TerminalController {

    TerminalScreenView terminalScreenView = new TerminalScreenView();
    Context context;

    public TerminalController(Context parent) {
        this.context = parent;
    }

    Context getContext() {
        return context;
    }
    void changeCurrentController(TerminalController newController){
        context.changeController(newController);
    }

    Database getDatabase() {
        return context.database;
    }

    void exit() {
        context.exit();
    }

    public HashMap<String, Command> AllCommands() {
        HashMap<String, Command> x = new HashMap<>();
        x.put("help", Help());
        x.put("exit", Exit());
        return x;
    }

    void ProcessCommands() {
        getDatabase().save();
        String command = terminalScreenView.showCommandPrompt();
        if (!AllCommands().containsKey(command)) {
            terminalScreenView.showInvalidCommandError(command);
        } else {
            AllCommands().get(command).execute(new ArrayList<>());
            getDatabase().save();
        }
    }

    public void run() {
        ProcessCommands();
    }

    protected Command back(TerminalController prev){
        return (x) -> {
            changeCurrentController(prev);
        };
    }

    protected Command signOut(){
        return (x) -> {
            changeCurrentController(new SignInController(context));
        };
    }
    Command notImplemented(){
        return (x) -> {
          throw new RuntimeException();
        };
    }

    protected Command Help() {
        return (x) -> {
            List<String> helpCommands = new ArrayList<>(AllCommands().keySet());
            terminalScreenView.showHelpView(helpCommands);
        };
    }

    protected Command Exit() {
        return (x) -> {
            exit();
        };
    }
}
