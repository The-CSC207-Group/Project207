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
        HashMap<String, Command> command  = super.AllCommands();
        command.put("view active prescription", new ViewActivePrescription());
        command.put("view all prescriptions", new ViewPrescriptionHistory());
        command.put("view appointments", new ViewAppointments());
        command.put("change patient password", new ChangePatientPassword());
        command.put("book appointment", new BookAppointment());
        command.put("reschedule appointment", new RescheduleAppointment());
        command.put("cancel appointment", new CancelAppointment());
        command.put("unload patient", back(secretaryController));
        return command;
    }

    class ViewActivePrescription implements Command {
        @Override
        public void execute(ArrayList<String> args) {
            ArrayList<PrescriptionData> prescriptions = secretaryAccess.
                    getActivePrescriptions(patientData.getUsername());
        }
    }
    class ViewPrescriptionHistory implements Command{

        @Override
        public void execute(ArrayList<String> args) {
            ArrayList<PrescriptionData> prescriptions = secretaryAccess.getAllPrescriptions(patientData.getUsername());
        }
    }
    class ViewAppointments implements Command {

        @Override
        public void execute(ArrayList<String> args) {
            ArrayList<AppointmentData> appointments = secretaryAccess.
                    getPatientAppointmentDataBundles(patientData);
        }
    }
    class ChangePatientPassword implements Command{

        @Override
        public void execute(ArrayList<String> args) {
            String p1 = presenter.promptPopup("Enter New Password");
            String p2 = presenter.promptPopup("Re-enter new password");
            if (p1.equals(p2)){
                secretaryAccess.changePatientPassword(patientData, p1);
                presenter.successMessage("Successfully changed password");
            } else {
                presenter.errorMessage("Invalid! Please ensure both passwords match");
            }
        }
    }
    class BookAppointment implements Command{
        @Override
        public void execute(ArrayList<String> args) {
        }
    }
    class RescheduleAppointment implements Command{

        @Override
        public void execute(ArrayList<String> args) {
        }
    }
    class CancelAppointment implements Command{

        @Override
        public void execute(ArrayList<String> args) {
        }
    }

}
