package controllers;

import dataBundles.PatientDataBundle;
import dataBundles.SecretaryDataBundle;
import useCases.accessClasses.SecretaryAccess;

import java.util.HashMap;

public class SecretaryLoadedPatientController extends TerminalController{
    PatientDataBundle patientData;
    SecretaryDataBundle secretaryDataBundle;
    SecretaryAccess secretaryAccess;
    DoctorController doctorController;

    public SecretaryLoadedPatientController(Context context, SecretaryController secretaryController,
                                            PatientDataBundle patientDataBundle) {

        super(context);
    }


    public HashMap<String, Command> AllCommands(){
        HashMap<String, Command> command  = super.AllCommands();
        return command;
    }
    @Override
    void WelcomeMessage() {

    }
}
