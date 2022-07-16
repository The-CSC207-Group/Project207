package controllers;

import dataBundles.PatientData;
import dataBundles.PatientData;
import dataBundles.SecretaryDataBundle;
import useCases.accessClasses.SecretaryAccess;

import java.util.HashMap;

public class SecretaryLoadedPatientController extends TerminalController{
    PatientData patientData;
    SecretaryDataBundle secretaryDataBundle;
    SecretaryAccess secretaryAccess;
    DoctorController doctorController;

    public SecretaryLoadedPatientController(Context context, SecretaryController secretaryController,
                                            PatientData patientDataBundle) {

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
