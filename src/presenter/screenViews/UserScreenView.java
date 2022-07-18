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

    public void viewLogs(List<LogData> items) {
        LogView logView = new LogView();
        String output = logView.viewFullFromList(items);
        infoMessage(output);
    }

    public void viewAllLogs(List<LogData> items) {
        infoMessage("All Logs:");
        viewLogs(items);
    }
}
