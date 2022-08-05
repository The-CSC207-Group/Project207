// PHASE 2 FILE

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
                + viewBody(item) + "\n"
                + viewDoctor(item);
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

    /**

     * @param item ReportData bundle to view.
     * @return String representation of DoctorId as a view.
     */
    public String viewDoctor(ReportData item) {
        return "Report was noted by doctor ID: " + item.getDoctorId();
    }

}
