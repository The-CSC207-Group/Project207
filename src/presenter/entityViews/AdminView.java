package presenter.entityViews;

import dataBundles.AdminData;

public class AdminView extends EntityView<AdminData> {

    @Override
    public String view(AdminData item) {
        return "Admin username is " + item.getUsername() + ".";
    }
}
