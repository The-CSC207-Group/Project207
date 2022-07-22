package presenter.entityViews;

import dataBundles.PatientData;

/**
 * The Patient entity's view.
 */
public class PatientView extends UserView<PatientData> {

    /**
     * @param item The patient data bundle to view.
     * @return Returns item's full patient view.
     */
    @Override
    public String viewFull(PatientData item) {
        return viewUsername(item) + "\n"
                + viewHealthNumber(item);
    }

    /**
     * @param item The patient data bundle to view.
     * @return Returns the patient's username as a view.
     */
    @Override
    public String viewUsername(PatientData item) {
        return "Patient username is " + item.getUsername() + ".";
    }

    /**
     * @param item The patient data bundle to view.
     * @return Returns the patient's health number as a view.
     */
    public String viewHealthNumber(PatientData item) {
        return "Patient health number is " + item.getHealthNumber() + ".";
    }

}
