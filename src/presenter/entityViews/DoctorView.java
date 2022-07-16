package presenter.entityViews;

import dataBundles.DoctorData;

import java.util.List;

public class DoctorView extends EntityView<DoctorData> {

    @Override
    public String viewFull(DoctorData item) {
        return viewUsername(item);
    }

    public String viewUsername(DoctorData item) {
        return "Doctor username is " + item.getUsername() + ".";
    }

    public String viewUsernameFromList(List<DoctorData> items) {
        StringBuilder appendedOutput = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            appendedOutput.append(viewUsername(items.get(i)));
            if (i != items.size() - 1) {
                appendedOutput.append("\n");
            }
        }
        return appendedOutput.toString();
    }
}
