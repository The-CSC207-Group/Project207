package presenter.entityViews;

import dataBundles.DoctorData;

public class DoctorView extends EntityView<DoctorData> {

    @Override
    public String view(DoctorData item) {
        return "Doctor username is " + item.getUsername() + ".";
    }
}
