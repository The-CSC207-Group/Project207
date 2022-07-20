package controllers;

import controllers.common.PrescriptionListCommands;
import dataBundles.*;
import presenter.response.PrescriptionDetails;
import presenter.screenViews.DoctorScreenView;
import useCases.managers.*;

import java.util.ArrayList;
import java.util.HashMap;

public class DoctorLoadedPatientController extends TerminalController {
    PatientData patientData;
    DoctorData doctorData;
    PrescriptionManager prescriptionManager;
    DoctorScreenView doctorView = new DoctorScreenView();

    DoctorController prev;

    @Override
    public HashMap<String, Command> AllCommands() {
        PrescriptionListCommands prescriptionController = new PrescriptionListCommands(getDatabase(), patientData);

        HashMap<String, Command> commands = super.AllCommands();
        commands.put("unload patient", back(prev));
        commands.put("view appointments", ViewPatientAppointments());
        commands.put("view reports", getReport());
        commands.put("create report", createReport());
        commands.put("delete report", deleteReport());

        HashMap<String, Command> prescriptionCommands = prescriptionController.AllCommands();
        for (String key : commands.keySet()) {
            commands.put("view " + key, prescriptionCommands.get(key));
        }

        commands.put("create prescription", CreatePatientPrescription());
        commands.put("delete prescription", DeletePatientPrescription());
        return commands;
    }

    public DoctorLoadedPatientController(Context parent, DoctorController prev, DoctorData doctorData, PatientData patientData) {
        super(parent);
        this.patientData = patientData;
        this.doctorData = doctorData;
        this.prescriptionManager = new PrescriptionManager(getDatabase());
        this.prev = prev;
    }

    private Command CreatePatientPrescription() {
        return (x) -> {
            PrescriptionDetails prescriptionDetails = doctorView.prescriptionDetailsPrompt();
            prescriptionManager.createPrescription(prescriptionDetails.header(), prescriptionDetails.body(),
                    patientData, doctorData, prescriptionDetails.expiryDate());
            doctorView.showSuccessfullyCreatedPrescription();
        };
    }

    private Command DeletePatientPrescription() {
        ContactManager contactManager = new ContactManager(getDatabase());
        ContactData patientContactData = contactManager.getContactData(patientData);
        ArrayList<PrescriptionData> prescriptionDataList = prescriptionManager.getAllPrescriptions(patientData);
        return (x) -> {
            Integer deleteIndex = doctorView.deletePrescriptionPrompt(patientContactData, prescriptionDataList);
            if (0 <= deleteIndex && deleteIndex < prescriptionDataList.size()) {
                prescriptionManager.removePrescription(prescriptionDataList.get(deleteIndex));
                doctorView.showSuccessfullyDeletedPrescription();
            } else {
                doctorView.showDeletePrescriptionOutOfRangeError();
            }
        };
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
            new ReportManager(getDatabase()).addReport(patientData, doctorData,
                    doctorView.reportDetailsPrompt().header(), doctorView.reportDetailsPrompt().body());
        };
    }

    private Command deleteReport() {
        return (x) -> {
            Integer deleteIndex = doctorView.deleteReportPrompt(new ContactManager(getDatabase()).getContactData(patientData), new
                    ReportManager(getDatabase()).getReportDataBundlesFromPatientDataBundle(patientData));
            getDatabase().getReportDatabase().remove(deleteIndex);
        };
    }
}
