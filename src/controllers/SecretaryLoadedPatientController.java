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
import utilities.TimeUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

/**
 * Controller class that process the commands a secretary would use on a specific patient that they loaded.
 */
public class SecretaryLoadedPatientController extends MenuLoadedPatientController {

    private final PatientData patientData;
    private final PatientManager patientManager;
    private final SecretaryScreenView secretaryScreenView = new SecretaryScreenView();
    private final AppointmentManager appointmentManager;
    private final ContactManager contactManager;
    private final DoctorManager doctorManager;
    private final TimeUtils timeUtils = new TimeUtils();

    /**
     * Creates a new controller for handling the state of the program when a doctor has loaded a specific patient.
     * @param context Context - a reference to the context object, which stores the current controller and allows for
     *                switching between controllers.
     * @param previousController SecretaryController - the previous controller object, allowing you to easily go back.
     * @param patientData PatientData - a data containing the ID and attributes of the current loaded
     *                    patient user.
     */
    public SecretaryLoadedPatientController(Context context, SecretaryController previousController,
                                            PatientData patientData) {
        super(context, previousController);
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
        commands.put("change patient password", ChangePatientPassword());
        commands.put("view appointments", ViewAppointments());
        commands.put("reschedule appointment", RescheduleAppointment());
        commands.put("book appointment", BookAppointment());
        commands.put("cancel appointment", CancelAppointment());

        prescriptionListCommands.AllCommands().forEach((x, y) -> commands.put("view " + x, y));

        commands.putAll(super.AllCommands());
        return commands;
    }

    private Command ChangePatientPassword() {
        return (x) -> {
            PasswordResetDetails passwordResetDetails = secretaryScreenView.resetPatientPasswordPrompt();
            if (patientManager.changeUserPassword(patientData, passwordResetDetails.password())) {
                secretaryScreenView.showResetPasswordSuccessMessage();
            } else {
                secretaryScreenView.showResetPasswordMismatchError();
            }
        };
    }

    private Command ViewAppointments() {
        return (x) -> {
            ArrayList<AppointmentData> appointments = appointmentManager.getPatientAppointments(patientData);
            if (appointments.size() == 0){
                secretaryScreenView.showNoUserAppointmentsMessage();
            }else{
                secretaryScreenView.viewPatientAppointments(contactManager.getContactData(patientData), appointments);
            }
        };
    }

    private Command BookAppointment() {
        return (x) -> {
            // getting the booking date
            LocalDate date = getAppointmentBookingDateWithErrorMessages();
            if (date == null){return;}

            // getting the doctor and checking if they are available.
            DoctorData doctorData = getDoctorDataWithErrorMessages();
            if (doctorData == null){return;}
            viewDoctorSchedule(doctorData, date);
            if (!isDoctorAvailableOnDay(date)){return;}

            // getting appointment start and end DateTimes
            TimeBlockData startEndTimes = getStartEndTimeBlockWithErrorMessages(date);
            if (startEndTimes == null){return;}
            LocalDateTime startTime = startEndTimes.getStartDateTime();
            LocalDateTime endTime = startEndTimes.getEndDateTime();

            AppointmentData appointmentData = appointmentManager.bookAppointment(patientData, doctorData, startTime, endTime);

            if (appointmentData == null){
                secretaryScreenView.showAppointmentBookingError();
            }else{
                secretaryScreenView.showBookAppointmentSuccess(contactManager.getContactData(patientData),
                        contactManager.getContactData(doctorManager.getUserData(appointmentData.getDoctorId())));
            }
        };
    }

    private LocalDate getAppointmentBookingDateWithErrorMessages(){
        LocalDate date = secretaryScreenView.bookAppointmentDayPrompt();
        if (date == null) {
            secretaryScreenView.showInvalidDateError();
            return null;
        }
        return date;
    }

    private DoctorData getDoctorDataWithErrorMessages(){
        String doctor = secretaryScreenView.bookAppointmentDoctorPrompt();
        DoctorData doctorData = doctorManager.getUserData(doctor);
        if (doctorData == null) {
            secretaryScreenView.showDoctorDoesNotExistError();
            return null;
        }
        return doctorData;
    }

    public TimeBlockData getStartEndTimeBlockWithErrorMessages(LocalDate date){
        AppointmentTimeDetails appointmentTimeDetails = secretaryScreenView.bookAppointmentTimePrompt();

        if (appointmentTimeDetails == null){
            secretaryScreenView.showInvalidTimeError();
            return null;
        }

        LocalDateTime startDateTime = LocalDateTime.of(date, appointmentTimeDetails.time());
        LocalDateTime endDateTime = startDateTime.plusMinutes(appointmentTimeDetails.length());

        if (startDateTime.isAfter(endDateTime)){
            secretaryScreenView.showInvalidTimeError();
            return null;
        }

        if (startDateTime.isBefore(LocalDateTime.now())){
            secretaryScreenView.showDayPassedError();
            return null;
        }

        if (spansMultipleDays(startDateTime, endDateTime)){
            secretaryScreenView.showSpanningMultipleDaysError();
            return null;
        }
        return timeUtils.createTimeBlock(startDateTime, endDateTime);
    }


