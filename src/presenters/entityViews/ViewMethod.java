package presenters.entityViews;

/**
 * ViewMethod interface used by EntityView.
 *
 * @param <T> The type of object being viewed.
 */
public interface ViewMethod<T> {

    /**
     * @param object T the general object to be viewed.
     * @return String representing the view of the object.
     */
    String view(T object);

}
