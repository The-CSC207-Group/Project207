package controllers;

import database.DataMapperGateway;
import entities.User;
import presenter.ApplicationPresenter;
import presenter.TerminalPresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


abstract class TerminalController {

    ApplicationPresenter presenter = new TerminalPresenter();
    Controller context;

    public TerminalController(Controller parent) {
        this.context = parent;
    }

    Controller getContext() {
        return context;
    }
    void changeCurrentController(TerminalController next){
        context.changeController(next);
    }

    DataMapperGateway<User> getDatabase() {
        return context.database;
    }

    void exit() {
        context.exit();
    }

    public HashMap<String, Command> AllCommands() {
        HashMap<String, Command> x = new HashMap<>();
        x.put("help", new Help());
        x.put("exit", new Exit());
        return x;
    }

    void ProcessCommands() {
        getDatabase().save();

        String command = presenter.promptPopup(">>> ");
        if (!AllCommands().containsKey(command)) {
            presenter.errorMessage("Invalid command: " + command);
        } else {
            AllCommands().get(command).execute(new ArrayList<>());
            getDatabase().save();
        }
    }

    abstract void WelcomeMessage();

    public void run() {
        WelcomeMessage();
        ProcessCommands();
    }

    class Help implements Command {

        @Override
        public boolean execute(ArrayList<String> args) {
            List<String> helpList = new ArrayList<>(AllCommands().keySet());
            presenter.infoMessage("List of available commands:");
            for (String i : helpList) {
                presenter.infoMessage(i);
            }
            return true;
        }
    }

    class Exit implements Command {

        @Override
        public boolean execute(ArrayList<String> args) {
            exit();
            return true;
        }
    }
}