package presenter.entityViews;

import dataBundles.AdminData;

public class AdminView extends EntityView<AdminData> {

    @Override
    public String view(AdminData item) {
        String username = item.getUsername();
        return "Admin username is " + username;
    }
}
