package controllers;

import dataBundles.*;
import entities.Admin;
import entities.User;
import presenters.response.PasswordResetDetails;
import presenters.response.UserCredentials;
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
    private final LogManager logManager;
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
        this.logManager = new LogManager(getDatabase());
    }

    /**
     * Creates a Linked hashmap of all string representations of admin commands mapped to the method that each command calls.
     * @return LinkedHashMap<String, Command> - ordered HashMap of strings mapped to their respective admin commands.
     */
    @Override
    public LinkedHashMap<String, Command> AllCommands() {
        LinkedHashMap<String, Command> commands = new LinkedHashMap<>();
        commands.put("create admin", CreateAdmin());
        commands.put("create secretary", CreateSecretary());
        commands.put("create doctor", CreateDoctor());
        commands.put("create patient", CreatePatient());
        commands.put("change clinic info", changeClinicInformation());
        commands.put("change user password", changeUserPassword());
        commands.put("delete user", deleteUser());
        commands.put("delete self", deleteSelf());

        commands.putAll(super.AllCommands());
        return commands;
    }

    private Command deleteSelf() {
        return (x) -> {
            adminManager.deleteUserByData(adminData);
            changeCurrentController(new SignInController(getContext()));
        };
    }

    private Command CreateSecretary() {
        return (x) -> {
            UserCredentials userCredentials = adminScreenView.registerSecretaryPrompt();
            SecretaryData secretary = secretaryManager.createSecretary(userCredentials.username(),
                    userCredentials.password());
            logManager.addLog(adminData, "created secretary: " + userCredentials.username());
            displaySuccessOnCreateAccount(secretary);
        };
    }

    private Command CreateDoctor() {
        return (x) -> {
            UserCredentials userCredentials = adminScreenView.registerDoctorPrompt();
            DoctorData doctor = doctorManager.createDoctor(userCredentials.username(), userCredentials.password());
            logManager.addLog(adminData, "created doctor: " + userCredentials.username());
            displaySuccessOnCreateAccount(doctor);
        };
    }

    private Command CreateAdmin() {
        return (x) -> {
            UserCredentials userCredentials = adminScreenView.registerAdminPrompt();
            AdminData admin = adminManager.createAdmin(userCredentials.username(), userCredentials.password());
            logManager.addLog(adminData, "created admin: " + userCredentials.username());
            displaySuccessOnCreateAccount(admin);
        };
    }

    private Command CreatePatient() {
        return (x) -> {
            UserCredentials userCredentials = adminScreenView.registerPatientPrompt();
            PatientData patient = patientManager.createPatient(userCredentials.username(), userCredentials.password());
            logManager.addLog(adminData, "created patient: " + userCredentials.username());
            displaySuccessOnCreateAccount(patient);
        };
    }

    private void displaySuccessOnCreateAccount(UserData<?> user) {
        if (user == null) {
            adminScreenView.showFailedToRegisterUserError();
        } else {
            adminScreenView.showRegisterUserSuccess();
        }
    }

    private boolean deleteUserHelper(String username) {
        if (patientManager.deleteUser(username)) {
            return true;
        } else if (doctorManager.deleteUser(username)) {
            return true;
        } else if (secretaryManager.deleteUser(username)) {
            return true;
        } else return adminManager.deleteUser(username);
    }

    private Command deleteUser() {
        return (x) -> {
            String username = adminScreenView.deleteUserPrompt();
            if (deleteUserHelper(username)) {
                adminScreenView.showDeleteUserSuccess();
                if (username.equals(adminData.getUsername())) {
                    changeCurrentController(new SignInController(getContext()));
                }
                logManager.addLog(adminData, "deleted user: " + username);
            } else {
                adminScreenView.showFailedToDeleteUserError();
            }
        };
    }

    private Command changeClinicInformation() {
        return (x) -> changeCurrentController(new ClinicController(getContext(), currentController, adminData));
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
            String username = adminScreenView.getUsersName();
            if ((changePassword(patientManager, username) |
                    (changePassword(adminManager, username)) |
                    (changePassword(secretaryManager, username)) |
                    (changePassword(doctorManager, username)))){
                logManager.addLog(adminData, "changed user " + username + "'s password");
                adminScreenView.showResetPasswordSuccessMessage();
            } else {
                adminScreenView.userDoesNotExistError(username);
            }
        };
    }

}
