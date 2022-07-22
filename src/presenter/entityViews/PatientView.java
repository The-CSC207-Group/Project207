package presenter.entityViews;

import dataBundles.PatientData;

/**
 * The Patient entity's view.
 */
public class PatientView extends UserView<PatientData> {

    /**
     * @param item PatientData bundle to view.
     * @return String representing item's full patient view.
     */
    @Override
    public String viewFull(PatientData item) {
        return viewUsername(item) + "\n"
                + viewHealthNumber(item);
    }

    /**
     * @param item PatientData bundle to view.
     * @return String representing the patient's username as a view.
     */
    @Override
    public String viewUsername(PatientData item) {
        return "Patient username is " + item.getUsername() + ".";
    }

    /**
     * @param item Patient bundle to view.
     * @return String representing the patient's health number as a view.
     */
    public String viewHealthNumber(PatientData item) {
        return "Patient health number is " + item.getHealthNumber() + ".";
    }

}
