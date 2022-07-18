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

    public String enterYearPrompt(String event) {
        return input("Enter the year of your " + event + " (YYYY): ");
    }

    public String enterMonthPrompt(String event) {
        return input("Enter the month of your " + event + " (MM): ");
    }

    public String enterDayPrompt(String event) {
        return input("Enter the day of your " + event + " (DD): ");
    }

    public String enterHourPrompt(String event) {
        return input("Enter the hour of your " + event + " (HH): ");
    }

    public String enterMinutePrompt(String event) {
        return input("Enter the minute of your " + event + " (MM): ");
    }

    public String enterLengthPrompt(String event) {
        return input("Enter the length of your " + event + " in minutes: ");
    }
}
