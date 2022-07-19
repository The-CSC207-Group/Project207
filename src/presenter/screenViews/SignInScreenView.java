package presenter.screenViews;

import dataBundles.ClinicData;
import presenter.entityViews.ClinicView;
import presenter.response.UserCredentials;

public class SignInScreenView extends ScreenView {

    public void welcomeMessage() {
        infoMessage("Welcome to the program! Please sign in to continue.\n" +
                    "Type 'help' to see a list of all possible commands.");
    }

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

    public void showLoginError() {
        errorMessage("Incorrect username or password, please try again...");
    }
}
