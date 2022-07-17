package controllers;

import dataBundles.AppointmentData;
import dataBundles.LogData;
import dataBundles.PatientData;
import dataBundles.PrescriptionData;
import presenter.screenViews.PatientScreenView;
import useCases.accessClasses.PatientAccess;

import java.util.ArrayList;
import java.util.HashMap;

public class PatientController extends TerminalController {
    private PatientAccess patientAccess;
    private PatientScreenView patientView = new PatientScreenView();
    private PatientData patientData;

    public PatientController(Context context, PatientData patientData) {
        super(context);
        this.patientAccess = new PatientAccess(getDatabase());
        this.patientData = patientData;
    }

    @Override
    public HashMap<String, Command> AllCommands() {
        HashMap<String, Command> commands = super.AllCommands();
        commands.put("change password", new ChangePassword());
        commands.put("logs", new GetLogs());
        commands.put("appointments", new ViewAppointments());
        commands.put("active prescription", new ViewActivePrescriptions());
        commands.put("all prescriptions", new ViewAllPrescriptions());
        commands.put("active prescription details", new ViewActivePrescriptionsDetailed());
        commands.put("all prescription details", new ViewAllPrescriptionsDetailed());
        commands.put("sign out", signOut());
        return commands;
    }

    class ChangePassword implements Command {

        @Override
        public boolean execute(ArrayList<String> args) {
            String newPassword1 = presenter.promptPopup("Enter a new password");
            String newPassword2 = presenter.promptPopup("Re-enter the new password");
            if (newPassword1.equals(newPassword2)){
                patientAccess.changeCurrentUserPassword(patientData, newPassword1);
            } else {
                presenter.errorMessage("These do not match");
            }
            return false;
        }
    }

    class ViewAppointments implements Command {

        @Override
        public boolean execute(ArrayList<String> args) {
            ArrayList<AppointmentData> appointments = patientAccess.getAppointments(patientData);
            return false;
        }
    }

    class ViewAllPrescriptions implements Command {

        @Override
        public boolean execute(ArrayList<String> args) {
            ArrayList<PrescriptionData> prescriptions = patientAccess.getAllPrescriptions(patientData.getId());
            patientView.viewPrescriptionHistory(prescriptions, false);
            return false;
        }
    }

    class ViewActivePrescriptions implements Command {
        @Override
        public boolean execute(ArrayList<String> args) {
            ArrayList<PrescriptionData> prescriptions = patientAccess.getActivePrescriptions(patientData.getId());
            patientView.viewActivePrescriptions(prescriptions, false);
            return false;
        }
    }

    class ViewAllPrescriptionsDetailed implements Command {

        @Override
        public boolean execute(ArrayList<String> args) {
            ArrayList<PrescriptionData> prescriptions = patientAccess.getAllPrescriptions(patientData.getId());
            patientView.viewPrescriptionHistory(prescriptions, true);
            return false;
        }
    }

    class ViewActivePrescriptionsDetailed implements Command {
        @Override
        public boolean execute(ArrayList<String> args) {
            ArrayList<PrescriptionData> prescriptions = patientAccess.getActivePrescriptions(patientData.getId());
            patientView.viewActivePrescriptions(prescriptions, true);
            return false;
        }
    }

    class GetLogs implements Command{

        @Override
        public boolean execute(ArrayList<String> args) {
            ArrayList<LogData> logs = patientAccess.getLogs(patientData);
            return false;
        }
    }
}
