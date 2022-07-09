package useCases.query;

import entities.Prescription;

import javax.swing.text.html.parser.Entity;

public abstract class QueryCondition<T> {
    private Boolean desiredStatus = true;
    public QueryCondition(Boolean desiredStatus){
        this.desiredStatus = desiredStatus;
    }
    public abstract boolean isTrue(T item);
    public Boolean getDesiredStatus(){
        return desiredStatus;
    };
}
