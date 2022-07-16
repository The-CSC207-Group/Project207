package presenter.screenViews;

import presenter.response.UserCredentials;

public class SignInScreenView extends ScreenView {
    public UserCredentials userLoginPrompt() {
        String username = input("Enter your username: ");
        String password = input("Enter your password: ");
        return new UserCredentials(username, password);
    }
}
