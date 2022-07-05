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
    DataMapperGateway<User> database;

    public TerminalController(DataMapperGateway<User> database) {
        this.database = database;
    }

    public HashMap<String, Command> AllCommands() {
        HashMap<String, Command> x =  new HashMap<>();
        x.put("help", new Help());
        return x;
    }

    class Help implements Command {

        @Override
        public boolean execute(ArrayList<String> args) {
            List<String> helpList = new ArrayList<>(AllCommands().keySet());
            presenter.infoMessage("List of available commands:");
            for (String i: helpList){
                presenter.infoMessage(i);
            }
            return true;
        }
    }

    void ProcessCommands() {
        database.save();

        boolean continued = true;
        while (continued) {
            String command = presenter.promptPopup(">>> ");
            if (!AllCommands().containsKey(command)){
                presenter.errorMessage("Invalid command: " + command);
            } else {
                continued = AllCommands().get(command).execute(new ArrayList<>());
                database.save();
            }
        }
    }

    abstract void WelcomeMessage();

    public void run(){
        WelcomeMessage();
        ProcessCommands();
    }
}
