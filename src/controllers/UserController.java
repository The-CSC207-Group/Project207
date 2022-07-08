package controllers;

import useCases.UserManager;

import java.util.*;


public class UserController extends TerminalController {

    private final UserManager currentUserManager;

    public UserController(Context parent, UserManager currentUser) {
        super(parent);
        this.currentUserManager = currentUser;
    }

    @Override
    public HashMap<String, Command> AllCommands() {
        HashMap<String, Command> commands = super.AllCommands();
        commands.put("sign out", new SignOutCommand());
        commands.put("history", new ViewHistoryCommand());
        commands.put("delete account", new DeleteAccountCommand());
        commands.put("change password", new ChangePasswordCommand());
        return commands;
    }

    @Override
    public void WelcomeMessage() {
        presenter.infoMessage("Welcome " + currentUserManager.getCurrentUser().getUsername());
    }

    class SignOutCommand implements Command {

        @Override
        public boolean execute(ArrayList<String> args) {
            presenter.successMessage("Successfully signed out");
            currentUserManager.signOut();
            return false;
        }
    }

    class DeleteAccountCommand implements Command {

        @Override
        public boolean execute(ArrayList<String> args) {
            presenter.successMessage("Your account has been deleted");
            currentUserManager.deleteCurrentUser();
            return false;
        }
    }

    class ViewHistoryCommand implements Command {

        @Override
        public boolean execute(ArrayList<String> args) {
            for (Object log : currentUserManager.getCurrentUserLogs()) {
                presenter.infoMessage(log.toString());
            }
            return true;
        }
    }

    class ChangePasswordCommand implements Command {

        @Override
        public boolean execute(ArrayList<String> args) {
            List<String> fields = Arrays.asList("new password", "new password again");
            HashMap<String, String> responses = presenter.promptPopup(fields);
            String tempPassword = responses.get(fields.get(0));
            String verifiedPassword = responses.get(fields.get(1));

            if (verifiedPassword.equals(tempPassword)) {
                currentUserManager.changeCurrentUserPassword(tempPassword);
                presenter.successMessage("Password has been changed");
            } else {
                presenter.errorMessage("Failed to change password");
            }
            return true;
        }
    }
}
