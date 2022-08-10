package presenters.screenViews;

import dataBundles.ContactData;
import presenters.entityViews.ContactView;
import presenters.response.PasswordResetDetails;
import presenters.response.UserCredentials;

/**
 * The Admin's presenter class.
 */
public class AdminScreenView extends UserScreenView {

    /**
     * The Contact entity's view.
     */
    private final ContactView contactView = new ContactView();

    /**
     * Register a secretary account.
     *
     * @return UserCredentials containing username and password as String.
     */
    public UserCredentials registerSecretaryPrompt() {
        return registerAccountPrompt("secretary");
    }

    /**
     * Register a doctor account.
     *
     * @return UserCredentials containing username and password as String.
     */
    public UserCredentials registerDoctorPrompt() {
        return registerAccountPrompt("doctor");
    }

    /**
     * Register a patient account.
     *
     * @return UserCredentials containing username and password as String.
     */
    public UserCredentials registerPatientPrompt() {
        return registerAccountPrompt("patient");
    }

    /**
     * Register an admin account.
     *
     * @return UserCredentials containing username and password as String.
     */
    public UserCredentials registerAdminPrompt() {
        return registerAccountPrompt("admin");
    }

    /**
     * Show a failed to create user error that is thrown when username or password is in an incorrect format.
     *
     * @param userType String - Type of the user as a string.
     */
    public void showIncorrectFormatError(String userType) {
        errorMessage("Failed to register " + userType + " account. Make sure:" +
                "\n1. Username is 6 characters long and only contains letters and numbers." +
                "\n2. Password is 8 characters long.");
    }

    /**
     * Show a failed to create user error that is thrown when username is already in use.
     *
     * @param userType String - Type of the user as a string.
     */
    public void showUsernameInUseError(String userType) {
        errorMessage("Failed to register " + userType + " account: username is already in use.");
    }

    /**
     * Show success message when admin successfully creates another user.
     *
     * @param userType String - Type of the user as a string.
     */
    public void showRegisterUserSuccess(String userType) {
        successMessage("Created " + userType + " account successfully!");
    }

    /**
     * Show welcome message when admin is redirected to the User Management Controller
     */
    public void showUserManagementWelcomeMessage() {
        infoMessage("Welcome to the User Management Terminal!");
    }

    /**
     * Ask admin for a user to delete.
     *
     * @return String username to delete.
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
     * Asks admin for a user to change password.
     *
     * @return String username of user to change password.
     */
    public String getUsersName() {
        infoMessage("You are about to change another user's password!");
        return input("Enter username: ");
    }

    /**
     * Asks admin for the new password and confirmed new password.
     *
     * @return PasswordResetDetails containing password and confirmedPassword as String.
     */
    public PasswordResetDetails getNewPasswordPrompt() {
        String password = input("Enter new password: ");
        String confirmedPassword = input("Enter confirmed password: ");
        return new PasswordResetDetails(password, confirmedPassword);
    }

    /**
     * Show a failed to change password method that is thrown when new password and confirmed password do not match.
     *
     * @param userContact The contact data of the user changing their password.
     */
    public void passwordMismatchError(ContactData userContact) {
        String name = contactView.viewName(userContact);
        errorMessage("Cannot change " + name + " password: new password and confirmed password do not match!");
    }

    /**
     * Show a user does not exist error when the user is not present in database.
     *
     * @param user The inputted username of the user that does not exist.
     */
    public void userDoesNotExistError(String user) {
        errorMessage(user + " is not a valid user");
    }

}
