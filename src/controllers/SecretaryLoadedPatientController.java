package controllers;

import controllers.common.PrescriptionListCommands;
import dataBundles.*;
import presenters.response.AppointmentTimeDetails;
import presenters.response.PasswordResetDetails;
import presenters.screenViews.SecretaryScreenView;
import useCases.AppointmentManager;
import useCases.ContactManager;
import useCases.DoctorManager;
import useCases.PatientManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Controller class that process the commands a secretary would use on a specific patient that they loaded.
 */
public class SecretaryLoadedPatientController extends TerminalController {

    private final PatientData patientData;
    private final SecretaryController secretaryController;
    private final PatientManager patientManager;
    private final SecretaryScreenView secretaryScreenView = new SecretaryScreenView();


    private final AppointmentManager appointmentManager;
    private final ContactManager contactManager;

    private final DoctorManager doctorManager;


    /**
     * Creates a new controller for handling the state of the program when a doctor has loaded a specific patient.
     * @param context Context - a reference to the context object, which stores the current controller and allows for
     *                switching between controllers.
     * @param secretaryController SecretaryController - the previous controller object, allowing you to easily go back.
     * @param patientData PatientData - a data containing the ID and attributes of the current loaded
     *                    patient user.
     */
    public SecretaryLoadedPatientController(Context context, SecretaryController secretaryController,
                                            PatientData patientData) {
        super(context);
        this.secretaryController = secretaryController;
        this.patientData = patientData;
        this.patientManager = new PatientManager(getDatabase());


        this.appointmentManager = new AppointmentManager(getDatabase());

        this.contactManager = new ContactManager(getDatabase());

        this.doctorManager = new DoctorManager(getDatabase());
    }

    /**
     * Creates a linked hashmap of all string representations of doctor loaded patient commands mapped to the method
     * that each command calls.
     * @return LinkedHashMap<String, Command> - ordered HashMap of strings mapped to their respective doctor loaded
     * patient commands.
     */
    @Override
    public LinkedHashMap<String, Command> AllCommands() {
        LinkedHashMap<String, Command> commands = new LinkedHashMap<>();
        PrescriptionListCommands prescriptionListCommands = new PrescriptionListCommands(getDatabase(), patientData);
        commands.put("change patient password", changePatientPassword());
        commands.put("unload patient", Back(secretaryController));
        commands.put("view appointments", viewAppointments());
        commands.put("reschedule appointment", rescheduleAppointment());
        commands.put("book appointment", bookAppointment());
        commands.put("cancel appointment", cancelAppointment());

        prescriptionListCommands.AllCommands().forEach((x, y) -> commands.put("view " + x, y));
        commands.putAll(super.AllCommands());
        return commands;
    }

    private Command changePatientPassword() {
        return (x) -> {
            PasswordResetDetails passwordResetDetails = secretaryScreenView.resetPatientPasswordPrompt();
            if (patientManager.changeUserPassword(patientData, passwordResetDetails.password())) {
                secretaryScreenView.showResetPasswordSuccessMessage();
            } else {
                secretaryScreenView.showResetPasswordMismatchError();
            }
        };
    }

    private Command viewAppointments() {
        return (x) -> {
            ArrayList<AppointmentData> appointments = appointmentManager.getPatientAppointments(patientData);
            if (appointments.size() == 0){
                secretaryScreenView.showNoUserAppointmentsMessage();
            }else{
                secretaryScreenView.viewAppointments(contactManager.getContactData(patientData), appointments);
            }
        };
    }

    private Command bookAppointment() {
        return (x) -> {
            LocalDate date = secretaryScreenView.bookAppointmentDayPrompt();
            if (date == null) {
                secretaryScreenView.showInvalidDateError();
                return;
            }
            String doctor = secretaryScreenView.bookAppointmentDoctorPrompt();
            DoctorData doctorData = doctorManager.getUserData(doctor);
            if (doctorData == null) {
                secretaryScreenView.showDoctorDoesNotExistError();
                return;
            }
            viewDoctorSchedule(doctorData, date);
            if (!isDoctorAvailableOnDay(date)){return;}
            bookAppointmentTime(doctorData, date);

        };
    }

