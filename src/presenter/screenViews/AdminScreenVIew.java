package presenter.screenViews;

import presenter.response.UserCredentials;

public class AdminScreenVIew extends UserScreenView {
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

}
