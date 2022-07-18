package controllers;

import dataBundles.*;
import presenter.response.UserCredentials;
import presenter.screenViews.AdminScreenView;
import useCases.accessClasses.AdminAccess;
import useCases.accessClasses.userType;
import useCases.managers.AdminManager;
import useCases.managers.DoctorManager;
import useCases.managers.LogManager;
import useCases.managers.PatientManager;

import java.util.HashMap;

public class AdminController extends TerminalController{
    private AdminAccess adminAccess;
    private AdminData adminData;
    private AdminScreenView adminScreenView = new AdminScreenView();
    AdminManager adminManager = new AdminManager(getDatabase());
    public AdminController(Context parent, AdminData adminData) {
        super(parent);
        this.adminData = adminData;
        this.adminAccess = new AdminAccess(getDatabase());
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
        return (x) -> {
            UserCredentials c = adminScreenView.registerSecretaryPrompt();
            SecretaryData secretary = adminAccess.createSecretary(c.username(), c.password());
            displaySuccessOnCreateAcount(secretary);
        };
    }
    Command CreateDoctor(){
        return (x) -> {
            DoctorManager doctorManager = new DoctorManager(getDatabase());
            UserCredentials c = adminScreenView.registerDoctorPrompt();
            DoctorData doctor = doctorManager.createDoctor(c.username(), c.password());
            displaySuccessOnCreateAcount(doctor);
        };
    }
    private Command CreateAdmin(){
        return (x) -> {

            UserCredentials c = adminScreenView.registerAdminPrompt();
            AdminData admin = adminManager.createAdmin(c.username(), c.password());
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
            adminScreenView.failedCreateAccount();
        } else {
            adminScreenView.successCreateAccount();
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
            String username = adminScreenView.patientUsernamePrompt();
            adminManager.deleteUser(username);
        };
    }
}
