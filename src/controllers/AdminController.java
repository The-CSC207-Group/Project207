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
        HashMap commands = super.AllCommands();
        commands.put("Create Secretary Account", new CreateSecretaryAccount());
        commands.put("Create Doctor account", new CreateDoctorAccount());
        commands.put("Create Patient account", new CreatePatientAccount());
        commands.put("Change Admin Password", new ChangeAdminPassword());
        commands.put("getLogs", new getLogs());
        return commands;
    }

    @Override
    void WelcomeMessage() {

    }
    class CreateSecretaryAccount implements Command{

        @Override
        public boolean execute(ArrayList<String> args) {
            return false;
        }
    }
    class CreatePatientAccount implements Command{

        @Override
        public boolean execute(ArrayList<String> args) {
            return false;
        }
    }
    class CreateDoctorAccount implements Command{

        @Override
        public boolean execute(ArrayList<String> args) {
            String username = presenter.promptPopup("Enter Username");
            String password = presenter.promptPopup("Enter Password");
            ContactData contact;
//            if (adminAccess.doesDoctorExist(username)){
//////                secretaryAccess.createDoctor(username, password, contact);// need to implement error or success message
////                presenter.successMessage("Successfully created new doctor");}
////            else {
////                presenter.warningMessage("This username already exists. No new doctor account created");}
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
            ArrayList<LogData> logs = adminAccess.getLogs(adminData.getUsername());
            return false;
        }
    }
}
