package controllers;

import dataBundles.*;
import presenter.response.AppointmentTimeDetails;
import presenter.response.PasswordResetDetails;
import presenter.screenViews.SecretaryScreenView;
import presenter.entityViews.PrescriptionView;
import useCases.managers.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Controller class that process the commands a secretary would use on a specific patient that they loaded.
 */
public class SecretaryLoadedPatientController extends TerminalController {
    private final PatientData patientData;
    private final SecretaryController secretaryController;
    private final PrescriptionView prescriptionView = new PrescriptionView();
    private final AppointmentManager appointmentManager;
    private final PrescriptionManager prescriptionManager;
    private final DoctorManager doctorManager;
    private final PatientManager patientManager;
    private final ContactManager contactManager;
    private final SecretaryScreenView secretaryScreenView = new SecretaryScreenView();

    /**
     * Creates a new controller for handling the state of the program when a secretary has loaded a specific patient.
     * @param context a reference to the context object, which stores the current controller and allows for switching
     *                between controllers.
     * @param secretaryController the previous controller object, allowing you to easily go back.
     * @param patientData a data bundle containing the ID and attributes of the current loaded patient user.
     */
    public SecretaryLoadedPatientController(Context context, SecretaryController secretaryController,
                                            PatientData patientData){
        super(context);
        this.secretaryController = secretaryController;
        this.patientData = patientData;


        this.appointmentManager = new AppointmentManager(getDatabase());
        this.prescriptionManager = new PrescriptionManager(getDatabase());
        this.doctorManager = new DoctorManager(getDatabase());
        this.patientManager = new PatientManager(getDatabase());
        this.contactManager = new ContactManager(getDatabase());
    }

    /**
     * Creates a hashmap of all string representations of secretary loaded patient commands mapped to the method that each
     * command calls.
     * @return HashMap of strings mapped to their respective secretary loaded patient commands.
     */
    @Override
    public HashMap<String, Command> AllCommands() {
        HashMap<String, Command> commands = super.AllCommands();
        commands.put("view active prescription", viewActivePrescription());
        commands.put("view all prescriptions", viewPrescriptionHistory());
        commands.put("view appointments", viewAppointments());
        commands.put("change password", changePatientPassword());
        commands.put("unload patient", back(secretaryController));
        commands.put("reschedule appointment", rescheduleAppointment());
        commands.put("book appointment", bookAppointment());
        commands.put("cancel appointment", cancelAppointment());
        commands.put("active prescription detail", viewActivePrescriptionsDetailed());
        commands.put("all prescription detail", viewAllPrescriptionsDetailed());
        return commands;
    }

    private Command viewActivePrescription() {
        return (x) -> {
            ArrayList<PrescriptionData> prescriptions = prescriptionManager.getAllActivePrescriptions(patientData);
            for (PrescriptionData prescriptionData : prescriptions) {
                prescriptionView.viewFull(prescriptionData);
            }

        };
    }

    private Command viewPrescriptionHistory() {
        return (x) -> {
            ArrayList<PrescriptionData> prescriptions = prescriptionManager.getAllPrescriptions(patientData);
            for (PrescriptionData prescriptionData : prescriptions) {
                prescriptionView.viewFull(prescriptionData);
            }
        };
    }

    private Command viewAppointments() {
        return (x) -> {
            ArrayList<AppointmentData> appointments = appointmentManager.getPatientAppointments(patientData);
            secretaryScreenView.viewAppointments(contactManager.getContactData(patientData), appointments);

        };
    }

    private Command changePatientPassword() {
        return (x) -> {
            PasswordResetDetails passwordResetDetails = secretaryScreenView.resetPasswordPrompt();
            if (patientManager.changeUserPassword(patientData, passwordResetDetails.password())) {
                secretaryScreenView.showResetPasswordSuccessMessage();
            } else {
                secretaryScreenView.showResetPasswordMismatchError();
            }
        };
    }

