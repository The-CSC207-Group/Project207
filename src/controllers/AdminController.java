package controllers;

import dataBundles.*;
import presenter.response.UserCredentials;
import presenter.screenViews.AdminScreenView;
import useCases.accessClasses.AdminAccess;
import useCases.accessClasses.userType;
import useCases.managers.AdminManager;

import java.util.HashMap;

public class AdminController extends TerminalController{
    private AdminAccess adminAccess;
    private AdminData adminData;
    private AdminScreenView adminScreenView = new AdminScreenView();
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
            UserCredentials c = adminScreenView.registerDoctorPrompt();
            DoctorData secretary = adminAccess.createDoctor(c.username(), c.password());
            displaySuccessOnCreateAcount(secretary);
        };
    }
    private Command CreateAdmin(){
        return (x) -> {
            UserCredentials c = adminScreenView.registerAdminPrompt();
            AdminData secretary = adminAccess.createAdmin(c.username(), c.password());
            displaySuccessOnCreateAcount(secretary);
        };
    }
    private Command CreatePatient(){
        return (x) -> {
            UserCredentials c = adminScreenView.registerPatientPrompt();
            PatientData patient = adminAccess.createPatient(c.username(), c.password());
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
        AdminManager adminManager = new AdminManager(getDatabase());
        return (x) -> { String p1 = presenter.promptPopup("Enter New Password");
            String p2 = presenter.promptPopup("Re-enter new password");
            if (p1.equals(p2)){
                adminAccess.changePassword(adminData.getUsername(), p1);
                presenter.successMessage("Successfully changed password");
            }
            else {
                presenter.errorMessage("Invalid! Please ensure both passwords match");
            }};
    }
    private Command getLogs (){
        return (x) -> {
            adminScreenView.viewAllLogs(adminAccess.getLogs(adminData));
        };
    }
    private Command deletePatient (){
        return (x) -> {
            String username = adminScreenView.patientUsernamePrompt();
            adminAccess.deletePatientUser(username);
        };
    }
}
