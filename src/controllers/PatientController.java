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
    private PatientScreenView view = new PatientScreenView();
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
        commands.put("get logs", new GetLogs());
        commands.put("view appointments", new ViewAppointments());
        commands.put("view active prescription", new ViewActivePrescriptions());
        commands.put("view all prescription", new ViewAllPrescriptions());
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
            return false;
        }
    }

    class ViewActivePrescriptions implements Command {
        @Override
        public boolean execute(ArrayList<String> args) {
            ArrayList<PrescriptionData> prescriptions = patientAccess.getActivePrescriptions(patientData.getId());
            return false;
        }
    }

    class GetLogs implements Command{

        @Override
        public boolean execute(ArrayList<String> args) {
            ArrayList<LogData> logs = patientAccess.getLogs(patientData.getUsername());
            return false;
        }
    }
}
