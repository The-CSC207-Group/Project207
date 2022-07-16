package presenter.entityViews;

import java.util.List;

public abstract class EntityView<T> {

    public abstract String viewFull(T item);

    public String viewFullFromList(List<T> items) {
        StringBuilder appendedOutput = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            appendedOutput.append(viewFull(items.get(i)));
            if (i != items.size() - 1) {
                appendedOutput.append("\n");
            }
        }
        return appendedOutput.toString();
    }
}
