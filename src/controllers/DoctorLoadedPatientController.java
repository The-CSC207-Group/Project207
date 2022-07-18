package controllers;

import controllers.common.PrescriptionListCommands;
import dataBundles.DoctorData;
import dataBundles.PatientData;
import presenter.screenViews.DoctorScreenView;
import useCases.accessClasses.DoctorAccess;
import useCases.managers.AppointmentManager;
import useCases.managers.ReportManager;
import useCases.managers.TimeManager;

import java.util.HashMap;

public class DoctorLoadedPatientController extends TerminalController {
    PatientData patientData;
    DoctorData doctorData;
    DoctorAccess doctorAccess;

    DoctorScreenView doctorView = new DoctorScreenView();

    DoctorController prev;

    @Override
    public HashMap<String, Command> AllCommands() {
        PrescriptionListCommands prescriptionController = new PrescriptionListCommands(getDatabase(), patientData);

        HashMap<String, Command> c = super.AllCommands();
        c.put("unload patient", back(prev));
        c.put("view patient appointments", ViewPatientAppointments());
        c.put("get reports", getReport());
        c.put("create report", createReport());
        c.put("delete report", deleteReport());

        HashMap<String, Command> commands = prescriptionController.AllCommands();
        for (String key : commands.keySet()) {
            c.put("patient " + key, commands.get(key));
        }
        return c;
    }

    public DoctorLoadedPatientController(Context parent, DoctorController prev, DoctorData doctorData, PatientData patientData) {
        super(parent);
        this.patientData = patientData;
        this.doctorData = doctorData;
        doctorAccess = new DoctorAccess(getDatabase());
        this.prev = prev;
    }

    private Command ViewPatientAppointments() {
        return (x) -> {
            doctorView.viewAppointments(new AppointmentManager(getDatabase()).getPatientAppointments(patientData));
        };
    }

    private Command getReport() {
        return (x) -> {
            new ReportManager(getDatabase()).getReportDataBundlesFromPatientDataBundle(patientData);
        };
    }

    private Command createReport() {
        return (x) -> {
            new ReportManager(getDatabase()).addReport(patientData, doctorData, new TimeManager().getCurrentZonedDateTime(),
                    doctorView.reportDetailsPrompt().header(), doctorView.reportDetailsPrompt().body());
        };
    }

    private Command deleteReport() {
        return (x) -> {
            //doctorAccess.removePatientReport(doctorAccess.getPatientReports(patientData)
                    //.get(doctorView.deletePatientReportPrompt(patientData., doctorAccess.getPatientReports(patientData))));
        };
    }
}
