package controllers;

import dataBundles.ContactData;
import dataBundles.LogData;
import dataBundles.UserData;
import entities.User;
import presenter.response.PasswordResetDetails;
import presenter.screenViews.UserScreenView;
import useCases.managers.ContactManager;
import useCases.managers.LogManager;
import useCases.managers.UserManager;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class UserController<T extends User> extends TerminalController {
    private final UserData<T> userData;
    private final UserManager<T> userManager;
    private final UserScreenView userScreenView;

    /**
     * creates a generic user controller
     * @param context the context (necessary for the state pattern)
     * @param userData the current users info
     * @param userManager a manager for handling  the user data (is generic depending on user type)
     * @param userScreenView the current scrren view
     */
    public UserController(Context context, UserData<T> userData, UserManager<T> userManager,
                          UserScreenView userScreenView) {
        super(context);
        this.userData = userData;
        this.userManager = userManager;
        this.userScreenView = userScreenView;
    }


    @Override
    /**
     * adds the commands associated with user
     */
    public HashMap<String, Command> AllCommands() {
        HashMap<String, Command> commands = new HashMap<>();
        commands.put("change password", ChangePassword());
        commands.put("get logs", GetLogs());
        commands.put("contact details", ContactDetails());
        commands.put("sign out", SignOut());
        return commands;
    }

    private Command ChangePassword() {
        return (x) -> {
            PasswordResetDetails passwordResetDetails = userScreenView.resetPasswordPrompt();
            if (passwordResetDetails.password().equals(passwordResetDetails.confirmedPassword())) {
                userManager.changeUserPassword(userData, passwordResetDetails.password());
                userScreenView.showResetPasswordSuccessMessage();
            } else {
                userScreenView.showResetPasswordMismatchError();
            }
        };
    }

    private Command GetLogs() {
        LogManager logManager = new LogManager(getDatabase());
        return (x) -> {
            ArrayList<LogData> logs = logManager.getUserLogs(userData);
            userScreenView.viewUserLogs(logs);
        };
    }

    private Command ContactDetails() {
        UserController<?> currentController = this;
        ContactManager contactManager = new ContactManager(getDatabase());
        ContactData contactData = contactManager.getContactData(userData);
        ContactController contactController = new ContactController(getContext(), currentController, contactData,
                userScreenView);
        return (x) -> {
            changeCurrentController(contactController);
        };
    }

    private Command SignOut(){
        return (x) -> {
            changeCurrentController(new SignInController(getContext()));
        };
    }
}
