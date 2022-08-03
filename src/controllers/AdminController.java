package controllers;

import dataBundles.*;
import entities.Admin;
import entities.User;
import presenters.response.PasswordResetDetails;
import presenters.screenViews.AdminScreenView;
import useCases.*;

import java.util.LinkedHashMap;

/**
 * Controller class that processes the commands that an admin passes in.
 */
public class AdminController extends UserController<Admin> {

    private final AdminData adminData;
    private final PatientManager patientManager;
    private final DoctorManager doctorManager;
    private final SecretaryManager secretaryManager;
    private final AdminManager adminManager;
    private final AdminScreenView adminScreenView = new AdminScreenView();
    private final AdminController currentController = this;

    /**
     * Creates an admin controller object that handles the commands used by the current admin user.
     * @param context Context - a reference to the context object, which stores the current controller and allows for
     *                switching between controllers.
     * @param adminData AdminData - a data containing the ID and attributes of the current admin user.
     */
    public AdminController(Context context, AdminData adminData) {
        super(context, adminData, new AdminManager(context.getDatabase()), new AdminScreenView());
        this.adminData = adminData;
        this.patientManager = new PatientManager(getDatabase());
        this.secretaryManager = new SecretaryManager(getDatabase());
        this.doctorManager = new DoctorManager(getDatabase());
        this.adminManager = new AdminManager(getDatabase());

    }

    /**
     * Creates a Linked hashmap of all string representations of admin commands mapped to the method that each command calls.
     * @return LinkedHashMap<String, Command> - ordered HashMap of strings mapped to their respective admin commands.
     */
    @Override
    public LinkedHashMap<String, Command> AllCommands() {
        LinkedHashMap<String, Command> commands = new LinkedHashMap<>();
        commands.put("change clinic info", changeClinicInformation());
        commands.put("change user password", changeUserPassword());
        commands.put("delete self", deleteSelf());
        commands.put("load user management system", LoadUserManagement());
        commands.putAll(super.AllCommands());
        return commands;
    }

    private Command LoadUserManagement(){
        return (x) -> {
            changeCurrentController(new AdminUserManagementController(getContext(), currentController, adminData));
            adminScreenView.showUserManagementWelcomeMessage();
        };
    }
    private Command deleteSelf() {
        return (x) -> {
            adminManager.deleteUserByData(adminData);
            changeCurrentController(new SignInController(getContext()));
        };
    }

    private Command changeClinicInformation() {
        return (x) -> changeCurrentController(new ClinicController(getContext(), currentController));
    }

    private <T extends User> boolean changePassword(UserManager<T> manager, String name) {
        UserData<T> user = manager.getUserData(name);
        if (user == null) {
            return false;
        }
        PasswordResetDetails password = adminScreenView.getNewPasswordPrompt();
        if (!password.password().equals(password.confirmedPassword())) {
            adminScreenView.passwordMismatchError(new ContactManager(getDatabase()).getContactData(user));
        } else manager.changeUserPassword(user, password.password());
        return true;
    }

    private Command changeUserPassword() {
        return (x) -> {
            // NOTE this is can be any user not just the one using it,
            // so can't use reset password prompt presenter method
            String name = adminScreenView.getUsersName();
            if ((changePassword(patientManager, name) |
                    (changePassword(adminManager, name)) |
                    (changePassword(secretaryManager, name)) |
                    (changePassword(doctorManager, name)))){
                adminScreenView.showResetPasswordSuccessMessage();
            } else {
                adminScreenView.userDoesNotExistError(name);
            }
        };
    }
}
