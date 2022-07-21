package controllers;

import dataBundles.DoctorData;
import dataBundles.PatientData;
import dataBundles.SecretaryData;
import entities.Secretary;
import presenter.response.UserCredentials;
import presenter.screenViews.SecretaryScreenView;
import useCases.managers.*;

import java.util.HashMap;

/**
 * Controller class that processes the commands that a secretary passes in.
 */
public class SecretaryController extends UserController<Secretary> {
    private final SecretaryScreenView secretaryScreenView = new SecretaryScreenView();
    private final PatientManager patientManager;
    private final ContactManager contactManager;
    private final DoctorManager doctorManager;
    private final AppointmentManager appointmentManager;
    private SecretaryController self = this;

    /**
     * Creates a new controller for handling the state of the program when a secretary is signed in.
     * @param context a reference to the context object, which stores the current controller and allows for switching
     *                between controllers.
     * @param secretaryData a data bundle containing the ID and attributes of the current secretary user.
     */
    public SecretaryController(Context context, SecretaryData secretaryData) {
        super(context, secretaryData, new SecretaryManager(context.getDatabase()), new SecretaryScreenView());
        this.appointmentManager = new AppointmentManager(getDatabase());
        this.doctorManager = new DoctorManager(getDatabase());
        this.contactManager = new ContactManager(getDatabase());
        this.patientManager = new PatientManager(getDatabase());
    }

    /**
     * Creates a hashmap of all string representations of secretary commands mapped to the method that each
     * command calls.
     * @return HashMap of strings mapped to their respective secretary commands.
     */
    @Override
    public HashMap<String, Command> AllCommands() {
        HashMap<String, Command> commands = super.AllCommands();
        commands.put("create patient", createPatientAccount());
        commands.put("load patient", LoadPatient());
        commands.put("delete patient", deletePatient());
        commands.put("add availability", addDoctorAvailability());
        commands.put("delete availability", removeDoctorAvailability());
        commands.put("add absence", addDoctorAbsence());

        return commands;
    }

    private Command LoadPatient() {
        return (x) -> {
            String username = secretaryScreenView.loadPatientPrompt();
            PatientData patientData = patientManager.getUserData(username);
            SecretaryController currentController = this;
            if (patientData != null) {
                changeCurrentController(new SecretaryLoadedPatientController(getContext(), currentController,
                        patientData, self));
                secretaryScreenView.showSuccessLoadingPatient(contactManager.getContactData(patientData));
            } else {
                secretaryScreenView.showErrorLoadingPatient();
            }
        };
    }

    private Command createPatientAccount() {
        return (x) -> {
            UserCredentials userCredentials = secretaryScreenView.registerPatientAccount();
            if (!patientManager.doesUserExist(userCredentials.username())) {
                patientManager.createPatient(userCredentials.username(), userCredentials.password());
                secretaryScreenView.showRegisterPatientSuccess();
            } else {
                secretaryScreenView.showRegisterPatientError();
            }

        };
    }

    private Command deletePatient() {
        return (x) -> {
            String patient = secretaryScreenView.showDeletePatientPrompt();
            if (patientManager.deleteUser(patient)) {
                secretaryScreenView.showDeletePatientSuccess();
            } else {
                secretaryScreenView.showFailedToDeletePatientError();
            }

        };
    }

    private Command addDoctorAvailability() {
        return (x) -> {
            String doctor = secretaryScreenView.getTargetDoctor();
            DoctorData doctorData = doctorManager.getUserData(doctor);
            appointmentManager.newAvailability(doctorData,
                    secretaryScreenView.getDay(),
                    secretaryScreenView.addDoctorAvailabilityTime().getHour(),
                    secretaryScreenView.addDoctorAvailabilityTime().getMinute(),
                    secretaryScreenView.addDoctorAvailableLength());
        };

    }

    private Command removeDoctorAvailability() {
        return (x) -> {
//            String doctor = secretaryScreenView.getTargetDoctor();
//            DoctorData doctorData = doctorManager.getUserData(doctor);
//
//            ArrayList<Availability> availableTimes =
//                    appointmentManager.getAvailabilityFromDayOfWeek(doctorData.getId(), secretaryScreenView.getDay());
//            secretaryScreenView.viewAvailabilityFull(availableTimes);
//
//            appointmentManager.removeAvailability(doctorData,
//                    availableTimes.get(secretaryScreenView.getIndexToRemove()));
        };
    }

    private Command addDoctorAbsence() {
        return (x) -> {
            String doctor = secretaryScreenView.getTargetDoctor();
            DoctorData doctorData = doctorManager.getUserData(doctor);

            appointmentManager.addAbsence(doctorData, secretaryScreenView.addZoneDateTimeStart(),
                    secretaryScreenView.addZoneDateTimeEnd());
        };
    }

}
