package presenter.entityViews;

import dataBundles.ReportData;

import java.util.List;

public class ReportView extends EntityView<ReportData> {

    @Override
    public String viewFull(ReportData item) {
        return viewDateNoted(item) + "\n"
                + viewHeader(item) + "\n"
                + viewBody(item);
    }

    public String viewDateNoted(ReportData item) {
        return "Report was noted on " + item.getDateNoted() + ".";
    }

    public String viewHeader(ReportData item) {
        return "Report header reads: " + item.getHeader();
    }

    public String viewBody(ReportData item) {
        return "Report body reads: " + item.getBody();
    }

    public String viewDateNotedFromList(List<ReportData> items) {
        StringBuilder appendedOutput = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            appendedOutput.append(viewDateNoted(items.get(i)));
            if (i != items.size() - 1) {
                appendedOutput.append("\n");
            }
        }
        return appendedOutput.toString();
    }

    public String viewHeaderFromList(List<ReportData> items) {
        StringBuilder appendedOutput = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            appendedOutput.append(viewHeader(items.get(i)));
            if (i != items.size() - 1) {
                appendedOutput.append("\n");
            }
        }
        return appendedOutput.toString();
    }

    public String viewBodyFromList(List<ReportData> items) {
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
