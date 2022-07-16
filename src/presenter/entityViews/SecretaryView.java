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

    public String viewUsernameFromList(List<SecretaryData> items) {
        StringBuilder appendedOutput = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            appendedOutput.append(viewUsername(items.get(i)));
            if (i != items.size() - 1) {
                appendedOutput.append("\n");
            }
        }
        return appendedOutput.toString();
    }
}
