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
    public void failedCreateAccount(){
        System.out.println("failed needs work on this method");

    }
    public void successCreateAccount(){
        System.out.println("succeeded needs work on this method");
    }
    public String patientUsernamePrompt(){
        return enterPatientUsernamePrompt();
    }

    public void viewAllLogs(List<LogData> items) {
        LogView logView = new LogView();
        String output = logView.viewFullFromList(items);
        infoMessage("All Logs:");
        infoMessage(output);
    }
}
