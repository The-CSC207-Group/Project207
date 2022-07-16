package presenter.screenViews;

import presenter.response.UserCredentials;

public class SignInView extends ScreenView {
    public UserCredentials askForLoginDetails() {
        String username = input("Enter your username: ");
        String password = input("Enter your password: ");
        return new UserCredentials(username, password);
    }
}
