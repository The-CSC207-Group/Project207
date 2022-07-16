package controllers;

import dataBundles.*;
import entities.Clinic;
import presenter.response.UserCredentials;
import presenter.screenViews.SignInScreenView;
import useCases.accessClasses.SystemAccess;

import java.util.ArrayList;
import java.util.HashMap;


public class SignInController extends TerminalController {

    SystemAccess systemAccess;
    SignInScreenView view = new SignInScreenView();

    public SignInController(Context parent) {
        super(parent);
        this.systemAccess = new SystemAccess(getDatabase());
        view.welcomeMessage();
    }

    @Override
    public HashMap<String, Command> AllCommands() {
        HashMap<String, Command> commands = super.AllCommands();
        commands.put("sign in", new SignInCommand());
        return commands;
    }

    class SignInCommand implements Command {

        @Override
        public boolean execute(ArrayList<String> args) {
            UserCredentials userCredentials = view.userLoginPrompt();
            String username = userCredentials.username();
            String password = userCredentials.password();
            
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
                view.showLoginError();
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
