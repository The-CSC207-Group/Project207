package controllers;

import dataBundles.PatientData;
import dataBundles.SecretaryDataBundle;
import useCases.accessClasses.SecretaryAccess;

import java.util.HashMap;

public class SecretaryLoadedPatientController extends TerminalController{
    PatientData patientData;
    SecretaryDataBundle secretaryData;
    SecretaryAccess secretaryAccess;
    SecretaryController secretaryController;

    public SecretaryLoadedPatientController(Context context, SecretaryController secretaryController,
                                            PatientData patientData) {
        super(context);
        this.secretaryController = secretaryController;
        this.patientData = patientData;
        this.secretaryAccess = new SecretaryAccess(getDatabase());
        this.secretaryController = secretaryController;
    }


    public HashMap<String, Command> AllCommands(){
        HashMap<String, Command> command  = super.AllCommands();
        return command;
    }
    @Override
    void WelcomeMessage() {

    }
}
