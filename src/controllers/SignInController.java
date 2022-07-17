package controllers;

import dataBundles.*;
import entities.Clinic;
import entities.Patient;
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
        public void execute(ArrayList<String> args) {
            UserCredentials userCredentials = view.userLoginPrompt();
            String username = userCredentials.username();
            String password = userCredentials.password();
            
            if (systemAccess.adminSignIn(username, password) != null) {
                AdminData adminData = systemAccess.adminSignIn(username, password);
                AdminController adminController = new AdminController(getContext(), adminData);
                changeCurrentController(adminController);
                
            } else if (systemAccess.patientSignIn(username, password) != null) {
                PatientData patientData = systemAccess.patientSignIn(username, password);
                PatientController patientController = new PatientController(getContext(), patientData);
                changeCurrentController(patientController);
                
            } else if (systemAccess.doctorSignIn(username, password) != null) {
                DoctorData doctorData = systemAccess.doctorSignIn(username, password);
                DoctorController doctorController = new DoctorController(getContext(), doctorData);
                changeCurrentController(doctorController);
                
            } else if (systemAccess.secretarySignIn(username, password) != null) {
                SecretaryData secretaryData = systemAccess.secretarySignIn(username, password);
                SecretaryController secretaryController = new SecretaryController(getContext(), secretaryData);
                changeCurrentController(secretaryController);

            } else {
                view.showLoginError();
            }
        }
    }

    class BackCommand implements Command{

        @Override
        public void execute(ArrayList<String> args) {
            changeCurrentController(new SignInController(getContext()));
        }
    }



}
