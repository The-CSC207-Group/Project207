package controllers;

import dataBundles.*;
import presenter.response.PasswordResetDetails;
import presenter.response.UserCredentials;
import presenter.screenViews.AdminScreenVIew;
import useCases.accessClasses.AdminAccess;
import useCases.managers.AdminManager;

import java.util.ArrayList;
import java.util.HashMap;

public class AdminController extends TerminalController{
    private AdminAccess adminAccess;
    private AdminManager adminManager;
    private AdminData adminData;
    private AdminScreenVIew v = new AdminScreenVIew();
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
        commands.put("change password", changePassword());
        commands.put("get logs", new getLogs());
        commands.put("sign out", signOut());
        commands.put("delete patient", new deletePatient());
        commands.put("getUserInfo", notImplemented());
        return commands;
    }


    Command CreateSecretary(){
        return (x) -> {
            UserCredentials c = v.registerSecretaryPrompt();
            SecretaryData secretary = adminAccess.createSecretary(c.username(), c.password());
            displaySuccessOnCreateAcount(secretary);
        };
    }
    Command CreateDoctor(){
        return (x) -> {
            UserCredentials c = v.registerDoctorPrompt();
            DoctorData secretary = adminAccess.createDoctor(c.username(), c.password());
            displaySuccessOnCreateAcount(secretary);
        };
    }
    Command CreateAdmin(){
        return (x) -> {
            UserCredentials c = v.registerAdminPrompt();
            DoctorData secretary = adminAccess.createDoctor(c.username(), c.password());
            displaySuccessOnCreateAcount(secretary);
        };
    }
    Command CreatePatient(){
        return (x) -> {
            UserCredentials c = v.registerPatientPrompt();
            PatientData patient = adminAccess.createPatient(c.username(), c.password());
            displaySuccessOnCreateAcount(patient);
        };
    }
    private void displaySuccessOnCreateAcount(UserData user){
        if (user == null){
            v.failedCreateAccount();
        } else {
            v.successCreateAccount();
        }
    }
    Command changePassword(){
        return (x) -> {
            PasswordResetDetails pa = v.resetPasswordPrompt();
            if (pa.password().equals(pa.confirmedPassword())){
                adminAccess.changeAdminsPassword(adminData, pa.password());
            }
            else v.showResetPasswordMismatchError();
        };
    }



    class getLogs implements Command{

        @Override
        public void execute(ArrayList<String> args) {
            ArrayList<LogData> logs = adminAccess.getLogs(adminData);

        }
    }

    class deletePatient implements Command{

        @Override
        public void execute(ArrayList<String> args) {
            String username = presenter.promptPopup("Enter username to be deleted");
            adminAccess.deletePatientUser(username);

        }
    }
}
