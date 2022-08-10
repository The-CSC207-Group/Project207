package presenters.entityViews;

import dataBundles.SecretaryData;

/**
 * The Secretary entity's view.
 */
public class SecretaryView extends UserView<SecretaryData> {

    /**
     * @param item SecretaryData bundle to view.
     * @return String representing item's full secretary view.
     */
    @Override
    public String viewFull(SecretaryData item) {
        return viewUsername(item);
    }

    /**
     * @param item SecretaryData bundle to view.
     * @return String representing the secretary's username as a view.
     */
    @Override
    public String viewUsername(SecretaryData item) {
        return "Secretary username is " + item.getUsername() + ".";
    }

}
