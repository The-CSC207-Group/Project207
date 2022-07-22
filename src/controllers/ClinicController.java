package controllers;

import dataBundles.ClinicData;
import entities.Admin;
import presenter.screenViews.ClinicScreenView;
import useCases.managers.ClinicManager;

import java.util.LinkedHashMap;

/**
 * Controller class that processes the commands that an admin performs on a clinic's information.
 */
public class ClinicController extends TerminalController {

    private final ClinicScreenView clinicScreenView;
    private final ClinicManager clinicManager;
    private final UserController<Admin> previousController;

    /**
     * Creates a clinic controller object that handles the commands an admin performs on the clinic information.
     * @param context Context - a reference to the context object, which stores the current controller and allows for
     *                switching between controllers.
     * @param previousController UserController<Admin> - the object of the admin controller that switched into this
     *                           clinic controller object.
     */
    public ClinicController(Context context, UserController<Admin> previousController) {
        super(context);
        this.clinicManager = new ClinicManager(getDatabase());
        this.clinicScreenView = new ClinicScreenView();
        this.previousController = previousController;
    }

    @Override
    public void welcomeMessage() {
        clinicScreenView.showWelcomeMessage();
        super.welcomeMessage();
    }

    /**
     * Creates a linked hashmap of all string representations of clinic commands mapped to the method that each command calls.
     * @return LinkedHashMap<String, Command> - ordered HashMap of strings mapped to their respective contact commands.
     */
    public LinkedHashMap<String, Command> AllCommands() {
        LinkedHashMap<String, Command> commands = new LinkedHashMap<>();
        commands.put("change clinic name", ChangeClinicName());
        commands.put("change clinic email", ChangeClinicEmail());
        commands.put("change clinic phone number", ChangeClinicPhoneNumber());
        commands.put("change clinic address", ChangeClinicAddress());
        commands.put("back", Back(previousController));
        commands.putAll(super.AllCommands());
        return commands;
    }

    private Command ChangeClinicName() {
        return (x) -> {
            String newName = clinicScreenView.showClinicNamePrompt();
            if (clinicManager.changeClinicName(newName)) {
                clinicScreenView.showSuccessfullyChangedClinicName();
            }
            else {
                clinicScreenView.showClinicNameFormatError();
            }
        };
    }

    private Command ChangeClinicPhoneNumber() {
        return (x) -> {
            String newPhoneNumber = clinicScreenView.showClinicPhoneNumberPrompt();
            if (clinicManager.changeClinicPhoneNumber(newPhoneNumber)) {
                clinicScreenView.showSuccessfullyChangedClinicPhoneNumber();
            }
            else {
                clinicScreenView.showClinicPhoneNumberFormatError();
            }
        };
    }

    private Command ChangeClinicEmail() {
        return (x) -> {
            String newEmail = clinicScreenView.showClinicEmailPrompt();
            if (clinicManager.changeClinicEmail(newEmail)) {
                clinicScreenView.showSuccessfullyChangedClinicEmail();
            }
            else {
                clinicScreenView.showClinicEmailFormatError();
            }
        };
    }

    private Command ChangeClinicAddress() {
        return (x) -> {
            String newAddress = clinicScreenView.showClinicAddressPrompt();
            if (clinicManager.changeClinicAddress(newAddress)) {
                clinicScreenView.showSuccessfullyChangedClinicAddress();
            }
            else {
                clinicScreenView.showClinicAddressFormatError();
            }
        };
    }

}