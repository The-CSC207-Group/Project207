package presenter.entityViews;

import dataBundles.DoctorData;

/**
 * The Doctor entity's view.
 */
public class DoctorView extends UserView<DoctorData> {

    /**
     * @param item DoctorData bundle to view.
     * @return String representing item's full doctor view.
     */
    @Override
    public String viewFull(DoctorData item) {
        return viewUsername(item);
    }

    /**
     * @param item DoctorData bundle to view.
     * @return String representing the doctor's username as a view.
     */
    @Override
    public String viewUsername(DoctorData item) {
        return "Doctor username is " + item.getUsername() + ".";
    }

}
