package presenter.entityViews;

/**
 * ViewMethod interface used by EntityView
 * @param <T> The type of object being viewed.
 */
public interface ViewMethod<T> {

    String view(T object);

}