    private Command bookAppointment() {
        return (x) -> {
            LocalDate date = secretaryScreenView.bookAppointmentDayPrompt();
            if (date == null) {
                secretaryScreenView.showInvalidDateError();
            } else {
                String doctor = secretaryScreenView.bookAppointmentDoctorPrompt();
                DoctorData doctorData = doctorManager.getUserData(doctor);
                if (doctorData == null) {
                    secretaryScreenView.showDoctorDoesNotExistError();
                } else {
                    ArrayList<AppointmentData> appointments = appointmentManager.getScheduleData(doctorData,
                            LocalDate.of(date.getYear(), date.getMonthValue(), date.getDayOfMonth()));
                    if (appointments == null) {
                        secretaryScreenView.showNoAvailableAppointmentDayError();
                    } else {
                        viewDoctorSchedule(doctorData, date);
                        AppointmentData appointment = bookAppointmentTime(doctorData, date);
                        if (appointment == null) {
                            secretaryScreenView.showAppointmentConflictError();
                        } else {
                            secretaryScreenView.showBookAppointmentSuccess(contactManager.getContactData(patientData),
                                    contactManager.getContactData(doctorData));
                        }
                    }
                }
            }
        };
    }

    private Command cancelAppointment() {
        return (x) -> {
            ArrayList<AppointmentData> data = appointmentManager.getPatientAppointments(patientData);
            ContactData contactData = contactManager.getContactData(patientData);
            Integer index = secretaryScreenView.deleteAppointmentPrompt(contactData, data);
            if (index == null) {
                secretaryScreenView.showDeleteNotAnIntegerError("null");
            } else if (index >= data.size()) {
                secretaryScreenView.showDeleteOutOfRangeError();
            } else {
                appointmentManager.removeAppointment(data.get(index));
            }

        };
    }

    private Command rescheduleAppointment() {
        return (x) -> {
            ArrayList<AppointmentData> appointments = appointmentManager.getPatientAppointments(patientData);
            ContactData contactData = contactManager.getContactData(patientData);
            Integer index = secretaryScreenView.rescheduleAppointmentPrompt(contactData, appointments);
            if (index == null) {
                secretaryScreenView.showRescheduleNotAnIntegerError("null");
            } else if (index >= appointments.size()) {
                secretaryScreenView.showRescheduleOutOfRangeError();
            } else {
                LocalDate day = secretaryScreenView.bookAppointmentDayPrompt();
                AppointmentTimeDetails time = secretaryScreenView.bookAppointmentTimePrompt();
                appointmentManager.rescheduleAppointment(
                        appointments.get(index), day.getYear(), day.getMonthValue(),
                        day.getDayOfMonth(), time.time().getHour(), time.time().getMinute(), time.length());

            }
        };
    }

    private void viewDoctorSchedule(DoctorData doctorData, LocalDate date) {
        secretaryScreenView.viewAppointments(
                contactManager.getContactData(doctorData),
                appointmentManager.getScheduleData(
                        doctorData, LocalDate.of(date.getYear(), date.getMonthValue(), date.getDayOfMonth())));
    }

    private AppointmentData bookAppointmentTime(DoctorData doctorData, LocalDate date) {
        AppointmentTimeDetails appointmentTimeDetails = secretaryScreenView.bookAppointmentTimePrompt();

        return appointmentManager.bookAppointment(
                patientData, doctorData, date.getYear(), date.getMonthValue(), date.getDayOfMonth(),
                appointmentTimeDetails.time().getHour(),
                appointmentTimeDetails.time().getMinute(),
                appointmentTimeDetails.length());
    }


    private Command viewActivePrescriptionsDetailed() {
        return (x) -> {
            secretaryScreenView.viewPrescriptionsDetailed(prescriptionManager.getAllActivePrescriptions(patientData));
        };

    }

    private Command viewAllPrescriptionsDetailed() {
        return (x) -> {
            secretaryScreenView.viewPrescriptionsDetailed(prescriptionManager.getAllPrescriptions(patientData));
        };
    }
}
