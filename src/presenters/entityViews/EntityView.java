package presenters.entityViews;

import java.util.List;

/**
 * The generic entity view.
 *
 * @param <T> The data bundle that the entity view will use.
 */
public abstract class EntityView<T> {

    /**
     * @param string        String to be returned if a string is inputted.
     * @param defaultString String to be returned if no string or a blank string was inputted in string.
     * @return String or defaultString depending on the value of string.
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
     * @return String - string or 'N/A' depending on if a string was inputted in string.
     */
    protected String getDefaultStringNA(String string) {
        return getDefaultString(string, "N/A");
    }

    /**
     * @param item The data bundle to view.
     * @return Returns a full entity view of item.
     */
    public abstract String viewFull(T item);

    /**
     * @param items The list of generic data bundles to view.
     * @return Returns a full entity view of all items in items.
     */
    public String viewFullFromList(List<? extends T> items) {
        return viewFromList(items, this::viewFull);
    }

    /**
     * @param items The list of generic data bundles to view.
     * @return Returns a full entity view as an enumeration of all items in items.
     */
    public String viewFullAsEnumerationFromList(List<? extends T> items) {
        return viewAsEnumerationFromList(items, this::viewFull);
    }

    /**
     * @param items    The list of generic data bundles to view.
     * @param function The view function to be used.
     * @return Returns an entity view of all items in items using the view function function.
     */
    public String viewFromList(List<? extends T> items, ViewMethod<T> function) {
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
     * @param items    The list of generic data bundles to view.
     * @param function The view function to be used.
     * @return Returns an entity view as an enumeration of all items in items using the view function function.
     */
    public String viewAsEnumerationFromList(List<? extends T> items, ViewMethod<T> function) {
        StringBuilder appendedOutput = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            appendedOutput.append(i + 1).append(": ");
            appendedOutput.append(function.view(items.get(i)));
            if (i != items.size() - 1) {
                appendedOutput.append("\n");
            }
        }
        return appendedOutput.toString();
    }

}
