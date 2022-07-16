package controllers;

import dataBundles.ContactDataBundle;
import dataBundles.SecretaryDataBundle;
import entities.Secretary;
import useCases.accessClasses.DoctorAccess;
import useCases.accessClasses.SecretaryAccess;

import java.util.ArrayList;
import java.util.HashMap;

public class SecretaryController extends TerminalController {


    private SecretaryAccess secretaryAccess;
    private SecretaryDataBundle secretaryDataBundle;

    public SecretaryController(Context context, SecretaryDataBundle secretaryData) {
        super(context);
        this.secretaryAccess = new SecretaryAccess(getDatabase());
        this.secretaryDataBundle = secretaryData;
    }

    @Override
    public HashMap<String, Command> AllCommands() {
        HashMap commands = super.AllCommands();
        commands.put("changePassword", new ChangePassword());
        commands.put("createPatientAccount", new CreatePatientAccount());
        commands.put("createDoctorAccount", new CreateDoctorAccount());
        return commands;
    }

    @Override
    void WelcomeMessage() {

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
            ContactDataBundle contact;
//            if (){
//                secretaryAccess.createDoctor(username, password, contact);// need to implement error or success message
//                presenter.successMessage("Successfully created new doctor");}
//            else {
//                presenter.warningMessage("This username already exists. No new doctor account created");}
            return false;
        }
    }
    class ChangePassword implements Command{

        @Override
        public boolean execute(ArrayList<String> args) {
            String p1 = presenter.promptPopup("Enter New Password");
            String p2 = presenter.promptPopup("Re-enter new password");
            if (p1.equals(p2)){
                secretaryAccess.changePassword(secretaryDataBundle.getId(), p1 );
                presenter.successMessage("Successfully changed password");
            } else {
                presenter.errorMessage("Invalid! Please ensure both passwords match");
            }
            return false;
        }
    }
}
