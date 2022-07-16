package presenter.entityViews;

import dataBundles.PrescriptionData;

import java.util.List;

public class PrescriptionView extends EntityView<PrescriptionData> {

    @Override
    public String viewFull(PrescriptionData item) {
        return viewDateNoted(item) + "\n"
                + viewExpiryDate(item) + "\n"
                + viewHeader(item) + "\n"
                + viewBody(item) + "\n";
    }

    public String viewDateNoted(PrescriptionData item) {
        return "Prescription was noted on " + item.getDateNoted() + ".";
    }

    public String viewExpiryDate(PrescriptionData item) {
        return "Prescription expires on " + item.getExpiryDate() + ".";
    }

    public String viewHeader(PrescriptionData item) {
        return "Prescription header reads: " + item.getHeader();
    }

    public String viewBody(PrescriptionData item) {
        return "Prescription body reads: " + item.getBody();
    }

    public String viewDateNotedFromList(List<PrescriptionData> items) {
        StringBuilder appendedOutput = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            appendedOutput.append(viewDateNoted(items.get(i)));
            if (i != items.size() - 1) {
                appendedOutput.append("\n");
            }
        }
        return appendedOutput.toString();
    }

    public String viewExpiryDateFromList(List<PrescriptionData> items) {
        StringBuilder appendedOutput = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            appendedOutput.append(viewExpiryDate(items.get(i)));
            if (i != items.size() - 1) {
                appendedOutput.append("\n");
            }
        }
        return appendedOutput.toString();
    }

    public String viewHeaderFromList(List<PrescriptionData> items) {
        StringBuilder appendedOutput = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            appendedOutput.append(viewHeader(items.get(i)));
            if (i != items.size() - 1) {
                appendedOutput.append("\n");
            }
        }
        return appendedOutput.toString();
    }

    public String viewBodyFromList(List<PrescriptionData> items) {
        StringBuilder appendedOutput = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            appendedOutput.append(viewBody(items.get(i)));
            if (i != items.size() - 1) {
                appendedOutput.append("\n");
            }
        }
        return appendedOutput.toString();
    }

}