    private Command cancelAppointment() {
        return (x) -> {
            ArrayList<AppointmentData> data = appointmentManager.getPatientAppointments(patientData);
            if (data.size() == 0){
                secretaryScreenView.showNoUserAppointmentsMessage();
                return;
            }
            ContactData contactData = contactManager.getContactData(patientData);
            Integer index = secretaryScreenView.deleteAppointmentPrompt(contactData, data);
            if (index == null) {
                secretaryScreenView.showDeleteNotAnIntegerError("null");
            }else if (index < 0 || index > data.size()) {
                secretaryScreenView.showDeleteOutOfRangeError();
            } else {
                appointmentManager.removeAppointment(data.get(index));
                secretaryScreenView.showCancelAppointmentSuccess();
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
            } else if (index >= 0 && index > appointments.size()) {
                secretaryScreenView.showRescheduleOutOfRangeError();
            } else {
                LocalDate day = secretaryScreenView.bookAppointmentDayPrompt();
                AppointmentTimeDetails time = secretaryScreenView.bookAppointmentTimePrompt();
                LocalDateTime startTime = LocalDateTime.of(day.getYear(), day.getMonthValue(), day.getDayOfMonth(),
                        time.time().getHour(), time.time().getMinute());
                appointmentManager.rescheduleAppointment(
                        appointments.get(index), startTime, startTime.plusMinutes(time.length()));
            }
        };
    }

    private void viewDoctorSchedule(DoctorData doctorData, LocalDate date) {
        ContactData doctorContact = contactManager.getContactData(doctorData);

        ArrayList<AppointmentData> appointments = appointmentManager.getSingleDayAppointment(
                doctorData, LocalDate.of(date.getYear(), date.getMonthValue(), date.getDayOfMonth()));


        AvailabilityData availabilityData = appointmentManager.getAvailabilityFromDayOfWeek(date.getDayOfWeek());
        if (availabilityData == null){
            secretaryScreenView.showNoAvailabilityError(doctorContact);
            return;
        }
        if (appointments.size() == 0){
            secretaryScreenView.showNoDoctorAppointmentsMessage();
        }else{
            secretaryScreenView.viewAppointments(doctorContact, appointments);
        }
        secretaryScreenView.viewDoctorAvailability(doctorContact, availabilityData);
    }
    private boolean isDoctorAvailableOnDay(LocalDate date){
        AvailabilityData availabilityData = appointmentManager.getAvailabilityFromDayOfWeek(date.getDayOfWeek());
        return availabilityData != null;
    }

    private void bookAppointmentTime(DoctorData doctorData, LocalDate date) {
        AppointmentTimeDetails appointmentTimeDetails = secretaryScreenView.bookAppointmentTimePrompt();

        if (appointmentTimeDetails == null){
            secretaryScreenView.showInvalidTimeError();
            return;
        }

        LocalDateTime startDateTime = LocalDateTime.of(date, appointmentTimeDetails.time());
        LocalDateTime endDateTime = startDateTime.plusMinutes(appointmentTimeDetails.length());

        if (spansMultipleDays(startDateTime, endDateTime)){
            secretaryScreenView.showSpanningMultipleDaysError();
            return;
        }

        AppointmentData appointmentData = appointmentManager.bookAppointment(patientData, doctorData, startDateTime, endDateTime);

        if (appointmentData == null){
            secretaryScreenView.showAppointmentBookingError();
        }else{
            secretaryScreenView.showBookAppointmentSuccess(contactManager.getContactData(patientData),
                    contactManager.getContactData(doctorData));
        }
    }

    private boolean spansMultipleDays(LocalDateTime starTime, LocalDateTime endTime){
        return starTime.getDayOfYear() != endTime.getDayOfYear();
    }


}
