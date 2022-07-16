package presenter.screenViews;

import presenter.response.UserCredentials;

public class AdminScreenVIew extends ScreenView {
    public UserCredentials registerSecretaryPrompt() {
        infoMessage("You are about to create a new secretary account.");
        String username = input("Enter secretary username: ");
        String password = input("Enter secretary password: ");
        return new UserCredentials(username, password);
    }
}
