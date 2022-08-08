package controllers;

import dataBundles.*;
import entities.Doctor;
import presenters.screenViews.DoctorScreenView;
import useCases.*;
import utilities.TimeUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Controller class that processes the commands that a patient passes in.
 */
public class DoctorController extends UserController<Doctor> {

    private final DoctorScreenView doctorScreenView = new DoctorScreenView();
    private final DoctorData doctorData;
    private final DoctorController currentController = this;
    private final AppointmentManager appointmentManager;

    private final TimeUtils timeUtils = new TimeUtils();

    /**
     * Creates a new controller for handling the state of the program when a doctor is signed in.
     *
     * @param context    Context - a reference to the context object, which stores the current controller and allows for
     *                   switching between controllers.
     * @param doctorData DoctorData - a data  containing the ID and attributes of the current doctor user.
     */
    public DoctorController(Context context, DoctorData doctorData) {
        super(context, doctorData, new DoctorManager(context.getDatabase()), new DoctorScreenView());
        this.doctorData = doctorData;
        this.appointmentManager = new AppointmentManager(context.getDatabase());
    }

    /**
     * Creates a linked hashmap of all string representations of doctor commands mapped to the method that each
     * command calls.
     *
     * @return LinkedHashMap<String, Command> - ordered HashMap of strings mapped to their respective doctor commands.
     */
    @Override
    public LinkedHashMap<String, Command> AllCommands() {
        LinkedHashMap<String, Command> commands = new LinkedHashMap<>();
        commands.put("load patient", LoadPatient());
        commands.put("view appointments", ViewAppointments());
        commands.put("show schedule", ViewSchedule());

        commands.putAll(super.AllCommands());
        return commands;
    }

    private Command LoadPatient() {
        PatientManager patientManager = new PatientManager(getDatabase());
        return (x) -> {
            String patientUsername = doctorScreenView.loadPatientPrompt();
            PatientData loadedPatientData = patientManager.getUserData(patientUsername);
            if (loadedPatientData != null) {
                doctorScreenView.showSuccessLoadingPatient(new ContactManager(getDatabase()).getContactData(loadedPatientData));
                changeCurrentController(new DoctorLoadedPatientController(
                        getContext(), currentController, doctorData, loadedPatientData));
            } else {
                doctorScreenView.showErrorLoadingPatient();
            }
        };
    }

    private Command ViewAppointments() {
        return (x) -> {
            ArrayList<AppointmentData> appointments = appointmentManager.getDoctorAppointments(doctorData);
            if (appointments.size() == 0){
                doctorScreenView.showNoAppointmentsMessage();
            }else{
                doctorScreenView.viewAppointments(appointments);
            }
        };
    }

    private Command ViewSchedule() {
        return (x) -> {
            LocalDate viewDate = doctorScreenView.viewSchedulePrompt();
            doctorScreenView.viewAppointments(new AppointmentManager(getDatabase()).getSingleDayAppointment(doctorData, viewDate));
        };
    }

    private Command ViewAllDoctorAppointments() {
        return (x) -> doctorScreenView.viewAppointments(new AppointmentManager(getDatabase())
                .getDoctorAppointments(doctorData));
    }
}
