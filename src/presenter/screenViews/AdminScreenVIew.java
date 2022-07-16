package presenter.screenViews;

import presenter.response.UserCredentials;

public class AdminScreenVIew extends UserScreenView {
    public UserCredentials registerSecretaryPrompt() {
        return registerAccountPrompt("secretary");
    }

    public UserCredentials registerDoctorPrompt() {
        return registerAccountPrompt("doctor");
    }
}
