package presenter.entityViews;

import dataBundles.SecretaryData;

import java.util.List;

/**
 * The Secretary entity's view.
 */
public class SecretaryView extends EntityView<SecretaryData> {

    /**
     * @param item The secretary data bundle to view.
     * @return Returns item's full secretary view.
     */
    @Override
    public String viewFull(SecretaryData item) {
        return viewUsername(item);
    }

    /**
     * @param item The secretary data bundle to view.
     * @return Returns the secretary's username as a view.
     */
    public String viewUsername(SecretaryData item) {
        return "Secretary username is " + item.getUsername() + ".";
    }

}
