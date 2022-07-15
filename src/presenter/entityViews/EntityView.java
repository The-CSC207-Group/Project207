package presenter.entityViews;

public interface EntityView<T> {
    String view(T item);
}
