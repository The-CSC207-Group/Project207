package controllers;

import controllers.common.PrescriptionListCommands;
import dataBundles.*;
import presenters.response.PrescriptionDetails;
import presenters.response.ReportDetails;
import presenters.screenViews.DoctorScreenView;
import useCases.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Controller class that process the commands a doctor would use on a specific patient that they loaded.
 */
public class DoctorLoadedPatientController extends MenuLoadedPatientController {

    private final PatientData patientData;
    private final DoctorData doctorData;
    private final PrescriptionManager prescriptionManager;
    private final DoctorScreenView doctorScreenView = new DoctorScreenView();
    private final ReportManager reportManager;
    private final DoctorManager doctorManager;
    private final ContactManager contactManager;


    /**
     * Creates a new controller for handling the state of the program when a doctor has loaded a specific patient.
     *
     * @param context            Context - a reference to the context object, which stores the current controller and
     *                           allows for switching between controllers.
     * @param previousController DoctorController - stores the previous controller, allows you to easily go back to it
     *                           via the back command.
     * @param doctorData         DoctorData - a data containing the ID and attributes of the current doctor user.
     * @param patientData        PatientData - a data containing the ID and attributes of the current loaded patient
     *                           user.
     */
    public DoctorLoadedPatientController(Context context, DoctorController previousController, DoctorData doctorData,
                                         PatientData patientData) {
        super(context, previousController);
        this.patientData = patientData;
        this.doctorData = doctorData;
        this.prescriptionManager = new PrescriptionManager(getDatabase());
        this.reportManager = new ReportManager(getDatabase());
        this.doctorManager = new DoctorManager(getDatabase());
        this.contactManager = new ContactManager(getDatabase());
    }

    /**
     * Creates a Linked hashmap of all string representations of doctor loaded patient commands mapped to the method
     * that each command calls.
     *
     * @return LinkedHashMap<String, Command> - ordered HashMap of strings mapped to their respective doctor loaded
     * patient commands.
     */
    @Override
    public LinkedHashMap<String, Command> allCommands() {
        PrescriptionListCommands prescriptionListCommands = new PrescriptionListCommands(getDatabase(), patientData);
        LinkedHashMap<String, Command> commands = new LinkedHashMap<>();
        commands.put("view appointments", ViewPatientAppointments());
        commands.put("view reports", ViewPatientReports());
        commands.put("create report", CreatePatientReport());
        commands.put("delete report", DeletePatientReport());

        prescriptionListCommands.AllCommands().forEach((x, y) -> commands.put("view " + x, y));

        commands.put("create prescription", CreatePatientPrescription());
        commands.put("delete prescription", DeletePatientPrescription());

        commands.putAll(super.allCommands());
        return commands;
    }

    private Command CreatePatientPrescription() {
        return (x) -> {
            PrescriptionDetails prescriptionDetails = doctorScreenView.prescriptionDetailsPrompt();
            if (prescriptionDetails != null) {
                prescriptionManager.createPrescription(prescriptionDetails.header(), prescriptionDetails.body(),
                        patientData, doctorData, prescriptionDetails.expiryDate());
                doctorScreenView.showSuccessfullyCreatedPrescription();
            } else {
                doctorScreenView.showInvalidPrescriptionDateError();
            }
        };
    }

    private Command DeletePatientPrescription() {
        return (x) -> {
            ContactData patientContactData = contactManager.getContactData(patientData);
            ArrayList<PrescriptionData> prescriptionDataList = prescriptionManager.getAllPrescriptions(patientData);
            if (prescriptionDataList == null || prescriptionDataList.size() == 0) {
                doctorScreenView.showNoPrescriptionError();
                return;
            }
            Integer deleteIndex = doctorScreenView.deletePrescriptionPrompt(patientContactData, prescriptionDataList);
            if (IsValidInput(deleteIndex, prescriptionDataList.size())) {
                return;
            }
            prescriptionManager.removePrescription(prescriptionDataList.get(deleteIndex));
            doctorScreenView.showSuccessfullyDeletedPrescription();
        };
    }


    private Command ViewPatientAppointments() {
        return (x) -> {
            ArrayList<AppointmentData> appointments = new AppointmentManager(
                    getDatabase()).getPatientAppointments(patientData);
            if (appointments.size() == 0) {
                doctorScreenView.showNoAppointmentsMessage();
            } else {
                doctorScreenView.viewAppointments(appointments);
            }
        };
    }

    private Command ViewPatientReports() {
        return (x) -> {
            ArrayList<ReportData> reportData = reportManager.getReportData(patientData);
            if (reportData == null || reportData.size() == 0) {
                doctorScreenView.showNoReportsError();
                return;
            }
            doctorScreenView.viewAllReports(reportData);
            Integer targetReport = doctorScreenView.viewReportPrompt();
            if (IsValidInput(targetReport, reportData.size())) {
                return;
            }
            ReportData selectedReport = reportData.get(targetReport);
            DoctorData reportDoctor = doctorManager.getUserData(selectedReport.getDoctorId());
            if (reportDoctor != null) {
                ContactData doctorContact = contactManager.getContactData(reportDoctor);
                doctorScreenView.viewReport(selectedReport, doctorContact);
            } else {
                doctorScreenView.viewReport(selectedReport, null);
            }
        };
    }

    private Command CreatePatientReport() {
        return (x) -> {
            ReportDetails reportDetails = doctorScreenView.reportDetailsPrompt();
            reportManager.addReport(patientData, doctorData, reportDetails.header(), reportDetails.body());
            doctorScreenView.showReportCreationSuccess();
        };
    }

    private Command DeletePatientReport() {
        return (x) -> {
            ArrayList<ReportData> reportData = reportManager.getReportData(patientData);
            if (reportData == null || reportData.size() == 0) {
                doctorScreenView.showNoReportsError();
                return;
            }

            Integer deleteIndex = doctorScreenView.deleteReportPrompt(new ContactManager(getDatabase())
                    .getContactData(patientData), reportData);

            if (IsValidInput(deleteIndex, reportData.size())) {
                return;
            }
            reportManager.deleteReport(reportData.get(deleteIndex));
            doctorScreenView.showReportDeletionSuccess();

        };
    }

    private boolean IsValidInput(Integer deleteIndex, Integer size) {
        if (deleteIndex == null) {
            doctorScreenView.showNotIntegerError();
            return true;
        }
        if (deleteIndex < 0 || deleteIndex >= size) {
            doctorScreenView.showOutOfRangeError();
            return true;
        }
        return false;
    }
}
