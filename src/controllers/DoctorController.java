package controllers;

import dataBundles.AppointmentData;
import dataBundles.DoctorData;
import dataBundles.LogData;
import presenter.screenViews.DoctorScreenView;
import useCases.accessClasses.DoctorAccess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DoctorController extends TerminalController{
    private DoctorAccess doctorAccess;
    private DoctorScreenView view = new DoctorScreenView();

    private DoctorData doctorData;
    private DoctorController self = this;

    public DoctorController(Context context, DoctorData doctorData){
        super(context);
        this.doctorAccess = new DoctorAccess(getDatabase());
        this.doctorData = doctorData;
    }

    @Override
    public HashMap<String, Command> AllCommands() {
        HashMap h = super.AllCommands();
        h.put("loadpatient", new LoadPatient());
        h.put("changePassword", new ChangePassword());
        h.put("show schedule", new CheckSchedule());
        h.put("show logs", new GetLogs());
        return h;
    }

    @Override
    void WelcomeMessage() {

    }
    class LoadPatient implements Command {

        @Override
        public boolean execute(ArrayList<String> args) {
            String name = presenter.promptPopup("name");
            doctorAccess.getPatient(name).ifPresent(
                    (patientData) -> {
                        changeCurrentController(new DoctorLoadedPatientController(getContext(), self, doctorData, patientData));
                    }
            );
            return false;
        }
    }
    class ChangePassword implements Command {

        @Override
        public boolean execute(ArrayList<String> args) {
            String newPassword1 = presenter.promptPopup("Enter a new password");
            String newPassword2 = presenter.promptPopup("Re-enter the new password");
            if (newPassword1.equals(newPassword2)){
                doctorAccess.changePassword(doctorData, newPassword1);
            } else {
                presenter.errorMessage("These do not match");
            }
            return false;
        }
    }

    class CheckSchedule implements Command{

        @Override
        public boolean execute(ArrayList<String> args) {
            List<AppointmentData> appointments = doctorAccess.getAllDoctorAppointments(doctorData);
            return false;
        }
    }
    class GetLogs implements Command{

        @Override
        public boolean execute(ArrayList<String> args) {
            ArrayList<LogData> logs = doctorAccess.getLogs(doctorData.getUsername());
            return false;
        }
    }
}
