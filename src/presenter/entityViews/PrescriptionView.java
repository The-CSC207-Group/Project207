package presenter.entityViews;

import dataBundles.PrescriptionData;

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
}
