package presenter.entityViews;

import java.util.List;

public interface EntityListView<T> {
    String viewFromList(List<T> items);
}
