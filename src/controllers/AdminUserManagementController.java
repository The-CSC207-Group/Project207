package controllers;

import dataBundles.*;
import entities.User;
import presenters.response.PasswordResetDetails;
import presenters.response.UserCredentials;
import presenters.screenViews.AdminScreenView;
import useCases.*;

import java.util.LinkedHashMap;

/**
 * Controller class that handles the creation and deletion of users by an admin.
 */
public class AdminUserManagementController extends MenuController {

    private final AdminData adminData;
    private final AdminScreenView adminScreenView = new AdminScreenView();
    private final PatientManager patientManager;
    private final DoctorManager doctorManager;
    private final SecretaryManager secretaryManager;
    private final AdminManager adminManager;

    /**
     * Creates a new controller for handling the state of the program when an admin wants to create/delete users
     *
     * @param context            Context - a reference to the context object, which stores the current controller and
     *                           allows for switching between controllers.
     * @param previousController AdminController - stores the previous controller, allows you to easily go back to it
     *                           via the back command.
     * @param adminData          AdminData - a data containing the ID and attributes of the current loaded admin user.
     */
    public AdminUserManagementController(Context context, AdminController previousController, AdminData adminData) {
        super(context, previousController);
        this.adminData = adminData;
        this.patientManager = new PatientManager(getDatabase());
        this.secretaryManager = new SecretaryManager(getDatabase());
        this.doctorManager = new DoctorManager(getDatabase());
        this.adminManager = new AdminManager(getDatabase());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void welcomeMessage() {
        adminScreenView.showUserManagementWelcomeMessage();
        super.welcomeMessage();
    }

    /**
     * Creates a Linked hashmap of all string representations of AdminUserManagement commands mapped to the
     * method that each command calls.
     *
     * @return LinkedHashMap<String, Command> - ordered HashMap of strings mapped to their respective
     * AdminUserManagement commands.
     */
    @Override
    public LinkedHashMap<String, Command> allCommands() {
        LinkedHashMap<String, Command> commands = new LinkedHashMap<>();
        commands.put("create admin", CreateAdmin());
        commands.put("create secretary", CreateSecretary());
        commands.put("create doctor", CreateDoctor());
        commands.put("create patient", CreatePatient());
        commands.put("delete user", DeleteUser());
        commands.put("change user password", ChangeUserPassword());

        commands.putAll(super.allCommands());
        return commands;
    }

    private Command CreateAdmin() {
        return (x) -> {
            try {
                UserCredentials userCred = adminScreenView.registerAdminPrompt();
                AdminData admin = adminManager.createAdmin(userCred.username(), userCred.password());
                displaySuccessOnCreateAccount(admin, "admin");
            } catch (IllegalArgumentException iae) {
                adminScreenView.showIncorrectFormatError("admin");
            }
        };
    }

    private Command CreateDoctor() {
        DoctorManager doctorManager = new DoctorManager(getDatabase());
        return (x) -> {
            try {
                UserCredentials userCred = adminScreenView.registerDoctorPrompt();
                DoctorData doctor = doctorManager.createDoctor(userCred.username(), userCred.password());
                displaySuccessOnCreateAccount(doctor, "doctor");
            } catch (IllegalArgumentException iae) {
                adminScreenView.showIncorrectFormatError("doctor");
            }
        };
    }

    private Command CreatePatient() {
        PatientManager patientManager = new PatientManager(getDatabase());
        return (x) -> {
            try {
                UserCredentials userCred = adminScreenView.registerPatientPrompt();
                PatientData patient = patientManager.createPatient(userCred.username(), userCred.password());
                displaySuccessOnCreateAccount(patient, "patient");
            } catch (IllegalArgumentException iae) {
                adminScreenView.showIncorrectFormatError("patient");
            }
        };
    }

    private Command CreateSecretary() {
        SecretaryManager secretaryManager = new SecretaryManager(getDatabase());
        return (x) -> {
            try {
                UserCredentials c = adminScreenView.registerSecretaryPrompt();
                SecretaryData secretary = secretaryManager.createSecretary(c.username(), c.password());
                displaySuccessOnCreateAccount(secretary, "secretary");
            } catch (IllegalArgumentException iae) {
                adminScreenView.showIncorrectFormatError("secretary");
            }
        };
    }

    private void displaySuccessOnCreateAccount(UserData<?> user, String userType) {
        if (user == null) {
            adminScreenView.showUsernameInUseError(userType);
        } else {
            adminScreenView.showRegisterUserSuccess(userType);
        }
    }

    private Command DeleteUser() {
        return (x) -> {
            String user = adminScreenView.deleteUserPrompt();
            if (deleteUserHelper(user)) {
                adminScreenView.showDeleteUserSuccess();
                if (user.equals(adminData.getUsername())) {
                    changeCurrentController(new SignInController(getContext()));
                }
            } else {
                adminScreenView.showFailedToDeleteUserError();
            }
        };
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

    private Command ChangeUserPassword() {
        return (x) -> {
            // NOTE this is can be any user not just the one using it,
            // so can't use reset password prompt presenter method
            String name = adminScreenView.getUsersName();
            if ((changePassword(patientManager, name) ||
                    (changePassword(adminManager, name)) ||
                    (changePassword(secretaryManager, name)) ||
                    (changePassword(doctorManager, name)))) {
                adminScreenView.showResetPasswordSuccessMessage();
            } else {
                adminScreenView.userDoesNotExistError(name);
            }
        };
    }

    private boolean deleteUserHelper(String username) {
        return patientManager.deleteUser(username) ||
                doctorManager.deleteUser(username) ||
                secretaryManager.deleteUser(username) ||
                adminManager.deleteUser(username);
    }
}
