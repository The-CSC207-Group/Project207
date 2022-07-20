package controllers;

import dataBundles.*;
import entities.Admin;
import entities.User;
import presenter.response.PasswordResetDetails;
import presenter.response.UserCredentials;
import presenter.screenViews.AdminScreenView;
import useCases.managers.*;

import java.util.HashMap;

public class AdminController extends UserController<Admin> {

    private AdminData adminData;
    PatientManager patientManager;
    DoctorManager doctorManager;
    SecretaryManager secretaryManager;
    AdminManager adminManager;
    AdminScreenView adminScreenView = new AdminScreenView();

    public AdminController(Context context, AdminData adminData) {
        super(context, adminData, new AdminManager(context.getDatabase()), new AdminScreenView());
        this.adminData = adminData;
        this.patientManager = new PatientManager(getDatabase());
        secretaryManager = new SecretaryManager(getDatabase());
        doctorManager = new DoctorManager(getDatabase());
        adminManager = new AdminManager(getDatabase());

    }

    /**
     * hashmap of all commands a user can call from the string for that command
     * @return strings along with their commands
     */

    @Override
    public HashMap<String, Command> AllCommands() {
        HashMap<String, Command> commands = super.AllCommands();
        commands.put("create admin", CreateAdmin());
        commands.put("create secretary", CreateSecretary());
        commands.put("create doctor", CreateDoctor());
        commands.put("create patient", CreatePatient());
        commands.put("change user password", changeUserPassword());
        commands.put("delete user", deleteUser());
        commands.put("delete self", deleteSelf());
        return commands;
    }

    Command deleteSelf() {
        return (x) -> {
            adminManager.deleteUserByData(adminData);
            changeCurrentController(new SignInController(context));
        };
    }

    Command CreateSecretary() {
        SecretaryManager secretaryManager = new SecretaryManager(getDatabase());
        return (x) -> {
            UserCredentials c = adminScreenView.registerSecretaryPrompt();
            SecretaryData secretary = secretaryManager.createSecretary(c.username(), c.password());
            displaySuccessOnCreateAccount(secretary);
        };
    }

    Command CreateDoctor() {
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
        } else if (adminManager.deleteUser(username)) {
            return true;
        } else {
            return false;
        }

    }

    private Command deleteUser() {
        return (x) -> {
            String user = adminScreenView.deleteUserPrompt();
            if (deleteUserHelper(user)) {
                adminScreenView.showDeleteUserSuccess();
            } else {
                adminScreenView.showFailedToDeleteUserError();
            }
        };
    }

    /**
     * allows the admin user to change their password
     * @param manager
     * @param name
     * @return
     * @param <T>
     */
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


    /**
     *  Allows the current admin to change the password of any kind of user.
     * @return change user password command
     */
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
