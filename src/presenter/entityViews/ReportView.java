package presenter.entityViews;

import dataBundles.ReportData;

import java.util.List;

/**
 * The Report entity's view.
 */
public class ReportView extends EntityView<ReportData> {

    /**
     * @param item The report data bundle to view.
     * @return Returns item's full report view.
     */
    @Override
    public String viewFull(ReportData item) {
        return viewDateNoted(item) + "\n"
                + viewHeader(item) + "\n"
                + viewBody(item);
    }

    /**
     * @param item The report data bundle to view.
     * @return Returns the report's date noted as a view.
     */
    public String viewDateNoted(ReportData item) {
        return "Report was noted on " + item.getDateNoted() + ".";
    }

    /**
     * @param item The report data bundle to view.
     * @return Returns the report's header as a view.
     */
    public String viewHeader(ReportData item) {
        return "Report header reads: " + item.getHeader();
    }
    /**
     * @param item The report data bundle to view.
     * @return Returns the report's body as a view.
     */
    public String viewBody(ReportData item) {
        return "Report body reads: " + item.getBody();
    }

}
