package presenter.screenViews;

import dataBundles.ClinicData;
import presenter.entityViews.ClinicView;
import presenter.response.UserCredentials;

public class SignInScreenView extends ScreenView {

    /**
     * Welcome message displayed before the user logs in.
     */
    public void welcomeMessage() {
        infoMessage("Welcome to the program! Please sign in to continue.");
        showHelpPrompt();
    }

    /**
     * Ask user for his username and password.
     * Used for login.
     * @return UserCredentials containing username and password.
     */
    public UserCredentials userLoginPrompt() {
        String username = enterUsernamePrompt("your");
        String password = enterPasswordPrompt("your");
        return new UserCredentials(username, password);
    }

    /**
     * View information about the clinic without login in.
     * @param clinic clinic data bundle.
     */
    public void viewClinicInfo(ClinicData clinic) {
        infoMessage(new ClinicView().viewFull(clinic));
    }

    /**
     * View login error on incorrect username and password.
     */
    public void showLoginError() {
        errorMessage("Incorrect username or password, please try again...");
    }

    public void showSuccessLogin(String user) {
        successMessage("Logged in to " + user + " successfully!");
    }
}
