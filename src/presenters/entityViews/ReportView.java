package presenters.entityViews;

import dataBundles.ReportData;

/**
 * The Report entity's view.
 */
public class ReportView extends EntityView<ReportData> {

    /**
     * @param item ReportData bundle to view.
     * @return String representing item's full report view.
     */
    @Override
    public String viewFull(ReportData item) {
        return viewDateNoted(item) + "\n"
                + viewHeader(item) + "\n"
                + viewBody(item);
    }

    /**
     * @param item ReportData bundle to view.
     * @return String representing the report's date noted as a view.
     */
    public String viewDateNoted(ReportData item) {
        return "Report was noted on " + item.getDateNoted() + ".";
    }

    /**
     * @param item ReportData bundle to view.
     * @return String representing the report's header as a view.
     */
    public String viewHeader(ReportData item) {
        return "Report header reads: " + item.getHeader();
    }

    /**
     * @param item ReportData bundle to view.
     * @return String representing the report's body as a view.
     */
    public String viewBody(ReportData item) {
        return "Report body reads: " + item.getBody();
    }

}
