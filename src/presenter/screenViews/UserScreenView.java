package presenter.screenViews;

import dataBundles.ContactData;
import dataBundles.LogData;
import presenter.entityViews.ContactView;
import presenter.entityViews.LogView;
import presenter.response.PasswordResetDetails;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;

public abstract class UserScreenView extends ScreenView{

    /**
     * Show user a password reset prompt with a confirmation.
     * @return PasswordResetDetails containing new password and confirmed new password.
     */
    public PasswordResetDetails resetPasswordPrompt() {
        infoMessage("You are about to reset your password.");
        String username = input("Enter your new password: ");
        String password = input("Confirm your new password: ");
        return new PasswordResetDetails(username, password);
    }

    /**
     * Show error when password and confirmed password don't match.
     */
    public void showResetPasswordMismatchError() {
        errorMessage("Cannot reset password: new password and confirmed new password do not match.");
    }

    /**
     * Show success message when password is reset.
     */
    public void showResetPasswordSuccessMessage() {
        successMessage("Password reset successfully");
    }

    /**
     * View logs specific to the current user.
     */
    public void viewUserLogs(List<LogData> items) {
        LogView logView = new LogView();
        String output = logView.viewFullFromList(items);
        infoMessage("Logs:");
        infoMessage(output);
    }

    /**
     * Ask user to enter a local date.
     * @return LocalDate if inputted date is valid.
     *         null if inputted date is invalid.
     */
    public LocalDate showLocalDatePrompt() {
        Integer year = inputInt("Enter year (YYYY): ");
        if (year == null) {return null;}
        Integer month = inputInt("Enter month (MM): ");
        if (month == null) {return null;}
        Integer day = inputInt("Enter day (DD): ");
        if (day == null) {return null;}

        try {
            return LocalDate.of(year, month, day);
        } catch (DateTimeException ignored) {
            return null;
        }
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

    public void showSuccessfullyChangedEmail() {
        successMessage("Successfully changed email.");
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

    public String showAddressPrompt(boolean emergencyContact) {
        if (emergencyContact) {
            return input("Enter the address of your emergency contact: ");
        } else {
            return input("Enter your address: ");
        }
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

    public String showEmergencyRelationship() {
        return input("Enter your emergency contact's relationship to you: ");
    }

    public void showEmergencyRelationshipError() {
        errorMessage("Emergency contact relationship not in valid format");
    }

    public void showSuccessfullyChangedEmergencyRelationship() {
        successMessage("Successfully changed emergency contact relationship.");
    }

    protected Integer deleteItemFromEnumerationPrompt(String itemType) {
        warningMessage("This action cannot be undone!");
        Integer index = inputInt("Input " + itemType + " number to delete: ");
        if (index != null) {
            return index - 1; // -1 to be the index of the list, not number inputted.
        } else {
            return null;
        }
    }

}
