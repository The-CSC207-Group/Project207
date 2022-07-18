package presenter.entityViews;

import java.util.List;

public abstract class EntityView<T> {

    protected String getDefaultString(String string, String defaultString) {
        if (string == null) {
            return defaultString;
        } else if (string.isBlank()) {
            return defaultString;
        } else {
            return string;
        }
    }

    protected String getDefaultStringNA(String string) {
        return getDefaultString(string, "N/A");
    }

    public abstract String viewFull(T item);

    public String viewFullFromList(List<T> items) {
        return viewFromList(items, this::viewFull);
    }

    public String viewFullAsEnumerationFromList(List<T> items) {
        return viewAsEnumerationFromList(items, this::viewFull);
    }

    public String viewFromList(List<T> items, ViewMethod<T> function) {
        StringBuilder appendedOutput = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            appendedOutput.append(function.view(items.get(i)));
            if (i != items.size() - 1) {
                appendedOutput.append("\n");
            }
        }
        return appendedOutput.toString();
    }

    public String viewAsEnumerationFromList(List<T> items, ViewMethod<T> function) {
        StringBuilder appendedOutput = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            appendedOutput.append(i + 1).append(":\n");
            appendedOutput.append(function.view(items.get(i)));
            if (i != items.size() - 1) {
                appendedOutput.append("\n");
            }
        }
        return appendedOutput.toString();
    }
}
