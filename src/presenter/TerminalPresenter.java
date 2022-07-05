package presenter;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;


public class TerminalPresenter implements ApplicationPresenter {

    Scanner scanner = new Scanner(System.in);

    @Override
    public void infoMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void successMessage(String message) {
        System.out.println("✓ " + message);
    }

    @Override
    public void warningMessage(String message) {
        System.out.println("⚠ " + message);
    }

    @Override
    public void errorMessage(String message) {
        System.out.println("✗ " + message);
    }

    @Override
    public HashMap<String, String> promptPopup(List<String> fields) {
        HashMap<String, String> responses = new HashMap<>();
        for (String field : fields) {
            String value = promptPopup("Enter " + field + ": ");
            responses.put(field, value);
        }
        return responses;
    }

    @Override
    public String promptPopup(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().strip();
    }
}
