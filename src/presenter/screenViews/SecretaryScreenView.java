package presenter.screenViews;

import presenter.response.UserCredentials;

public class SecretaryScreenView extends ScreenView {

    public UserCredentials registerPatientAccount() {
        return registerAccountPrompt("patient");
    }

    public UserCredentials registerDoctorAccount() {
        return registerAccountPrompt("doctor");
    }
}
