package presenter.entityViews;

import dataBundles.SecretaryData;

import java.util.List;

public class SecretaryView extends EntityView<SecretaryData> {

    @Override
    public String viewFull(SecretaryData item) {
        return viewUsername(item);
    }

    public String viewUsername(SecretaryData item) {
        return "Secretary username is " + item.getUsername() + ".";
    }

}
