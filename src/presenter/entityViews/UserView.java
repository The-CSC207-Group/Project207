package presenter.entityViews;

import dataBundles.UserData;

public abstract class UserView<T extends UserData<?>> extends EntityView<T> {

    @Override
    public String viewFull(T item) {
        return viewUsername(item);
    }

    public abstract String viewUsername(T item);
}
