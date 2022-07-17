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

}
