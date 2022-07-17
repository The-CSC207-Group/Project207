package presenter.entityViews;

import dataBundles.PatientData;

public class PatientView extends EntityView<PatientData> {

    @Override
    public String viewFull(PatientData item) {
        return viewUsername(item) + "\n"
                + viewHealthNumber(item);
    }

    public String viewUsername(PatientData item) {
        return "Patient username is " + item.getUsername() + ".";
    }

    public String viewHealthNumber(PatientData item) {
        return "Patient health number is " + item.getHealthNumber() + ".";
    }

}
