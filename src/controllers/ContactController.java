package controllers;

import dataBundles.ContactData;
import dataBundles.UserData;
import presenters.screenViews.ContactScreenView;
import useCases.ContactManager;
import useCases.LogManager;
import useCases.UserManager;

import java.time.LocalDate;
import java.util.LinkedHashMap;

/**
 * Controller class that processes the commands that a user performs on their contact information.
 */
public class ContactController extends TerminalController {

    private final ContactData contactData;
    private final ContactScreenView contactScreenView;
    private final ContactManager contactManager;
    private final UserController<?> previousController;
    private final UserData<?> userData;
    private final LogManager logManager;

    /**
     * Creates a contact controller object that handles the commands a user performs on their contact information.
     * @param context Context - a reference to the context object, which stores the current controller and allows for
     *                switching between controllers.
     * @param previousController UserController<?> - the object of the controller that switched into this contact
     *                           controller object.
     * @param contactData ContactData - a data bundle storing the ID and attributes of the contact object associated
     *                    with the current user.
     * @param userData UserData - a data bundle storing the ID and attributes of the user object that this contact
     *                 information is associated with
     */
    public ContactController(Context context, UserController<?> previousController,
                             ContactData contactData, UserData<?> userData) {
        super(context);
        this.previousController = previousController;
        this.contactData = contactData;
        this.userData = userData;
        this.contactManager = new ContactManager(getDatabase());
        this.logManager = new LogManager(getDatabase());
        this.contactScreenView = new ContactScreenView();
    }

    @Override
    public void welcomeMessage() {
        contactScreenView.showWelcomeMessage();
        super.welcomeMessage();
    }

    /**
     * Creates a linked hashmap of all string representations of contact commands mapped to the method that each command calls.
     * @return LinkedHashMap<String, Command> - ordered HashMap of strings mapped to their respective contact commands.
     */
    public LinkedHashMap<String, Command> AllCommands() {
        LinkedHashMap<String, Command> commands = new LinkedHashMap<>();
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
        commands.put("back", Back(previousController));
        commands.putAll(super.AllCommands());
        return commands;
    }

    private Command ViewContactInformation() {
        return (x) -> contactScreenView.displayContactInfo(contactData);
    }

    private Command ChangeName() {
        return (x) -> {
            String newName = contactScreenView.showNamePrompt(false);
            if (contactManager.changeName(contactData, newName)) {
                logManager.addLog(userData, "changed name");
                contactScreenView.showSuccessfullyChangedName(false);
            } else {
                contactScreenView.showNameFormatError();
            }
        };
    }

    private Command ChangeEmail() {
        return (x) -> {
            String newEmail = contactScreenView.showEmailPrompt(false);
            if (contactManager.changeEmail(contactData, newEmail)) {
                logManager.addLog(userData, "changed email");
                contactScreenView.showSuccessfullyChangedEmail(false);
            } else {
                contactScreenView.showEmailFormatError();
            }
        };
    }

    private Command ChangePhoneNumber() {
        return (x) -> {
            String newPhoneNumber = contactScreenView.showPhoneNumberPrompt(false);
            if (contactManager.changePhoneNumber(contactData, newPhoneNumber)) {
                logManager.addLog(userData, "changed phone number");
                contactScreenView.showSuccessfullyChangedPhoneNumber(false);
            } else {
                contactScreenView.showPhoneNumberFormatError();
            }
        };
    }

    private Command ChangeAddress() {
        return (x) -> {
            String newAddress = contactScreenView.showAddressPrompt();
            if (contactManager.changeAddress(contactData, newAddress)) {
                logManager.addLog(userData, "changed address");
                contactScreenView.showSuccessfullyChangedAddress();
            } else {
                contactScreenView.showAddressFormatError();
            }
        };
    }

    private Command ChangeBirthday() {
        return (x) -> {
            LocalDate newBirthday = contactScreenView.showBirthdayPrompt();
            if (newBirthday != null) {
                logManager.addLog(userData, "changed birthday");
                contactManager.changeBirthday(contactData, newBirthday);
                contactScreenView.showSuccessfullyChangedBirthday();
            } else {
                contactScreenView.showBirthdayFormatError();
            }
        };
    }

    private Command ChangeEmergencyContactName() {
        return (x) -> {
            String newEmergencyContactName = contactScreenView.showNamePrompt(true);
            if (contactManager.changeEmergencyContactName(contactData, newEmergencyContactName)) {
                logManager.addLog(userData, "changed emergency contact name");
                contactScreenView.showSuccessfullyChangedName(true);
            } else {
                contactScreenView.showNameFormatError();
            }
        };
    }

    private Command ChangeEmergencyContactEmail() {
        return (x) -> {
            String newEmergencyContactEmail = contactScreenView.showEmailPrompt(true);
            if (contactManager.changeEmergencyContactEmail(contactData, newEmergencyContactEmail)) {
                logManager.addLog(userData, "changed emergency contact email");
                contactScreenView.showSuccessfullyChangedEmail(true);
            } else {
                contactScreenView.showEmailFormatError();
            }
        };
    }

    private Command ChangeEmergencyContactPhoneNumber() {
        return (x) -> {
            String newEmergencyContactPhoneNumber = contactScreenView.showPhoneNumberPrompt(true);
            if (contactManager.changeEmergencyContactPhoneNumber(contactData, newEmergencyContactPhoneNumber)) {
                logManager.addLog(userData, "changed emergency contact phone number");
                contactScreenView.showSuccessfullyChangedPhoneNumber(true);
            } else {
                contactScreenView.showPhoneNumberFormatError();
            }
        };
    }

    private Command ChangeEmergencyContactRelationship() {
        return (x) -> {
            String newEmergencyContactRelationship = contactScreenView.showEmergencyRelationshipPrompt();
            if (contactManager.changeEmergencyRelationship(contactData, newEmergencyContactRelationship)) {
                logManager.addLog(userData, "changed emergency contact relationship");
                contactScreenView.showSuccessfullyChangedEmergencyRelationship();
            } else {
                contactScreenView.showEmergencyRelationshipError();
            }
        };
    }

}
