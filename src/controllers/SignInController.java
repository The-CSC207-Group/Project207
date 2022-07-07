package controllers;

import useCases.SystemManager;
import useCases.UserManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class SignInController extends TerminalController {

    SystemManager systemManager;

    public SignInController(Context parent) {
        super(parent);
        this.systemManager = new SystemManager(getDatabase());
    }

    @Override
    public HashMap<String, Command> AllCommands() {
        HashMap<String, Command> commands = super.AllCommands();
        commands.put("sign in", new SignInCommand());
        commands.put("register", new RegisterCommand());
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
//                    new AdminController(new AdminManager(username, getDatabase())).run();
                } else {
                    changeCurrentController(new UserController(getContext(), new UserManager(username, getDatabase())));
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
    class BackCommand implements Command{

        @Override
        public boolean execute(ArrayList<String> args) {
            changeCurrentController(new SignInController(getContext()));
            return true;
        }
    }



}
