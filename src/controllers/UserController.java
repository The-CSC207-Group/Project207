package controllers;

import dataBundles.ContactData;
import dataBundles.LogData;
import dataBundles.UserData;
import entities.User;
import presenter.entityViews.UserView;
import presenter.response.PasswordResetDetails;
import presenter.screenViews.UserScreenView;
import useCases.managers.ClinicManager;
import useCases.managers.ContactManager;
import useCases.managers.LogManager;
import useCases.managers.UserManager;

import java.util.ArrayList;
import java.util.LinkedHashMap;


/**
 * Controller class for processing commands that a user passes in.
 * @param <T> the type of user using this controller.
 */
public abstract class UserController<T extends User> extends TerminalController {

    private final UserData<T> userData;
    private final UserManager<T> userManager;
    private final UserScreenView userScreenView;
    private final UserView<UserData<T>> userView;

    /**
     * Creates a new controller for handling the state of when a user is signed in.
     *
     * @param context Context - a reference to the context object, which stores the current controller and allows for
     *                       switching between controllers.
     * @param userData UserData<T> where T extends User - a data containing the ID and attributes of the current
     *                       user.
     * @param userManager UserManager<T> where T extends User - a manager for handling the user data (is generic
     *                       depending on user type).
     * @param userScreenView UserScreenView - the current screen presenter method associated with this user.
     * @param userView UserView<UserData<T>> where T extends User - the presenter view of the user associated
     *                 with this controller
     */
    public UserController(Context context, UserData<T> userData, UserManager<T> userManager,
                          UserScreenView userScreenView, UserView<UserData<T>> userView) {
        super(context);
        this.userData = userData;
        this.userManager = userManager;
        this.userScreenView = userScreenView;
        this.userView = userView;
    }

    @Override
    public void welcomeMessage() {
        userScreenView.showWelcomeUserMessage(userData);
        super.welcomeMessage();
    }

    /**
     * Creates a Linked hashmap of all string representations of user commands mapped to the method that each
     * command calls.
     * @return LinkedHashMap<String, Command> - ordered HashMap of strings mapped to their respective user commands.
     */
    @Override
    public LinkedHashMap<String, Command> AllCommands() {
        LinkedHashMap<String, Command> commands = new LinkedHashMap<>();
        commands.put("change password", ChangePassword());
        commands.put("get logs", GetLogs());
        commands.put("contact details", ContactDetails());
        commands.put("view my info", ViewUserInformation());
        commands.put("view clinic info", ViewClinicInformation());
        commands.put("sign out", SignOut());
        commands.putAll(super.AllCommands());
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

    private Command ViewUserInformation() {
        return (x) -> userScreenView.displayUserInfo(userData, userView);
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
