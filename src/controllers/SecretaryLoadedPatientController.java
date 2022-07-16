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
        command.put("View Active Prescription", new ViewActivePrescription());
        command.put("View All Prescriptions", new ViewPrescriptionHistory());
        command.put("View Patient Appointments", new ViewAppointments());
        command.put("Change Patient Password", new ChangePatientPassword());
        command.put("Book Appointment", new BookAppointment());
        command.put("Reschedule Appointment", new RescheduleAppointment());
        command.put("Cancel Appointment", new CancelAppointment());
        return command;
    }
    @Override
    void WelcomeMessage() {
    }
    class ViewActivePrescription implements Command {
        @Override
        public boolean execute(ArrayList<String> args) {
            ArrayList<PrescriptionData> prescriptions = secretaryAccess.
                    getActivePrescriptions(patientData.getUsername());
            return false;
        }
    }
    class ViewPrescriptionHistory implements Command{

        @Override
        public boolean execute(ArrayList<String> args) {
            ArrayList<PrescriptionData> prescriptions = secretaryAccess.getAllPrescriptions(patientData.getUsername());
            return false;
        }
    }
    class ViewAppointments implements Command {

        @Override
        public boolean execute(ArrayList<String> args) {
            ArrayList<AppointmentData> appointments = secretaryAccess.
                    getPatientAppointmentDataBundles(patientData);
            return false;
        }
    }
    class ChangePatientPassword implements Command{

        @Override
        public boolean execute(ArrayList<String> args) {
            String p1 = presenter.promptPopup("Enter New Password");
            String p2 = presenter.promptPopup("Re-enter new password");
            if (p1.equals(p2)){
                secretaryAccess.changePatientPassword(patientData, p1);
                presenter.successMessage("Successfully changed password");
            } else {
                presenter.errorMessage("Invalid! Please ensure both passwords match");
            }
            return false;
        }
    }
    class BookAppointment implements Command{
        @Override
        public boolean execute(ArrayList<String> args) {
            return false;
        }
    }
    class RescheduleAppointment implements Command{

        @Override
        public boolean execute(ArrayList<String> args) {
            return false;
        }
    }
    class CancelAppointment implements Command{

        @Override
        public boolean execute(ArrayList<String> args) {
            return false;
        }
    }

}
