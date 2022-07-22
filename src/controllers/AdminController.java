package controllers;

import dataBundles.*;
import entities.Admin;
import entities.User;
import presenter.response.PasswordResetDetails;
import presenter.response.UserCredentials;
import presenter.screenViews.AdminScreenView;
import useCases.managers.*;

import java.util.HashMap;

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
     * @param adminData AdminData - a data bundle containing the ID and attributes of the current admin user.
     */
    public AdminController(Context context, AdminData adminData) {
        super(context, adminData, new AdminManager(context.getDatabase()), new AdminScreenView());
        this.adminData = adminData;
        this.patientManager = new PatientManager(getDatabase());
        secretaryManager = new SecretaryManager(getDatabase());
        doctorManager = new DoctorManager(getDatabase());
        adminManager = new AdminManager(getDatabase());

    }

    /**
     * Creates a hashmap of all string representations of admin commands mapped to the method that each command calls.
     * @return HashMap<String, Command> - HashMap of strings mapped to their respective admin commands.
     */
    @Override
    public HashMap<String, Command> AllCommands() {
        HashMap<String, Command> commands = super.AllCommands();
        commands.put("create admin", CreateAdmin());
        commands.put("create secretary", CreateSecretary());
        commands.put("create doctor", CreateDoctor());
        commands.put("create patient", CreatePatient());
        commands.put("change clinic info", changeClinicInformation());
        commands.put("change user password", changeUserPassword());
        commands.put("delete user", deleteUser());
        commands.put("delete self", deleteSelf());
        return commands;
    }

    private Command deleteSelf() {
        return (x) -> {
            adminManager.deleteUserByData(adminData);
            changeCurrentController(new SignInController(getContext()));
        };
    }

    private Command CreateSecretary() {
        SecretaryManager secretaryManager = new SecretaryManager(getDatabase());
        return (x) -> {
            UserCredentials c = adminScreenView.registerSecretaryPrompt();
            SecretaryData secretary = secretaryManager.createSecretary(c.username(), c.password());
            displaySuccessOnCreateAccount(secretary);
        };
    }

    private Command CreateDoctor() {
        DoctorManager doctorManager = new DoctorManager(getDatabase());
        return (x) -> {
            UserCredentials userCred = adminScreenView.registerDoctorPrompt();
            DoctorData doctor = doctorManager.createDoctor(userCred.username(), userCred.password());
            displaySuccessOnCreateAccount(doctor);
        };
    }

    private Command CreateAdmin() {
        return (x) -> {

            UserCredentials userCred = adminScreenView.registerAdminPrompt();
            AdminData admin = adminManager.createAdmin(userCred.username(), userCred.password());
            displaySuccessOnCreateAccount(admin);
        };
    }

    private Command CreatePatient() {
        PatientManager patientManager = new PatientManager(getDatabase());
        return (x) -> {
            UserCredentials userCred = adminScreenView.registerPatientPrompt();
            PatientData patient = patientManager.createPatient(userCred.username(), userCred.password());
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

    private Command changeClinicInformation() {
        ClinicManager clinicManager = new ClinicManager(getDatabase());
        return (x) -> changeCurrentController(new ClinicController(getContext(), currentController,
                clinicManager.clinicData()));
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
            // NOTE this is can be any user not just the one using it so can't use reset password prompt
            String name = adminScreenView.getUsersName();
            if (!(changePassword(patientManager, name) |
                    (changePassword(adminManager, name)) |
                    (changePassword(secretaryManager, name)) |
                    (changePassword(doctorManager, name)))){

                adminScreenView.userDoesNotExistError(name);
            }
        };
    }

}
