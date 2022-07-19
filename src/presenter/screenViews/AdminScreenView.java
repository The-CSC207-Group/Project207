package presenter.screenViews;

import dataBundles.LogData;
import presenter.entityViews.LogView;
import presenter.response.UserCredentials;

import java.util.List;

public class AdminScreenView extends UserScreenView {

    public UserCredentials registerSecretaryPrompt() {
        return registerAccountPrompt("secretary");
    }

    public UserCredentials registerDoctorPrompt() {
        return registerAccountPrompt("doctor");
    }

    public UserCredentials registerPatientPrompt() {
        return registerAccountPrompt("patient");
    }

    public UserCredentials registerAdminPrompt() {
        return registerAccountPrompt("admin");
    }

    public void showFailedToRegisterUserError() {
        errorMessage("Failed to register user account: username already in use");
    }

    public void showRegisterUserSuccess() {
        successMessage("Created user account successfully!");
    }

    public void deleteAdminPrompt() {
        deleteAccountPrompt("admin");
    }

    public void deleteSecretaryPrompt() {
        deleteAccountPrompt("secretary");
    }

    public void deleteDoctorPrompt() {
        deleteAccountPrompt("doctor");
    }

    public void deletePatientPrompt() {
        deleteAccountPrompt("patient");
    }

    public void showFailedToDeleteUserError() {
        errorMessage("Failed to register user account: username already in use");
    }

    public void showDeleteUserSuccess() {
        successMessage("Successfully deleted user!");
    }

    public void viewAllLogs(List<LogData> items) {
        LogView logView = new LogView();
        infoMessage("All Logs:");
        infoMessage(logView.viewFullFromList(items));
    }
}
