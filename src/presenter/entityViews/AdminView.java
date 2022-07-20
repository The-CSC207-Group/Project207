package presenter.entityViews;

import dataBundles.AdminData;

/**
 * The Admin entity's view.
 */
public class AdminView extends EntityView<AdminData> {

    /**
     * @param item The admin data bundle to view.
     * @return Returns item's full admin view.
     */
    @Override
    public String viewFull(AdminData item) {
        return viewUsername(item);
    }

    /**
     * @param item The admin data bundle to view.
     * @return Returns the admin's username as a view.
     */
    public String viewUsername(AdminData item) {
        return "Admin username is " + item.getUsername() + ".";
    }
}
