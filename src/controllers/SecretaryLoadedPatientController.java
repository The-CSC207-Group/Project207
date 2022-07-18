package controllers;

import dataBundles.AppointmentData;
import dataBundles.PatientData;
import dataBundles.PrescriptionData;
import dataBundles.SecretaryData;
import useCases.accessClasses.SecretaryAccess;

import java.util.ArrayList;
import java.util.HashMap;

public class SecretaryLoadedPatientController extends TerminalController{
    PatientData patientData;

    SecretaryData secretaryData;
    SecretaryAccess secretaryAccess;
    SecretaryController secretaryController;

    public SecretaryLoadedPatientController(Context context, SecretaryController secretaryController,
                                            PatientData patientData) {
        super(context);
        this.secretaryController = secretaryController;
        this.patientData = patientData;
        this.secretaryAccess = new SecretaryAccess(getDatabase());
    }


    public HashMap<String, Command> AllCommands(){
        HashMap<String, Command> commands  = super.AllCommands();
        commands.put("view active prescription", ViewActivePrescription());
        commands.put("view all prescriptions", ViewPrescriptionHistory());
        commands.put("view appointments", ViewAppointments());
        commands.put("change patient password", ChangePatientPassword());
        commands.put("unload patient", back(secretaryController));
        return commands;
    }
    Command ViewActivePrescription(){
        return (x) -> {
            ArrayList<PrescriptionData> prescriptions = secretaryAccess.
                getActivePrescriptions(patientData.getUsername());};
    }

    Command ViewPrescriptionHistory(){
        return (x) -> {
            ArrayList<PrescriptionData> prescriptions = secretaryAccess.getAllPrescriptions(patientData.getUsername());
        };
    }

    Command ViewAppointments(){
        return (x) -> {
            ArrayList<AppointmentData> appointments = secretaryAccess.getPatientAppointmentDataBundles(patientData);
        };
    }

    Command ChangePatientPassword() {
        return (x) -> {
            String p1 = presenter.promptPopup("Enter New Password");
            String p2 = presenter.promptPopup("Re-enter new password");
            if (p1.equals(p2)){
                secretaryAccess.changePatientPassword(patientData, p1);
                presenter.successMessage("Successfully changed password");
            } else {
                presenter.errorMessage("Invalid! Please ensure both passwords match");
            }};
    }


}
