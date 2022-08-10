package presenters.screenViews;

import dataBundles.ClinicData;
import dataBundles.LogData;
import dataBundles.UserData;
import presenters.entityViews.ClinicView;
import presenters.entityViews.LogView;
import presenters.response.PasswordResetDetails;

import java.util.List;

/**
 * Presenter for a user of the program.
 */
public abstract class UserScreenView extends ScreenView {

    /**
     * Show user a password reset prompt with a confirmation.
     *
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
     *
     * @param items List<LogData> - list of the user's logs.
     */
    public void viewUserLogs(List<LogData> items) {
        LogView logView = new LogView();
        String output = logView.viewFullFromList(items);
        infoMessage("Logs:");
        infoMessage(output);
    }

    /**
     * Displays clinic information to users.
     *
     * @param clinicData ClinicData - data consisting of the information about the clinic associated
     *                   with this program
     */
    public void displayClinicInfo(ClinicData clinicData) {
        ClinicView clinicView = new ClinicView();
        infoMessage(clinicView.viewFull(clinicData));
    }

    /**
     * Shows a welcome message to the user.
     *
     * @param user UserData<?> - data relating to the user.
     */
    public void showWelcomeUserMessage(UserData<?> user) {
        infoMessage("Welcome, " + user.getUsername() + "!");
    }

    /**
     * Prompt for deleting an item from an enumeration.
     *
     * @param itemType String - type of the item listed.
     * @return Integer - returns the index inputted by the user.
     */
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
