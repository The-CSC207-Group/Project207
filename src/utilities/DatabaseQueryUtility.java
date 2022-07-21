package utilities;

import database.DataMapperGateway;
import entities.User;

import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DatabaseQueryUtility {
    /**
     * Converts given stream of some type to arraylist of the same type with all the same items.
     * @param stream Stream<T> - some stream any type.
     * @return ArrayList<T> - arraylist of the items in the stream.
     * @param <T> any type.
     */
    public <T> ArrayList<T> toArrayList(Stream<T> stream){
        return stream.collect(Collectors.toCollection(ArrayList::new));
    }
}

