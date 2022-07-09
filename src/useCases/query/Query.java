package useCases.query;

import database.DataMapperGateway;
import entities.Prescription;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.HashSet;

public class Query<T> {

    public ArrayList<T> returnAllMeetingConditions(DataMapperGateway<T> database, ArrayList<QueryCondition<T>> conditions){
        HashSet<Integer> Ids = database.getAllIds();
        ArrayList<T> res = new ArrayList<>();
        for (Integer Id : Ids) {
            T currItem = database.get(Id);

            if (checkConditions(currItem, conditions)) {
                res.add(currItem);}
        }
        return res;
    }
    public T returnFirstMeetingConditions(DataMapperGateway<T> database, ArrayList<QueryCondition<T>> conditions){
        HashSet<Integer> Ids = database.getAllIds();
        for (Integer Id : Ids) {
            T currItem = database.get(Id);

            if (checkConditions(currItem, conditions)) {return currItem;}
        }
        return null;
    }
    private boolean checkConditions(T item, ArrayList<QueryCondition<T>> conditions){
        Boolean conditionsMet = true;
        for (QueryCondition<T> condition : conditions){
            conditionsMet = conditionsMet & (condition.isTrue(item) == condition.getDesiredStatus());
        }
        return conditionsMet;
    }
}
