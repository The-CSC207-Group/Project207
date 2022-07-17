package presenter.entityViews;

import dataBundles.DoctorData;

public class DoctorView extends EntityView<DoctorData> {

    @Override
    public String viewFull(DoctorData item) {
        return viewUsername(item);
    }

    public String viewUsername(DoctorData item) {
        return "Doctor username is " + item.getUsername() + ".";
    }

}
