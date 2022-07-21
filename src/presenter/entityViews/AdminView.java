package presenter.entityViews;

import dataBundles.AdminData;

/**
 * The Admin entity's view.
 */
public class AdminView extends EntityView<AdminData> {

    /**
     * @param item AdminData bundle to view.
     * @return String representing item's full admin view.
     */
    @Override
    public String viewFull(AdminData item) {
        return viewUsername(item);
    }

    /**
     * @param item AdminData bundle to view.
     * @return String representing the admin's username as a view.
     */
    public String viewUsername(AdminData item) {
        return "Admin username is " + item.getUsername() + ".";
    }
}
