package controllers;

import database.DataMapperGateway;
import entities.User;
import useCases.AdminManager;
import useCases.SystemManager;
import useCases.UserManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class ApplicationController extends TerminalController {

    SystemManager systemManager;

    public ApplicationController(DataMapperGateway<User> database) {
        super(database);
        this.systemManager = new SystemManager(database);
    }

    @Override
    public HashMap<String, Command> AllCommands() {
        HashMap<String, Command> commands = super.AllCommands();
        commands.put("sign in", new SignInCommand());
        commands.put("register", new RegisterCommand());
        commands.put("exit", new ExitCommand());
        return commands;
    }

    @Override
    public void WelcomeMessage() {
        presenter.infoMessage("\nWelcome to the program! Please sign in or create an account." +
                              "\nType 'help' to see a list of all possible commands.");
    }

    class SignInCommand implements Command {

        @Override
        public boolean execute(ArrayList<String> args) {
            List<String> fields = Arrays.asList("username", "password");
            HashMap<String, String> responses = presenter.promptPopup(fields);
            String username = responses.get(fields.get(0));
            String password = responses.get(fields.get(1));

            if (systemManager.canSignIn(username, password)) {
                presenter.successMessage("Successfully signed in");
                systemManager.addUserLog(username);
                if (systemManager.isUserAdmin(username)) {
                    new AdminController(database, new AdminManager(username, database)).run();
                } else {
                    new UserController(database, new UserManager(username, database)).run();
                }
            } else {
                presenter.errorMessage("Failed to sign in");
            }
            return true;
        }
    }

    class RegisterCommand implements Command {

        @Override
        public boolean execute(ArrayList<String> args) {
            List<String> fields = Arrays.asList("username", "password");
            HashMap<String, String> responses = presenter.promptPopup(fields);
            String username = responses.get(fields.get(0));
            String password = responses.get(fields.get(1));

            if (systemManager.createUser(username, password)) {
                presenter.successMessage("User " + username + " has been created");
            } else {
                presenter.errorMessage("Failed to create user: Username already exists");
            }

            return true;
        }
    }

    class ExitCommand implements Command {

        @Override
        public boolean execute(ArrayList<String> args) {
            return false;
        }
    }

}
