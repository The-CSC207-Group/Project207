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
        String username = input("Enter your username: ");
        String password = input("Enter your password: ");
        return new UserCredentials(username, password);
    }

    public void viewClinicInfo(ClinicData clinic) {
        infoMessage(new ClinicView().viewFull(clinic));
    }

    public void showLoginError() {
        errorMessage("Incorrect username or password, please try again...");
    }
}
