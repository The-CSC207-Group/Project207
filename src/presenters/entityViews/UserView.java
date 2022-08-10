package presenters.entityViews;

import dataBundles.UserData;

/**
 * The User entity's view.
 *
 * @param <T> The User data bundle that the user view will use.
 */
public abstract class UserView<T extends UserData<?>> extends EntityView<T> {

    /**
     * @param item T user data bundle to view.
     * @return String representing item's full user view.
     */
    @Override
    public String viewFull(T item) {
        return viewUsername(item);
    }

    /**
     * @param item T user data bundle to view.
     * @return String representing item's username as a view.
     */
    public abstract String viewUsername(T item);
}
