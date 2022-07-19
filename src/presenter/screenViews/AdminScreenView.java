package presenter.screenViews;

import dataBundles.LogData;
import presenter.entityViews.LogView;
import presenter.response.UserCredentials;

import java.util.List;

public class AdminScreenView extends UserScreenView {

    public UserCredentials registerSecretaryPrompt() {
        return registerAccountPrompt("secretary");
    }

    public UserCredentials registerDoctorPrompt() {
        return registerAccountPrompt("doctor");
    }

    public UserCredentials registerPatientPrompt() {
        return registerAccountPrompt("patient");
    }

    public UserCredentials registerAdminPrompt() {
        return registerAccountPrompt("admin");
    }

    public void showFailedToRegisterUserError(){
        errorMessage("Failed to register user account");
    }

    public void showRegisterAccountSuccess(){
        successMessage("Created user account successfully");
    }

    public void viewAllLogs(List<LogData> items) {
        LogView logView = new LogView();
        String output = logView.viewFullFromList(items);
        infoMessage("All Logs:");
        infoMessage(output);
    }
}
