package presenter.screenViews;

import dataBundles.LogData;
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
    public void ResetPasswordSuccessMessage() {
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
    protected LocalDate showLocalDatePrompt() {
        Integer year = inputInt("Enter your desired year (YYYY): ");
        if (year == null) {return null;}
        Integer month = inputInt("Enter your desired month (MM): ");
        if (month == null) {return null;}
        Integer day = inputInt("Enter your desired day (DD): ");
        if (day == null) {return null;}

        try {
            return LocalDate.of(year, month, day);
        } catch (DateTimeException ignored) {
            return null;
        }
    }

}
