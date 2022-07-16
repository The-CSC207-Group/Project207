package controllers;

import dataBundles.DoctorData;
import dataBundles.PatientData;
import dataBundles.SecretaryData;
import useCases.accessClasses.SystemAccess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class SignInController extends TerminalController {

    SystemAccess systemAccess;

    public SignInController(Context parent) {
        super(parent);
        this.systemAccess = new SystemAccess(getDatabase());
    }

    @Override
    public HashMap<String, Command> AllCommands() {
        HashMap<String, Command> commands = super.AllCommands();
        commands.put("sign in", new SignInCommand());
        commands.put("register", new RegisterCommand());
        return commands;
    }

    @Override
    public void WelcomeMessage() {
        presenter.infoMessage("\nWelcome to the program! Please sign in or create an account." +
                              "\nType 'help' to see a list of all possible commands.");
    }

    class SignInCommand implements Command {

        @Override
        public boolean execute(ArrayList<String> args) {
            List<String> fields = Arrays.asList("username", "password");
            HashMap<String, String> responses = presenter.promptPopup(fields);
            String username = responses.get(fields.get(0));
            String password = responses.get(fields.get(1));
            
            if (systemAccess.adminSignIn(username, password) != null) {
                return false;
                
            } else if (systemAccess.patientSignIn(username, password) != null) {
                PatientData patientData = systemAccess.patientSignIn(username, password);
                new PatientController(getContext(), patientData);
                
            } else if (systemAccess.doctorSignIn(username, password) != null) {
                DoctorData doctorData = systemAccess.doctorSignIn(username, password);
                new DoctorController(getContext(), doctorData);
                
            } else if (systemAccess.secretarySignIn(username, password) != null) {
                SecretaryData secretaryData = systemAccess.secretarySignIn(username, password);
                new SecretaryController(getContext(), secretaryData);

            } else {
                presenter.errorMessage("failed to sign in");
                return false;
            }
            return true;
        }
    }

    class RegisterCommand implements Command {

        @Override
        public boolean execute(ArrayList<String> args) {
            List<String> fields = Arrays.asList("username", "password");
            HashMap<String, String> responses = presenter.promptPopup(fields);
            String username = responses.get(fields.get(0));
            String password = responses.get(fields.get(1));

//            if (systemManager.createUser(username, password)) {
//                presenter.successMessage("User " + username + " has been created");
//            } else {
//                presenter.errorMessage("Failed to create user: Username already exists");
//            }

            return true;
        }
    }
    class BackCommand implements Command{

        @Override
        public boolean execute(ArrayList<String> args) {
            changeCurrentController(new SignInController(getContext()));
            return true;
        }
    }



}
