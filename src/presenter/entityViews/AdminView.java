package presenter.entityViews;

import dataBundles.AdminDataBundle;

public class AdminView {

    public String view(AdminDataBundle item) {
        String username = item.getUsername();
        return "Admin username is " + username;
    }
}
