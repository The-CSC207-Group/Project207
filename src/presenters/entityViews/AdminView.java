// PHASE 2 FILE

package presenters.entityViews;

import dataBundles.AdminData;

/**
 * The Admin entity's view.
 */
public class AdminView extends UserView<AdminData> {

    /**
     * @param item AdminData to view.
     * @return String representing item's full admin view.
     */
    @Override
    public String viewFull(AdminData item) {
        return viewUsername(item);
    }

    /**
     * @param item AdminData to view.
     * @return String representing the admin's username as a view.
     */
    @Override
    public String viewUsername(AdminData item) {
        return "Admin username is " + item.getUsername() + ".";
    }

}
