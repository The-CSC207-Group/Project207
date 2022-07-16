package presenter.entityViews;

import dataBundles.PatientData;

import java.util.List;

public class PatientView extends EntityView<PatientData> {

    @Override
    public String viewFull(PatientData item) {
        return viewUsername(item) + "\n"
                + viewHealthNumber(item);
    }

    public String viewUsername(PatientData item) {
        return "Patient username is " + item.getUsername() + ".";
    }

    public String viewHealthNumber(PatientData item) {
        return "Patient health number is " + item.getHealthNumber() + ".";
    }

    public String viewUsernameFromList(List<PatientData> items) {
        StringBuilder appendedOutput = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            appendedOutput.append(viewUsername(items.get(i)));
            if (i != items.size() - 1) {
                appendedOutput.append("\n");
            }
        }
        return appendedOutput.toString();
    }

    public String viewHealthNumberFromList(List<PatientData> items) {
        StringBuilder appendedOutput = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            appendedOutput.append(viewHealthNumber(items.get(i)));
            if (i != items.size() - 1) {
                appendedOutput.append("\n");
            }
        }
        return appendedOutput.toString();
    }
}
