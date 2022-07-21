package controllers;

import dataBundles.ContactData;
import presenter.screenViews.UserScreenView;
import useCases.managers.ContactManager;

import java.time.LocalDate;
import java.util.HashMap;

/**
 * Controller class that processes the commands that a user performs on their contact information.
 */
public class ContactController extends TerminalController {

    private final ContactData contactData;
    private final UserScreenView userScreenView;
    private final ContactManager contactManager;
    private final UserController<?> previousController;

    /**
     * Creates a contact controller object that handles the commands a user performs on their contact information.
     * @param context Context - a reference to the context object, which stores the current controller and allows for
     *                switching between controllers.
     * @param previousController UserController<?> - the object of the controller that switched into this contact
     *                           controller object.
     * @param contactData ContactData - a data bundle storing the ID and attributes of the contact object associated
     *                    with the current user.
     * @param userScreenView UserScreenView - the presenter object of the user that this controller's contact data is
     *                       associated with.
     */
    public ContactController(Context context, UserController<?> previousController,
                             ContactData contactData, UserScreenView userScreenView) {
        super(context);
        this.previousController = previousController;
        this.contactData = contactData;
        this.userScreenView = userScreenView;
        this.contactManager = new ContactManager(getDatabase());
    }

    /**
     * Creates a hashmap of all string representations of contact commands mapped to the method that each command calls.
     * @return HashMap<String, Command> - HashMap of strings mapped to their respective contact commands.
     */
    public HashMap<String, Command> AllCommands() {
        HashMap<String, Command> commands = super.AllCommands();
        commands.put("view contact information", ViewContactInformation());
        commands.put("change name", ChangeName());
        commands.put("change email", ChangeEmail());
        commands.put("change phone number", ChangePhoneNumber());
        commands.put("change address", ChangeAddress());
        commands.put("change birthday", ChangeBirthday());
        commands.put("change emergency contact name", ChangeEmergencyContactName());
        commands.put("change emergency contact email", ChangeEmergencyContactEmail());
        commands.put("change emergency contact phone number", ChangeEmergencyContactPhoneNumber());
        commands.put("change emergency contact relationship", ChangeEmergencyContactRelationship());
        commands.put("back", back(previousController));
        return commands;
    }

    private Command ViewContactInformation() {
        return (x) -> userScreenView.displayContactInfo(contactData);
    }

    private Command ChangeName() {
        return (x) -> {
            String newName = userScreenView.showNamePrompt(false);
            if (contactManager.changeName(contactData, newName)) {
                userScreenView.showSuccessfullyChangedName(false);
            }
            else {
                userScreenView.showNameFormatError();
            }
        };
    }

    private Command ChangeEmail() {
        return (x) -> {
            String newEmail = userScreenView.showEmailPrompt(false);
            if (contactManager.changeEmail(contactData, newEmail)) {
                userScreenView.showSuccessfullyChangedEmail(false);
            }
            else {
                userScreenView.showEmailFormatError();
            }
        };
    }

    private Command ChangePhoneNumber() {
        return (x) -> {
            String newPhoneNumber = userScreenView.showPhoneNumberPrompt(false);
            if (contactManager.changePhoneNumber(contactData, newPhoneNumber)) {
                userScreenView.showSuccessfullyChangedPhoneNumber(false);
            }
            else {
                userScreenView.showPhoneNumberFormatError();
            }
        };
    }

    private Command ChangeAddress() {
        return (x) -> {
            String newAddress = userScreenView.showAddressPrompt();
            if (contactManager.changeAddress(contactData, newAddress)) {
                userScreenView.showSuccessfullyChangedAddress();
            }
            else {
                userScreenView.showAddressFormatError();
            }
        };
    }

    private Command ChangeBirthday() {
        return (x) -> {
            LocalDate newBirthday = userScreenView.showBirthdayPrompt();
            if (contactManager.changeBirthday(contactData, newBirthday)) {
                userScreenView.showSuccessfullyChangedBirthday();
            }
            else {
                userScreenView.showBirthdayFormatError();
            }
        };
    }

    private Command ChangeEmergencyContactName() {
        return (x) -> {
            String newEmergencyContactName = userScreenView.showNamePrompt(true);
            if (contactManager.changeEmergencyContactName(contactData, newEmergencyContactName)) {
                userScreenView.showSuccessfullyChangedName(true);
            }
            else {
                userScreenView.showNameFormatError();
            }
        };
    }

    private Command ChangeEmergencyContactEmail() {
        return (x) -> {
            String newEmergencyContactEmail = userScreenView.showEmailPrompt(true);
            if (contactManager.changeEmergencyContactEmail(contactData, newEmergencyContactEmail)) {
                userScreenView.showSuccessfullyChangedEmail(true);
            }
            else {
                userScreenView.showEmailFormatError();
            }
        };
    }

    private Command ChangeEmergencyContactPhoneNumber() {
        return (x) -> {
            String newEmergencyContactPhoneNumber = userScreenView.showPhoneNumberPrompt(true);
            if (contactManager.changeEmergencyContactPhoneNumber(contactData, newEmergencyContactPhoneNumber)) {
                userScreenView.showSuccessfullyChangedPhoneNumber(true);
            }
            else {
                userScreenView.showPhoneNumberFormatError();
            }
        };
    }

    private Command ChangeEmergencyContactRelationship() {
        return (x) -> {
            String newEmergencyContactRelationship = userScreenView.showEmergencyRelationshipPrompt();
            if (contactManager.changeEmergencyRelationship(contactData, newEmergencyContactRelationship)) {
                userScreenView.showSuccessfullyChangedEmergencyRelationship();
            }
            else {
                userScreenView.showEmergencyRelationshipError();
            }
        };
    }

}
