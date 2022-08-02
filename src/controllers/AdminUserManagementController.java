package controllers;

import dataBundles.*;
import presenters.response.UserCredentials;
import presenters.screenViews.AdminScreenView;
import useCases.AdminManager;
import useCases.DoctorManager;
import useCases.PatientManager;
import useCases.SecretaryManager;

import java.util.LinkedHashMap;

public class AdminUserManagementController extends TerminalController {

    private final AdminController previousController;
    private final AdminData adminData;
    private final AdminScreenView adminScreenView = new AdminScreenView();
    private final PatientManager patientManager;
    private final DoctorManager doctorManager;
    private final SecretaryManager secretaryManager;
    private final AdminManager adminManager;
    /**
     * Creates a new controller for handling the state of the program where commands are being passed into the terminal.
     *
     * @param context Context - a reference to the context object, which stores the current controller and allows for
     *                switching between controllers.
     */
    public AdminUserManagementController(Context context, AdminController previousController, AdminData adminData) {
        super(context);
        this.previousController = previousController;
        this.adminData = adminData;
        this.patientManager = new PatientManager(getDatabase());
        this.secretaryManager = new SecretaryManager(getDatabase());
        this.doctorManager = new DoctorManager(getDatabase());
        this.adminManager = new AdminManager(getDatabase());
    }

    @Override
    public LinkedHashMap<String, Command> AllCommands (){
        LinkedHashMap<String, Command> commands = new LinkedHashMap<>();
        commands.put("back", Back(previousController));
        commands.put("create admin", CreateAdmin());
        commands.put("create secretary", CreateSecretary());
        commands.put("create doctor", CreateDoctor());
        commands.put("create patient", CreatePatient());
        //commands.put("delete user", DeleteUser());

        commands.putAll(super.AllCommands());
        return commands;
    }

    private Command CreateAdmin(){
        return (x) -> {
            UserCredentials userCred = adminScreenView.registerAdminPrompt();
            AdminData admin = adminManager.createAdmin(userCred.username(), userCred.password());
            DisplaySuccessOnCreateAccount(admin);
        };
    }

    private Command CreateDoctor(){
        DoctorManager doctorManager = new DoctorManager(getDatabase());
        return (x) -> {
            UserCredentials userCred = adminScreenView.registerDoctorPrompt();
            DoctorData doctor = doctorManager.createDoctor(userCred.username(), userCred.password());
            DisplaySuccessOnCreateAccount(doctor);
        };
    }

    private Command CreatePatient(){
        PatientManager patientManager = new PatientManager(getDatabase());
        return (x) -> {
            UserCredentials userCred = adminScreenView.registerPatientPrompt();
            PatientData patient = patientManager.createPatient(userCred.username(), userCred.password());
            DisplaySuccessOnCreateAccount(patient);
        };
    }

    private Command CreateSecretary(){
        SecretaryManager secretaryManager = new SecretaryManager(getDatabase());
        return (x) -> {
            UserCredentials userCred = adminScreenView.registerSecretaryPrompt();
            SecretaryData secretary = secretaryManager.createSecretary(userCred.username(), userCred.password());
            DisplaySuccessOnCreateAccount(secretary);
        };
    }

    private void DisplaySuccessOnCreateAccount(UserData<?> user) {
        if (user == null) {
            adminScreenView.showFailedToRegisterUserError();
        } else {
            adminScreenView.showRegisterUserSuccess();
        }
    }
    private boolean DeleteUserHelper(String username) {
        if (patientManager.deleteUser(username)) {
            return true;
        } else if (doctorManager.deleteUser(username)) {
            return true;
        } else if (secretaryManager.deleteUser(username)) {
            return true;
        } else return adminManager.deleteUser(username);
    }

}
