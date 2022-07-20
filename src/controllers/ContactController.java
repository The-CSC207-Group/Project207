package controllers;

import controllers.Command;
import controllers.Context;
import controllers.TerminalController;
import controllers.UserController;
import dataBundles.ContactData;
import database.Database;
import presenter.screenViews.UserScreenView;
import useCases.managers.ContactManager;

import java.time.LocalDate;
import java.util.HashMap;

public class ContactController extends TerminalController {

    private final ContactData contactData;
    private final UserScreenView userScreenView;
    private final ContactManager contactManager;
    private final UserController<?> previousController;

    public ContactController(Context context, UserController<?> previousController,
                             ContactData contactData, UserScreenView userScreenView) {
        super(context);
        this.previousController = previousController;
        this.contactData = contactData;
        this.userScreenView = userScreenView;
        this.contactManager = new ContactManager(getDatabase());
    }

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

    public Command ViewContactInformation() {
        return (x) -> {
            userScreenView.displayContactInfo(contactData);
        };
    }

    public Command ChangeName() {
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

    public Command ChangeEmail() {
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

    public Command ChangePhoneNumber() {
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

    public Command ChangeAddress() {
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

    public Command ChangeBirthday() {
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

    public Command ChangeEmergencyContactName() {
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

    public Command ChangeEmergencyContactEmail() {
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

    public Command ChangeEmergencyContactPhoneNumber() {
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

    public Command ChangeEmergencyContactRelationship() {
        return (x) -> {
            String newEmergencyContactRelationship = userScreenView.showAddressPrompt();
            if (contactManager.changeEmergencyRelationship(contactData, newEmergencyContactRelationship)) {
                userScreenView.showSuccessfullyChangedEmergencyRelationship();
            }
            else {
                userScreenView.showEmergencyRelationshipError();
            }
        };
    }

}
