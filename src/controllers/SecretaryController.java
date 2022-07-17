package controllers;

import dataBundles.ContactData;
import dataBundles.LogData;
import dataBundles.SecretaryData;
import presenter.screenViews.SecretaryScreenView;
import useCases.accessClasses.SecretaryAccess;

import java.util.ArrayList;
import java.util.HashMap;

public class SecretaryController extends TerminalController {


    private SecretaryAccess secretaryAccess;
    private SecretaryData secretaryData;
    private SecretaryController self = this;
    private SecretaryScreenView view = new SecretaryScreenView();

    public SecretaryController(Context context, SecretaryData secretaryData) {
        super(context);
        this.secretaryAccess = new SecretaryAccess(getDatabase());
        this.secretaryData = secretaryData;
    }

    @Override
    public HashMap<String, Command> AllCommands() {
        HashMap<String, Command> commands = super.AllCommands();
        commands.put("change password", new ChangePassword());
        commands.put("create patient", new CreatePatientAccount());
        commands.put("create doctor", new CreateDoctorAccount());
        commands.put("get logs", new GetLogs());
        commands.put("load patient", new LoadPatient());
        return commands;
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
            if (secretaryAccess.doesPatientExist(username)){
                secretaryAccess.createPatient(username, password);
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
            if (secretaryAccess.doesDoctorExist(username)){
                secretaryAccess.createDoctor(username, password);
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
                secretaryAccess.changeSecretaryPassword(secretaryData, p1 );
                presenter.successMessage("Successfully changed password");
            } else {
                presenter.errorMessage("Invalid! Please ensure both passwords match");
            }
            return false;
        }
    }
    class GetLogs implements Command{

        @Override
        public boolean execute(ArrayList<String> args) {
            ArrayList<LogData> logs = secretaryAccess.getLogs(secretaryData);
            return false;
        }
    }
}
