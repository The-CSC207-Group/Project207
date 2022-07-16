package controllers;

import dataBundles.AdminData;
import dataBundles.ContactData;
import dataBundles.LogData;
import useCases.accessClasses.AdminAccess;

import java.util.ArrayList;
import java.util.HashMap;

public class AdminController extends TerminalController{
    private AdminAccess adminAccess;
    private AdminData adminData;
    public AdminController(Context parent, AdminData adminData) {
        super(parent);
        this.adminData = adminData;
        this.adminAccess = new AdminAccess(getDatabase());
    }
    @Override
    public HashMap<String, Command> AllCommands() {
        HashMap<String, Command> commands = super.AllCommands();
        commands.put("create secretary", new CreateSecretaryAccount());
        commands.put("create doctor", new CreateDoctorAccount());
        commands.put("create patient", new CreatePatientAccount());
        commands.put("change password", new ChangeAdminPassword());
        commands.put("get logs", new getLogs());
        commands.put("sign out", signOut());
        return commands;
    }

//    class CreateAccount implements Command{
//
//        @Override
//        public boolean execute(ArrayList<String> args) {
//            String username = presenter.promptPopup("Enter Username");
//            String password = presenter.promptPopup("Enter Password");
//            return false;
//        }
//    }
    class CreateSecretaryAccount implements Command{

        @Override
        public boolean execute(ArrayList<String> args) {
            String username = presenter.promptPopup("Enter Username");
            String password = presenter.promptPopup("Enter Password");
            if (adminAccess.doesSecretaryExist(username)){
                adminAccess.createSecretary(username, password);
                presenter.successMessage("Successfully created new secretary");}
            else {
                presenter.warningMessage("This username already exists. No new secretary account created");}
            return false;
        }
    }
    class CreatePatientAccount implements Command{

        @Override
        public boolean execute(ArrayList<String> args) {
            String username = presenter.promptPopup("Enter Username");
            String password = presenter.promptPopup("Enter Password");
            if (adminAccess.doesPatientExist(username)){
                adminAccess.createPatient(username, password);
                presenter.successMessage("Successfully created new patient");}
            else {
                presenter.warningMessage("This username already exists. No new patient account created");}
            return false;
        }
    }
    class CreateDoctorAccount implements Command{

        @Override
        public boolean execute(ArrayList<String> args) {
            String username = presenter.promptPopup("Enter Username");
            String password = presenter.promptPopup("Enter Password");
            if (adminAccess.doesDoctorExist(username)){
               adminAccess.createDoctor(username, password);
                presenter.successMessage("Successfully created new doctor");}
            else {
                presenter.warningMessage("This username already exists. No new doctor account created");}
            return false;
        }
    }
    class ChangeAdminPassword implements Command{

        @Override
        public boolean execute(ArrayList<String> args) {
            String p1 = presenter.promptPopup("Enter New Password");
            String p2 = presenter.promptPopup("Re-enter new password");
            if (p1.equals(p2)){
                adminAccess.changePassword(adminData.getUsername(), p1);
                presenter.successMessage("Successfully changed password");
            }
            else {
                presenter.errorMessage("Invalid! Please ensure both passwords match");
            }
            return false;
        }
    }
    class getLogs implements Command{

        @Override
        public boolean execute(ArrayList<String> args) {
            ArrayList<LogData> logs = adminAccess.getLogs(adminData);
            return false;
        }
    }
}
