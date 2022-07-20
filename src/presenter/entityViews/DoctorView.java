package presenter.entityViews;

import dataBundles.DoctorData;

/**
 * The Doctor entity's view.
 */
public class DoctorView extends EntityView<DoctorData> {

    /**
     * @param item The doctor data bundle to view.
     * @return Returns item's full doctor view.
     */
    @Override
    public String viewFull(DoctorData item) {
        return viewUsername(item);
    }

    /**
     * @param item The doctor data bundle to view.
     * @return Returns the doctor's username as a view.
     */
    public String viewUsername(DoctorData item) {
        return "Doctor username is " + item.getUsername() + ".";
    }
}
