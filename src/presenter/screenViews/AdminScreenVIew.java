package presenter.screenViews;

import presenter.response.UserCredentials;

public class AdminScreenVIew extends ScreenView {
    public UserCredentials registerSecretaryPrompt() {
        return registerAccountPrompt("secretary");
    }

    public UserCredentials registerDoctorPrompt() {
        return registerAccountPrompt("doctor");
    }
}
