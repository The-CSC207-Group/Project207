package presenter.screenViews;

import dataBundles.LogData;
import presenter.entityViews.LogView;
import presenter.response.UserCredentials;

import java.util.List;

public class AdminScreenView extends UserScreenView {

    /**
     * Registers a secretary account.
     * @return UserCredentials containing username and password as String.
     */
    public UserCredentials registerSecretaryPrompt() {
        return registerAccountPrompt("secretary");
    }

    /**
     * Registers a doctor account.
     * @return UserCredentials containing username and password as String.
     */
    public UserCredentials registerDoctorPrompt() {
        return registerAccountPrompt("doctor");
    }

    /**
     * Registers a patient account.
     * @return UserCredentials containing username and password as String.
     */
    public UserCredentials registerPatientPrompt() {
        return registerAccountPrompt("patient");
    }

    /**
     * Registers an admin account.
     * @return UserCredentials containing username and password as String.
     */
    public UserCredentials registerAdminPrompt() {
        return registerAccountPrompt("admin");
    }

    /**
     * Shows a failed to create user error that is thrown when username is already used.
     */
    public void showFailedToRegisterUserError() {
        errorMessage("Failed to register user account: username already in use");
    }

    /**
     * Shows success message when admin successfully creates another user.
     */
    public void showRegisterUserSuccess() {
        successMessage("Created user account successfully!");
    }

    /**
     * Asks admin for a user to delete
     * @return String username to delete
     */
    public String deleteUserPrompt() {
        showIrreversibleActionWarning();
        return input("Enter username to delete: ");
    }

    /**
     * Shows a failed to delete user error that is thrown when username is not present in database.
     */
    public void showFailedToDeleteUserError() {
        errorMessage("Failed to delete account: username does not exist");
    }

    /**
     * Shows a success message when a user is successfully deleted.
     */
    public void showDeleteUserSuccess() {
        successMessage("Successfully deleted user account!");
    }

    /**
     * View all logs by all users.
     */
    public void viewAllLogs(List<LogData> items) {
        LogView logView = new LogView();
        infoMessage("All Logs:");
        infoMessage(logView.viewFullFromList(items));
    }
}
