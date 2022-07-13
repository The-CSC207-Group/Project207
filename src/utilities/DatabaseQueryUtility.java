package utilities;

import database.DataMapperGateway;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DatabaseQueryUtility {


    public <T> Stream<T> getAllItems(DataMapperGateway<T> database){
        return database.getAllIds().stream().map(database::get);
    }

    public <T> ArrayList<T> toArrayList(Stream<T> stream){
        return stream.collect(Collectors.toCollection(ArrayList::new));
    }
}

