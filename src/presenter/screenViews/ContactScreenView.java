package presenter.screenViews;

import dataBundles.ContactData;
import presenter.entityViews.ContactView;

import java.time.LocalDate;

/**
 * The Contact's presenter class.
 */
public class ContactScreenView extends ScreenView {
    /**
     * Shows the welcome message that is presented at the start of the screen.
     */
    public void showWelcomeMessage() {
        infoMessage("Entered contact details menu.");
    }

    /**
     * Displays the various contact information stored in contact data.
     * @param contactData data representing a contact entity.
     */
    public void displayContactInfo(ContactData contactData) {
        ContactView contactView = new ContactView();
        infoMessage(contactView.viewFull(contactData));
    }

    /**
     * Shows the name input, which can be used for user's name or emergency contact name dependent on a boolean.
     * @param emergencyContact a boolean that represents whether the prompt is for emergency contact name or not.
     * @return a String of the inputted name.
     */
    public String showNamePrompt(boolean emergencyContact) {
        if (emergencyContact) {
            return input("Enter the name of your emergency contact: ");
        } else {
            return input("Enter your name: ");
        }
    }

    /**
     * Show an error related to name formatting.
     */
    public void showNameFormatError() {
        errorMessage("Name is not in valid format.");
    }

    /**
     * Show a success message relating to changing a name.
     * @param emergencyContact a boolean that represents whether the name change was that of an emergency contact or not.
     */
    public void showSuccessfullyChangedName(boolean emergencyContact) {
        if (emergencyContact) {
            successMessage("Successfully changed emergency contact name.");
        } else {
            successMessage("Successfully changed name.");
        }
    }

    /**
     * Shows the email input, taking in text from the user.
     * @param emergencyContact a boolean that represents whether the email change was that of an emergency contact
     *                         or not.
     * @return a string representing the inputted data.
     */
    public String showEmailPrompt(boolean emergencyContact) {
        if (emergencyContact) {
            return input("Enter the email of your emergency contact: ");
        } else {
            return input("Enter your email: ");
        }
    }

    /**
     * Shows an error relating to email address formatting.
     */
    public void showEmailFormatError() {
        errorMessage("Email is not in valid format.");
    }

    /**
     * Show a success message for changing an email.
     * @param emergencyContact a boolean that represents whether the email change was that of an emergency contact
     *                        or not.
     */
    public void showSuccessfullyChangedEmail(boolean emergencyContact) {
        if (emergencyContact) {
            successMessage("Successfully changed emergency contact email.");
        } else {
            successMessage("Successfully changed email.");
        }
    }

    /**
     * Show phone number input, taking in text from the user.
     * @param emergencyContact a boolean that represents whether the phone number change was that of an emergency
     *                        contact or not.
     * @return a String representing the inputted data.
     */
    public String showPhoneNumberPrompt(boolean emergencyContact) {
        if (emergencyContact) {
            return input("Enter the phone number of your emergency contact: ");
        } else {
            return input("Enter your phone number: ");
        }
    }

    /**
     * Show an error relating to phone number formatting.
     */
    public void showPhoneNumberFormatError() {
        errorMessage("Phone number is not in valid format: ^([0-9])+$");
    }

    /**
     * Show a success message for changing a phone number.
     * @param emergencyContact a boolean that represents whether the phone number change was that of an emergency
     *                        contact or not.
     */
    public void showSuccessfullyChangedPhoneNumber(boolean emergencyContact) {
        if (emergencyContact) {
            successMessage("Successfully changed emergency contact phone number.");
        } else {
            successMessage("Successfully changed phone number.");
        }
    }

    /**
     * Show Address input, taking in text from the user.
     * @return a String representing inputted data.
     */
    public String showAddressPrompt() {
        return input("Enter your address: ");
    }

    /**
     * Show an error relating to address formatting.
     */
    public void showAddressFormatError() {
        errorMessage("Address is not in valid format.");
    }

    /**
     * Show a success message for changing an address.
     */
    public void showSuccessfullyChangedAddress() {
        errorMessage("Successfully changed address.");
    }

    /**
     * Show a birthday input, taking in data from the user.
     * @return a LocalDate representing the inputted data.
     */
    public LocalDate showBirthdayPrompt() {
        infoMessage("Enter your date of birth: ");
        return showLocalDatePrompt();
    }

    /**
     * Show error relating to birthday formatting.
     */
    public void showBirthdayFormatError() {
        errorMessage("Birthday is not in valid format");
    }

    /**
     * Show success message for changing a birthday.
     */
    public void showSuccessfullyChangedBirthday() {
        successMessage("Successfully changed birthday.");
    }

    /**
     * Show emergency relationship input, taking in text from the user.
     * @return a String representing the inputted user data.
     */
    public String showEmergencyRelationshipPrompt() {
        return input("Enter your emergency contact's relationship to you: ");
    }

    /**
     * Show an error relating to emergency relationship formatting.
     */
    public void showEmergencyRelationshipError() {
        errorMessage("Emergency contact relationship not in valid format");
    }

    /**
     * Show a success message for changing emergency relationship.
     */
    public void showSuccessfullyChangedEmergencyRelationship() {
        successMessage("Successfully changed emergency contact relationship.");
    }
}
