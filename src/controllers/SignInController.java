package controllers;

import dataBundles.*;
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
                AdminData adminData = systemAccess.adminSignIn(username, password);
                new AdminController(getContext(), adminData);
                
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

    class BackCommand implements Command{

        @Override
        public boolean execute(ArrayList<String> args) {
            changeCurrentController(new SignInController(getContext()));
            return true;
        }
    }



}
