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
}
