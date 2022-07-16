package presenter.entityViews;

import dataBundles.AdminData;

import java.util.List;

public class AdminView extends EntityView<AdminData> {

    @Override
    public String viewFull(AdminData item) {
        return viewUsername(item);
    }

    public String viewUsername(AdminData item) {
        return "Admin username is " + item.getUsername() + ".";
    }

    public String viewUsernameFromList(List<AdminData> items) {
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
