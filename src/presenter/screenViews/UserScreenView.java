package presenter.screenViews;

import dataBundles.LogData;
import presenter.entityViews.LogView;
import presenter.response.PasswordResetDetails;

import java.util.List;

public abstract class UserScreenView extends ScreenView{

    public PasswordResetDetails resetPasswordPrompt() {
        infoMessage("You are about to reset your password.");
        String username = input("Enter your new password: ");
        String password = input("Confirm your new password: ");
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

    protected String enterYearPrompt() {
        return input("Enter your desired year (YYYY): ");
    }

    protected String enterMonthPrompt() {
        return input("Enter your desired month (MM): ");
    }

    protected String enterDayPrompt() {
        return input("Enter your desired day (DD): ");
    }

    protected String enterHourPrompt() {
        return input("Enter your desired hour (HH): ");
    }

    protected String enterMinutePrompt() {
        return input("Enter your desired minute (MM): ");
    }

    protected String enterLengthPrompt() {
        return input("Enter your desired length in minutes: ");
    }
}
