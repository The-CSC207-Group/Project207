package presenter.entityViews;

import dataBundles.AdminData;

public class AdminView {

    public String view(AdminData item) {
        String username = item.getUsername();
        return "Admin username is " + username;
    }
}
