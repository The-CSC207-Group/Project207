package presenter.screenViews;

import dataBundles.LogData;
import presenter.entityViews.LogView;
import presenter.response.PasswordResetDetails;

import java.util.List;

public abstract class UserScreenView extends ScreenView{

    public PasswordResetDetails resetPasswordPrompt() {
        infoMessage("You are about to reset your password.");
        String username = input("Enter new password: ");
        String password = input("Confirm new password: ");
        return new PasswordResetDetails(username, password);
    }

    public void showResetPasswordMismatchError() {
        errorMessage("Cannot reset password: new password and confirmed new password do not match.");
    }

    public void viewUserLogs(List<LogData> items) {
        LogView logView = new LogView();
        String output = logView.viewFullFromList(items);
        infoMessage("Logs:");
        infoMessage(output);
    }

    public String enterPatientUsernamePrompt() {
        return enterUsernamePrompt("patient");
    }

    public String enterDoctorUsernamePrompt() {
        return enterUsernamePrompt("doctor");
    }

    public String enterYearPrompt() {
        return input("Enter your desired year (YYYY): ");
    }

    public String enterMonthPrompt() {
        return input("Enter your desired month (MM): ");
    }

    public String enterDayPrompt() {
        return input("Enter your desired day (DD): ");
    }

    public String enterHourPrompt() {
        return input("Enter your desired hour (HH): ");
    }

    public String enterMinutePrompt() {
        return input("Enter your desired minute (MM): ");
    }

    public String enterLengthPrompt() {
        return input("Enter your desired length in minutes: ");
    }
}