    private Command CancelAppointment() {
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
            }else if (index < 0 || index > data.size() - 1) {
                secretaryScreenView.showDeleteOutOfRangeError();
            } else {
                appointmentManager.removeAppointment(data.get(index));
                secretaryScreenView.showCancelAppointmentSuccess();
            }
        };
    }

    private Command RescheduleAppointment() {
        return (x) -> {
            ArrayList<AppointmentData> appointments = appointmentManager.getPatientAppointments(patientData);
            if (appointments.size() == 0){
                secretaryScreenView.showNoDoctorAppointmentsMessage();
                return;
            }
            ContactData contactData = contactManager.getContactData(patientData);
            Integer index = secretaryScreenView.rescheduleAppointmentPrompt(contactData, appointments);
            if (index == null) {
                secretaryScreenView.showRescheduleNotAnIntegerError("null");
            } else if (index < 0 || index > appointments.size() - 1) {
                secretaryScreenView.showRescheduleOutOfRangeError();
            } else {
                AppointmentData appointmentData = appointments.get(index);

                // check if doctor still exists
                DoctorData doctorData = doctorManager.getUserData(appointments.get(index).getDoctorId());
                if (doctorData == null) {
                    secretaryScreenView.showDoctorNoLongerExists();
                    return;
                }

                // getting the booking date
                LocalDate date = getAppointmentBookingDateWithErrorMessages();
                if (date == null){return;}

                viewDoctorSchedule(doctorData, date, appointmentData);
                if (!isDoctorAvailableOnDay(date)){return;}

                // getting appointment start and end DateTimes
                TimeBlockData startEndTimes = getStartEndTimeBlockWithErrorMessages(date);
                if (startEndTimes == null){return;}
                LocalDateTime startTime = startEndTimes.getStartDateTime();
                LocalDateTime endTime = startEndTimes.getEndDateTime();

                if (appointmentManager.rescheduleAppointment(appointmentData, startTime, endTime)){
                    secretaryScreenView.showRescheduleAppointmentSuccess();
                }else{
                    secretaryScreenView.showRescheduleAppointmentError();
                }
            }
        };
    }

    private void viewDoctorSchedule(DoctorData doctorData, LocalDate date) {
        ArrayList<AppointmentData> appointments = appointmentManager.getSingleDayAppointment(
                doctorData, LocalDate.of(date.getYear(), date.getMonthValue(), date.getDayOfMonth()));
        viewDoctorScheduleGivenAppointments(doctorData, date, appointments);
    }

    private void viewDoctorSchedule(DoctorData doctorData, LocalDate date, AppointmentData excludedAppointment) {
        ArrayList<AppointmentData> appointments = appointmentManager.getSingleDayAppointment(
                doctorData, LocalDate.of(date.getYear(), date.getMonthValue(), date.getDayOfMonth()));
        appointments = appointments.stream().
                filter(a -> !a.getAppointmentId().equals(excludedAppointment.getAppointmentId())).
                collect(Collectors.toCollection(ArrayList::new));
        viewDoctorScheduleGivenAppointments(doctorData, date, appointments);
    }

    private void viewDoctorScheduleGivenAppointments(DoctorData doctorData, LocalDate date,
                                                     ArrayList<AppointmentData> appointments){
        ContactData doctorContact = contactManager.getContactData(doctorData);
        AvailabilityData availabilityData = appointmentManager.getAvailabilityFromDayOfWeek(date.getDayOfWeek());
        if (availabilityData == null){
            secretaryScreenView.showNoAvailabilityError(doctorContact);
            return;
        }
        if (appointments.size() == 0){
            secretaryScreenView.showNoDoctorAppointmentsMessage();
        }else{
            secretaryScreenView.viewDoctorAppointments(doctorContact, appointments);
        }
        secretaryScreenView.viewDoctorAvailability(doctorContact, availabilityData);
    }

    private boolean isDoctorAvailableOnDay(LocalDate date){
        AvailabilityData availabilityData = appointmentManager.getAvailabilityFromDayOfWeek(date.getDayOfWeek());
        return availabilityData != null;
    }

    private boolean spansMultipleDays(LocalDateTime starTime, LocalDateTime endTime){
        return starTime.getDayOfYear() != endTime.getDayOfYear();
    }


}
