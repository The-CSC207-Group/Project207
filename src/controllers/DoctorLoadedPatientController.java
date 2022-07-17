package controllers;

import dataBundles.DoctorData;
import dataBundles.PatientData;
import presenter.screenViews.DoctorScreenView;
import useCases.accessClasses.DoctorAccess;
import useCases.managers.TimeManager;

import java.util.ArrayList;
import java.util.HashMap;

public class DoctorLoadedPatientController extends TerminalController{
    PatientData patientData;
    DoctorData doctorData;
    DoctorAccess doctorAccess;

    DoctorScreenView doctorView = new DoctorScreenView();

    DoctorController prev;

    @Override
    public HashMap<String, Command> AllCommands() {
        HashMap<String, Command> c  = super.AllCommands();
        c.put("unload patient", back(prev));
        c.put("prescription", prescription());
        c.put("appointments", appointments());
        c.put("get reports", new getReport());
        c.put("add report", new newReport());
        return c;
    }

    public DoctorLoadedPatientController(Context parent, DoctorController prev, DoctorData doctorData, PatientData patientData) {
        super(parent);
        this.patientData = patientData;
        this.doctorData = doctorData;
        doctorAccess = new DoctorAccess(getDatabase());
        this.prev = prev;
    }
    private Command prescription(){
        return (x) -> {
        };
    }
    private Command appointments(){
        return (x) -> {
//            doctorView.viewAppointments(doctorAccess.getAllPatientAppointments(patientData.getId()));
        };
    }

    class getReport implements Command {

        @Override
        public void execute(ArrayList<String> args) {
            doctorAccess.getPatientReports(patientData);
        }
    }
    class newReport implements Command {
        @Override
        public void execute(ArrayList<String> args) {

            doctorAccess.addPatientReport(patientData, doctorData, new TimeManager().getCurrentZonedDateTime(),
                    doctorView.reportDetailsPrompt().header(), doctorView.reportDetailsPrompt().body());
        }
    }

    private Command createAppointment(){
        return (x) -> {
        };
    }
}
