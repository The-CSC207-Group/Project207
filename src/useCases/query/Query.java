package useCases.query;

import database.DataMapperGateway;
import entities.Prescription;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.HashSet;

public class Query<T> {

    public ArrayList<T> execute(DataMapperGateway<T> database, ArrayList<QueryCondition> conditions){
        HashSet<Integer> Ids = database.getAllIds();
        ArrayList<T> res = new ArrayList<>();
        for (Integer Id : Ids) {
            T currItem = database.get(Id);

            if (!checkConditions(currItem, conditions)) {return null;}

            res.add(currItem);
        }
        return res;
    }
    private boolean checkConditions(T item, ArrayList<QueryCondition> conditions){
        Boolean conditionsMet = true;
        for (QueryCondition condition : conditions){
            conditionsMet = conditionsMet & condition.isTrue(item);
        }
        return conditionsMet;
    }
}
