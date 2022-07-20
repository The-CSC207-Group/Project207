package presenter.entityViews;

import dataBundles.PrescriptionData;

/**
 * The Prescription entity's view.
 */
public class PrescriptionView extends EntityView<PrescriptionData> {

    /**
     * @param item The prescription data bundle to view.
     * @return Returns item's full prescription view.
     */
    @Override
    public String viewFull(PrescriptionData item) {
        return viewDateNoted(item) + "\n"
                + viewExpiryDate(item) + "\n"
                + viewHeader(item) + "\n"
                + viewBody(item) + "\n";
    }

    /**
     * @param item The prescription data bundle to view.
     * @return Returns the prescription's date noted as a view.
     */
    public String viewDateNoted(PrescriptionData item) {
        return "Prescription was noted on " + item.getDateNoted() + ".";
    }

    /**
     * @param item The prescription data bundle to view.
     * @return Returns the prescription's expiry date as a view.
     */
    public String viewExpiryDate(PrescriptionData item) {
        return "Prescription expires on " + item.getExpiryDate() + ".";
    }

    /**
     * @param item The prescription data bundle to view.
     * @return Returns the prescription's header as a view.
     */
    public String viewHeader(PrescriptionData item) {
        return "Prescription header reads: " + item.getHeader();
    }

    /**
     * @param item The prescription data bundle to view.
     * @return Returns the prescription's body as a view.
     */
    public String viewBody(PrescriptionData item) {
        return "Prescription body reads: " + item.getBody();
    }
}
