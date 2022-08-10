package controllers;

import dataBundles.PatientData;
import dataBundles.SecretaryData;
import entities.Secretary;
import presenters.response.UserCredentials;
import presenters.screenViews.SecretaryScreenView;
import useCases.PatientManager;
import useCases.SecretaryManager;

import java.util.LinkedHashMap;

/**
 * Controller class that processes the commands that a secretary passes in.
 */
public class SecretaryController extends UserController<Secretary> {

    private final SecretaryScreenView secretaryScreenView = new SecretaryScreenView();
    private final PatientManager patientManager;

    /**
     * Creates a new controller for handling the state of the program when a secretary is signed in.
     *
     * @param context       Context - a reference to the context object, which stores the current controller and allows
     *                      for switching between controllers.
     * @param secretaryData SecretaryData - a data containing the ID and attributes of the current secretary
     *                      user.
     */
    public SecretaryController(Context context, SecretaryData secretaryData) {
        super(context, secretaryData, new SecretaryManager(context.getDatabase()), new SecretaryScreenView());
        this.patientManager = new PatientManager(getDatabase());
    }

    /**
     * Creates a linked hashmap of all string representations of secretary commands mapped to the method that each
     * command calls.
     *
     * @return LinkedHashMap<String, Command> - ordered HashMap of strings mapped to their respective secretary
     * commands.
     */
    @Override
    public LinkedHashMap<String, Command> AllCommands() {
        LinkedHashMap<String, Command> commands = new LinkedHashMap<>();
        commands.put("create patient", CreatePatientAccount());
        commands.put("load patient", LoadPatient());
        commands.put("delete patient", DeletePatient());

        commands.putAll(super.AllCommands());
        return commands;
    }

    private Command LoadPatient() {
        return (x) -> {
            String username = secretaryScreenView.loadPatientPrompt();
            PatientData patientData = patientManager.getUserData(username);
            SecretaryController currentController = this;
            if (patientData != null) {
                secretaryScreenView.showSuccessLoadingPatient(patientData);
                changeCurrentController(new SecretaryLoadedPatientController(getContext(), currentController,
                        patientData));
            } else {
                secretaryScreenView.showErrorLoadingPatient();
            }
        };
    }

    private Command CreatePatientAccount() {
        return (x) -> {
            try {
                UserCredentials userCred = secretaryScreenView.registerPatientPrompt();
                PatientData patient = patientManager.createPatient(userCred.username(), userCred.password());
                if (patient == null) {
                    secretaryScreenView.showPatientUsernameInUseError();
                } else {
                    secretaryScreenView.showRegisterPatientSuccess();
                }
            } catch (IllegalArgumentException iae) {
                secretaryScreenView.showIncorrectPatientFormatError();
            }
        };
    }

    private Command DeletePatient() {
        return (x) -> {
            String patient = secretaryScreenView.showDeletePatientPrompt();
            if (patientManager.deleteUser(patient)) {
                secretaryScreenView.showDeletePatientSuccess();
            } else {
                secretaryScreenView.showFailedToDeletePatientError();
            }

        };
    }

}
