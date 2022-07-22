package presenter.entityViews;

import java.util.List;

/**
 * The generic entity view.
 * @param <T> The data bundle that the entity view will use.
 */
public abstract class EntityView<T> {

    /**
     * @param string String to be returned if a string is inputted.
     * @param defaultString String to be returned if no string or a blank string was inputted in string.
     * @return String string or defaultString depending on the value of string.
     */
    protected String getDefaultString(String string, String defaultString) {
        if (string == null) {
            return defaultString;
        } else if (string.isBlank()) {
            return defaultString;
        } else {
            return string;
        }
    }

    /**
     * @param string String to be returned if a string is inputted.
     * @return String string or 'N/A' depending on if a string was inputted in string.
     */
    protected String getDefaultStringNA(String string) {
        return getDefaultString(string, "N/A");
    }

    /**
     * @param item T data bundle to view.
     * @return String representing a full entity view of item.
     */
    public abstract String viewFull(T item);

    /**
     * @param items List<T> of data bundles to view.
     * @return String representing the full entity view of all items in items.
     */
    public String viewFullFromList(List<T> items) {
        return viewFromList(items, this::viewFull);
    }

    /**
     * @param items List<T> of data bundles to view.
     * @return String representing a full entity view as an enumeration of all items in items.
     */
    public String viewFullAsEnumerationFromList(List<T> items) {
        return viewAsEnumerationFromList(items, this::viewFull);
    }

    /**
     * @param items List<T> of data bundles to view.
     * @param function ViewMethod<T> representing the view function to be used.
     * @return String representing an entity view of all items in items using the view function function.
     */
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

    /**
     * @param items List<T> of data bundles to view.
     * @param function ViewMethod<T> representing the view function to be used.
     * @return String representing an entity view as an enumeration of all items in items using the view function
     * function.
     */
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
