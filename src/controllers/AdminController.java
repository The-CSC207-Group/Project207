package controllers;

import dataBundles.*;
import presenter.response.UserCredentials;
import presenter.screenViews.AdminScreenView;

import useCases.managers.*;

import java.util.HashMap;

public class AdminController extends TerminalController{

    private AdminData adminData;
    private AdminScreenView adminScreenView = new AdminScreenView();
    AdminManager adminManager = new AdminManager(getDatabase());
    public AdminController(Context parent, AdminData adminData) {
        super(parent);
        this.adminData = adminData;

    }
    @Override
    public HashMap<String, Command> AllCommands() {
        HashMap<String, Command> commands = super.AllCommands();
        commands.put("create admin", CreateAdmin());
        commands.put("create secretary", CreateSecretary());
        commands.put("create doctor", CreateDoctor());
        commands.put("create patient", CreatePatient());
        commands.put("change password", ChangePassword());
        commands.put("get logs", getLogs());
        commands.put("sign out", signOut());
        commands.put("Delete patient", deletePatient());
        return commands;
    }

    Command CreateSecretary(){
        SecretaryManager secretaryManager = new SecretaryManager(getDatabase());
        return (x) -> {
            UserCredentials c = adminScreenView.registerSecretaryPrompt();
            SecretaryData secretary = secretaryManager.createSecretary(c.username(), c.password());
            displaySuccessOnCreateAcount(secretary);
        };
    }
    Command CreateDoctor(){
        DoctorManager doctorManager = new DoctorManager(getDatabase());
        return (x) -> {
            UserCredentials userCred = adminScreenView.registerDoctorPrompt();
            DoctorData doctor = doctorManager.createDoctor(userCred.username(), userCred.password());
            displaySuccessOnCreateAcount(doctor);
        };
    }
    private Command CreateAdmin(){
        return (x) -> {

            UserCredentials userCred = adminScreenView.registerAdminPrompt();
            AdminData admin = adminManager.createAdmin(userCred.username(), userCred.password());
            displaySuccessOnCreateAcount(admin);
        };
    }
    private Command CreatePatient(){
        PatientManager patientManager = new PatientManager(getDatabase());
        return (x) -> {
            UserCredentials userCred = adminScreenView.registerPatientPrompt();
            PatientData patient = patientManager.createPatient(userCred.username(), userCred.password());
            displaySuccessOnCreateAcount(patient);
        };
    }
    private void displaySuccessOnCreateAcount(UserData user){
        if (user == null){
            adminScreenView.showFailedToRegisterUserError();
        } else {
            adminScreenView.showRegisterUserSuccess();
        }
    }

    private Command ChangePassword(){

        return (x) -> { String p1 = presenter.promptPopup("Enter New Password");
            String p2 = presenter.promptPopup("Re-enter new password");
            if (p1.equals(p2)){
                adminManager.changeUserPassword(adminData, p1);
                presenter.successMessage("Successfully changed password");
            }
            else {
                presenter.errorMessage("Invalid! Please ensure both passwords match");
            }};
    }
    private Command getLogs (){
        LogManager logManager = new LogManager(getDatabase());
        return (x) -> {
            adminScreenView.viewAllLogs(logManager.getUserLogs(adminData));
        };
    }
    private Command deletePatient (){
        return (x) -> {
            String username = adminScreenView.enterPatientUsernamePrompt();
            adminManager.deleteUser(username);
        };
    }
}
