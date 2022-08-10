package controllers;

import controllers.common.PrescriptionListCommands;
import dataBundles.*;
import presenters.response.PasswordResetDetails;
import presenters.screenViews.SecretaryScreenView;
import useCases.PatientManager;

import java.util.LinkedHashMap;

/**
 * Controller class that process the commands a secretary would use on a specific patient that they loaded.
 */
public class SecretaryLoadedPatientController extends MenuLoadedPatientController {

    private final PatientData patientData;
    private final PatientManager patientManager;
    private final SecretaryScreenView secretaryScreenView = new SecretaryScreenView();

    /* PHASE 2 ATTRIBUTES
    private final AppointmentManager appointmentManager;
    private final DoctorManager doctorManager;
    private final ContactManager contactManager; */

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

        /* PHASE 2 INSTANTIATIONS
        this.appointmentManager = new AppointmentManager(getDatabase());
        this.doctorManager = new DoctorManager(getDatabase());
        this.contactManager = new ContactManager(getDatabase()); */
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

        /* PENDING IMPLEMENTATION IN PHASE 2
        commands.put("view appointments", ViewAppointments());
        commands.put("reschedule appointment", RescheduleAppointment());
        commands.put("book appointment", BookAppointment());
        commands.put("cancel appointment", CancelAppointment()); */

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


    /* PENDING IMPLEMENTATION IN PHASE 2


    private Command ViewAppointments() {
        return (x) -> {
            ArrayList<AppointmentData> appointments = appointmentManager.getPatientAppointments(patientData);
            secretaryScreenView.viewAppointments(contactManager.getContactData(patientData), appointments);

        };
    }

    private Command BookAppointment() {
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
            ArrayList<AppointmentData> appointments = appointmentManager.getScheduleData(doctorData,
                    LocalDate.of(date.getYear(), date.getMonthValue(), date.getDayOfMonth()));
            if (appointments.size() == 0) {
                secretaryScreenView.showNoAvailableAppointmentDayError();
                return;
            }
            viewDoctorSchedule(doctorData, date);
            AppointmentData appointment = bookAppointmentTime(doctorData, date);
            if (appointment == null) {
                secretaryScreenView.showAppointmentConflictError();
                return;
            }
            secretaryScreenView.showBookAppointmentSuccess(contactManager.getContactData(patientData),
                    contactManager.getContactData(doctorData));

        };
    }

    private Command CancelAppointment() {
        return (x) -> {
            ArrayList<AppointmentData> data = appointmentManager.getPatientAppointments(patientData);
            ContactData contactData = contactManager.getContactData(patientData);
            Integer index = secretaryScreenView.deleteAppointmentPrompt(contactData, data);
            if (index == null) {
                secretaryScreenView.showDeleteNotAnIntegerError("null");
            } else if (index < 0 || index > data.size()) {
                secretaryScreenView.showDeleteOutOfRangeError();
            } else {
                appointmentManager.removeAppointment(data.get(index));
            }
        };
    }

    private Command RescheduleAppointment() {
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
                appointmentManager.rescheduleAppointment(
                        appointments.get(index), day.getYear(), day.getMonthValue(),
                        day.getDayOfMonth(), time.time().getHour(), time.time().getMinute(), time.length());
            }
        };
    }

    private void ViewDoctorSchedule(DoctorData doctorData, LocalDate date) {
        secretaryScreenView.viewAppointments(
                contactManager.getContactData(doctorData),
                appointmentManager.getScheduleData(
                        doctorData, LocalDate.of(date.getYear(), date.getMonthValue(), date.getDayOfMonth())));
    }

    private AppointmentData BookAppointmentTime(DoctorData doctorData, LocalDate date) {
        AppointmentTimeDetails appointmentTimeDetails = secretaryScreenView.bookAppointmentTimePrompt();
        return appointmentManager.bookAppointment(
                patientData, doctorData, date.getYear(), date.getMonthValue(), date.getDayOfMonth(),
                appointmentTimeDetails.time().getHour(),
                appointmentTimeDetails.time().getMinute(),
                appointmentTimeDetails.length());
    } */

}
