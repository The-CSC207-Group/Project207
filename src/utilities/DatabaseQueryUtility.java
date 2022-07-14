package utilities;

import database.DataMapperGateway;

import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DatabaseQueryUtility {

    /**
     * Returns a stream of all items in the database.
     * @param database database where the items will be retrieved from
     * @return list of all items in the database
     * @param <T> type of the database we are querying from.
     */
    public <T> Stream<T> getAllItems(DataMapperGateway<T> database){
        return database.getAllIds().stream().map(id -> getFromDatabase(database, id));
    }

    /**
     * Returns stream of items with the specified ids from the given database.
     * @param database database where ids will be queried from
     * @param ids list of ids to query from database
     * @return Stream of type of the items that we are querying
     * @param <T> Type of the item you would like to query
     */
    public <T> Stream<T> getItemsWithIds(DataMapperGateway<T> database, ArrayList<Integer> ids){
        Stream<T> stream =  ids.stream().map(id -> getFromDatabase(database, id));
        if (stream.anyMatch(Objects::isNull)){return null;}
        return stream;
    }

    private <T> T getFromDatabase(DataMapperGateway<T> database, Integer id){
        if (id == null){return null;}
        T item = database.get(id);
        if (item == null){return null;}
        return item;
    }


    /**
     * Converts given stream of some type to arraylist of the same type with all the same items.
     * @param stream .
     * @return .
     * @param <T> .
     */
    public <T> ArrayList<T> toArrayList(Stream<T> stream){
        return stream.collect(Collectors.toCollection(ArrayList::new));
    }
}

