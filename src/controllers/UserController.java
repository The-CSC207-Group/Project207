package controllers;

import dataBundles.ContactData;
import dataBundles.LogData;
import dataBundles.UserData;
import entities.User;
import presenter.response.PasswordResetDetails;
import presenter.screenViews.UserScreenView;
import useCases.managers.ClinicManager;
import useCases.managers.ContactManager;
import useCases.managers.LogManager;
import useCases.managers.UserManager;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Controller class for processing commands that a user passes in.
 * @param <T> the type of user using this controller.
 */
public abstract class UserController<T extends User> extends TerminalController {

    private final UserData<T> userData;
    private final UserManager<T> userManager;
    private final UserScreenView userScreenView;

    /**
     * Creates a new controller for handling the state of when a user is signed in.
     * @param context Context - a reference to the context object, which stores the current controller and allows for
     *                switching between controllers.
     * @param userData UserData<T> where T extends User - a data containing the ID and attributes of the current
     *                 user.
     * @param userManager UserManager<T> where T extends User - a manager for handling the user data (is generic
     *                    depending on user type).
     * @param userScreenView UserScreenView - the current screen presenter method associated with this user.
     */
    public UserController(Context context, UserData<T> userData, UserManager<T> userManager,
                          UserScreenView userScreenView) {
        super(context);
        this.userData = userData;
        this.userManager = userManager;
        this.userScreenView = userScreenView;
    }

    @Override
    public void welcomeMessage() {
        userScreenView.showWelcomeUserMessage(userData);
        super.welcomeMessage();
    }

    /**
     * Creates a hashmap of all string representations of user commands mapped to the method that each
     * command calls.
     * @return HashMap<String, Command> - HashMap of strings mapped to their respective user commands.
     */
    @Override
    public HashMap<String, Command> AllCommands() {
        HashMap<String, Command> commands = super.AllCommands();
        commands.put("change password", ChangePassword());
        commands.put("get logs", GetLogs());
        commands.put("contact details", ContactDetails());
        commands.put("view clinic info", ViewClinicInformation());
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
        ContactController contactController = new ContactController(getContext(), currentController, contactData);
        return (x) -> changeCurrentController(contactController);
    }

    private Command ViewClinicInformation() {
        return (x) -> {
            ClinicManager clinicManager = new ClinicManager(getDatabase());
            userScreenView.displayClinicInfo(clinicManager.clinicData());
        };
    }

    private Command SignOut(){
        return (x) -> changeCurrentController(new SignInController(getContext()));
    }

}
