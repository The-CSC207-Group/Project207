package controllers;

import dataBundles.*;
import presenter.response.UserCredentials;
import presenter.screenViews.AdminScreenVIew;
import useCases.accessClasses.AdminAccess;

import java.util.ArrayList;
import java.util.HashMap;

public class AdminController extends TerminalController{
    private AdminAccess adminAccess;
    private AdminData adminData;
    private AdminScreenVIew adminScreenVIew = new AdminScreenVIew();
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
        commands.put("get logs", new getLogs());
        commands.put("sign out", signOut());
        commands.put("Delete patient", new deletePatient());
        return commands;
    }

    Command CreateSecretary(){
        return (x) -> {
            UserCredentials c = adminScreenVIew.registerSecretaryPrompt();
            SecretaryData secretary = adminAccess.createSecretary(c.username(), c.password());
            displaySuccessOnCreateAcount(secretary);
        };
    }
    Command CreateDoctor(){
        return (x) -> {
            UserCredentials c = adminScreenVIew.registerDoctorPrompt();
            DoctorData secretary = adminAccess.createDoctor(c.username(), c.password());
            displaySuccessOnCreateAcount(secretary);
        };
    }
    Command CreateAdmin(){
        return (x) -> {
            UserCredentials c = adminScreenVIew.registerAdminPrompt();
            DoctorData secretary = adminAccess.createDoctor(c.username(), c.password());
            displaySuccessOnCreateAcount(secretary);
        };
    }
    Command CreatePatient(){
        return (x) -> {
            UserCredentials c = adminScreenVIew.registerPatientPrompt();
            PatientData patient = adminAccess.createPatient(c.username(), c.password());
            displaySuccessOnCreateAcount(patient);
        };
    }
    private void displaySuccessOnCreateAcount(UserData user){
        if (user == null){
            adminScreenVIew.failedCreateAccount();
        } else {
            adminScreenVIew.successCreateAccount();
        }
    }
//    class CreateSecretaryAccount implements Command{
//
//        @Override
//        public void execute(ArrayList<String> args) {
//            String username = presenter.promptPopup("Enter Username");
//            String password = presenter.promptPopup("Enter Password");
//            if (adminAccess.doesSecretaryExist(username)){
//                adminAccess.createSecretary(username, password);
//                presenter.successMessage("Successfully created new secretary");}
//            else {
//                presenter.warningMessage("This username already exists. No new secretary account created");}
//        }
//    }
//    class CreatePatientAccount implements Command{
//
//        @Override
//        public void execute(ArrayList<String> args) {
//            String username = presenter.promptPopup("Enter Username");
//            String password = presenter.promptPopup("Enter Password");
//            if (adminAccess.doesPatientExist(username)){
//                adminAccess.createPatient(username, password);
//                presenter.successMessage("Successfully created new patient");}
//            else {
//                presenter.warningMessage("This username already exists. No new patient account created");}
//        }
//    }
//    class CreateDoctorAccount implements Command{
//
//        @Override
//        public void execute(ArrayList<String> args) {
//            String username = presenter.promptPopup("Enter Username");
//            String password = presenter.promptPopup("Enter Password");
//            if (adminAccess.doesDoctorExist(username)){
//               adminAccess.createDoctor(username, password);
//                presenter.successMessage("Successfully created new doctor");}
//            else {
//                presenter.warningMessage("This username already exists. No new doctor account created");}
//        }
//    }
    Command ChangePassword(){
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
    class ChangeAdminPassword implements Command{

        @Override
        public void execute(ArrayList<String> args) {
            String p1 = presenter.promptPopup("Enter New Password");
            String p2 = presenter.promptPopup("Re-enter new password");
            if (p1.equals(p2)){
                adminAccess.changePassword(adminData.getUsername(), p1);
                presenter.successMessage("Successfully changed password");
            }
            else {
                presenter.errorMessage("Invalid! Please ensure both passwords match");
            }
        }
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
