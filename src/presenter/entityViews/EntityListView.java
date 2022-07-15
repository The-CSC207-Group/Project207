package presenter.entityViews;

import java.util.List;

public interface EntityListView<T> {
    void viewFromList(List<T> items);
}
