package presenter.screenViews;

import dataBundles.ContactData;
import dataBundles.LogData;
import presenter.entityViews.ContactView;
import presenter.entityViews.LogView;
import presenter.response.PasswordResetDetails;
import presenter.response.UserCredentials;

import java.util.List;

public class AdminScreenView extends UserScreenView {

    ContactView contactView = new ContactView();

    /**
     * Register a secretary account.
     * @return UserCredentials containing username and password as String.
     */
    public UserCredentials registerSecretaryPrompt() {
        return registerAccountPrompt("secretary");
    }

    /**
     * Register a doctor account.
     * @return UserCredentials containing username and password as String.
     */
    public UserCredentials registerDoctorPrompt() {
        return registerAccountPrompt("doctor");
    }

    /**
     * Register a patient account.
     * @return UserCredentials containing username and password as String.
     */
    public UserCredentials registerPatientPrompt() {
        return registerAccountPrompt("patient");
    }

    /**
     * Register an admin account.
     * @return UserCredentials containing username and password as String.
     */
    public UserCredentials registerAdminPrompt() {
        return registerAccountPrompt("admin");
    }

    /**
     * Show a failed to create user error that is thrown when username is already used.
     */
    public void showFailedToRegisterUserError() {
        errorMessage("Failed to register user account: username already in use");
    }

    /**
     * Show success message when admin successfully creates another user.
     */
    public void showRegisterUserSuccess() {
        successMessage("Created user account successfully!");
    }

    /**
     * Ask admin for a user to delete
     * @return String username to delete
     */
    public String deleteUserPrompt() {
        showIrreversibleActionWarning();
        return input("Enter username to delete: ");
    }

    /**
     * Show a failed to delete user error that is thrown when username is not present in database.
     */
    public void showFailedToDeleteUserError() {
        errorMessage("Failed to delete account: username does not exist");
    }

    /**
     * Show a success message when a user is successfully deleted.
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

    public String getUsersName(){
        infoMessage("You are about to change another user's password!");
        return input("Enter username: ");
    }

    public PasswordResetDetails getNewPasswordPrompt() {
        String password = input("Enter new password: ");
        String confirmedPassword = input("Enter confirmed password");
        return new PasswordResetDetails(password, confirmedPassword);
    }

    public void passwordMismatchError(ContactData userContact) {
        String name = contactView.viewName(userContact);
        errorMessage("Cannot change " + name + " new password ");
    }

    public void userDoesNotExistError(String user){
        errorMessage(user + " is not a valid user");
    }
}
