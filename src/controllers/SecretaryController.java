package controllers;

import dataBundles.ContactData;
import dataBundles.SecretaryData;
import useCases.accessClasses.SecretaryAccess;

import java.util.ArrayList;
import java.util.HashMap;

public class SecretaryController extends TerminalController {


    private SecretaryAccess secretaryAccess;
    private SecretaryData secretaryData;
    private SecretaryController self = this;

    public SecretaryController(Context context, SecretaryData secretaryData) {
        super(context);
        this.secretaryAccess = new SecretaryAccess(getDatabase());
        this.secretaryData = secretaryData;
    }

    @Override
    public HashMap<String, Command> AllCommands() {
        HashMap commands = super.AllCommands();
        commands.put("changePassword", new ChangePassword());
        commands.put("createPatientAccount", new CreatePatientAccount());
        commands.put("createDoctorAccount", new CreateDoctorAccount());
        commands.put("getLogs", new Logs());
        return commands;
    }

    @Override
    void WelcomeMessage() {

    }
    class LoadPatient implements Command {
        @Override
        public boolean execute(ArrayList<String> args) {
            String name = presenter.promptPopup("username of patient");
            secretaryAccess.getPatient(name).ifPresent(
                    (patientData) -> {
                        changeCurrentController(new SecretaryLoadedPatientController(getContext(), self,  patientData));

                    }
            );
            return false;
        }
    }

    class CreatePatientAccount implements Command{

        @Override
        public boolean execute(ArrayList<String> args) {
            String username = presenter.promptPopup("Enter Username");
            String password = presenter.promptPopup("Enter Password");
            ContactData contact;
            String healthNumber = presenter.promptPopup("Enter Health Number"); // need to confirm if health no is input by user or no
            if (secretaryAccess.doesPatientExist(username)){
                // secretaryAccess.createPatient(username, password, contact, healthNumber);// need to implement error or success message
                presenter.successMessage("Successfully created new Patient");}
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
            ContactData contact;
            if (secretaryAccess.doesDoctorExist(username)){
//                secretaryAccess.createDoctor(username, password, contact);// need to implement error or success message
                presenter.successMessage("Successfully created new doctor");}
            else {
                presenter.warningMessage("This username already exists. No new doctor account created");}
            return false;
        }
    }
    class ChangePassword implements Command{

        @Override
        public boolean execute(ArrayList<String> args) {
            String p1 = presenter.promptPopup("Enter New Password");
            String p2 = presenter.promptPopup("Re-enter new password");
            if (p1.equals(p2)){
                secretaryAccess.changeSecretaryPassword(secretaryData.getSecretaryId(), p1 );
                presenter.successMessage("Successfully changed password");
            } else {
                presenter.errorMessage("Invalid! Please ensure both passwords match");
            }
            return false;
        }
    }
    class Logs implements Command{

        @Override
        public boolean execute(ArrayList<String> args) { // need to implement
            return false;
        }
    }
}
