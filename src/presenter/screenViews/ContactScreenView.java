package presenter.screenViews;

import dataBundles.ContactData;
import presenter.entityViews.ContactView;

import java.time.LocalDate;

public class ContactScreenView extends ScreenView {

    public void showWelcomeMessage() {
        infoMessage("Entered contact details menu.");
    }

    public void displayContactInfo(ContactData contactData) {
        ContactView contactView = new ContactView();
        infoMessage(contactView.viewFull(contactData));
    }

    public String showNamePrompt(boolean emergencyContact) {
        if (emergencyContact) {
            return input("Enter the name of your emergency contact: ");
        } else {
            return input("Enter your name: ");
        }
    }

    public void showNameFormatError() {
        errorMessage("Name is not in valid format.");
    }

    public void showSuccessfullyChangedName(boolean emergencyContact) {
        if (emergencyContact) {
            successMessage("Successfully changed emergency contact name.");
        } else {
            successMessage("Successfully changed name.");
        }
    }

    public String showEmailPrompt(boolean emergencyContact) {
        if (emergencyContact) {
            return input("Enter the email of your emergency contact: ");
        } else {
            return input("Enter your email: ");
        }
    }

    public void showEmailFormatError() {
        errorMessage("Email is not in valid format.");
    }

    public void showSuccessfullyChangedEmail(boolean emergencyContact) {
        if (emergencyContact) {
            successMessage("Successfully changed emergency contact email.");
        } else {
            successMessage("Successfully changed email.");
        }
    }

    public String showPhoneNumberPrompt(boolean emergencyContact) {
        if (emergencyContact) {
            return input("Enter the phone number of your emergency contact: ");
        } else {
            return input("Enter your phone number: ");
        }
    }

    public void showPhoneNumberFormatError() {
        errorMessage("Phone number is not in valid format: ^([0-9])+$");
    }

    public void showSuccessfullyChangedPhoneNumber(boolean emergencyContact) {
        if (emergencyContact) {
            successMessage("Successfully changed emergency contact phone number.");
        } else {
            successMessage("Successfully changed phone number.");
        }
    }

    public String showAddressPrompt() {
        return input("Enter your address: ");
    }

    public void showAddressFormatError() {
        errorMessage("Address is not in valid format.");
    }

    public void showSuccessfullyChangedAddress() {
        errorMessage("Successfully changed address.");
    }

    public LocalDate showBirthdayPrompt() {
        infoMessage("Enter your date of birth: ");
        return showLocalDatePrompt();
    }

    public void showBirthdayFormatError() {
        errorMessage("Birthday is not in valid format");
    }

    public void showSuccessfullyChangedBirthday() {
        successMessage("Successfully changed birthday.");
    }

    public String showEmergencyRelationshipPrompt() {
        return input("Enter your emergency contact's relationship to you: ");
    }

    public void showEmergencyRelationshipError() {
        errorMessage("Emergency contact relationship not in valid format");
    }

    public void showSuccessfullyChangedEmergencyRelationship() {
        successMessage("Successfully changed emergency contact relationship.");
    }
}
