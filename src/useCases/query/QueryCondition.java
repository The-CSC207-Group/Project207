package useCases.query;

import entities.Prescription;

import javax.swing.text.html.parser.Entity;

public abstract class QueryCondition {
    private Boolean desiredStatus = true;
    public QueryCondition(Boolean desiredStatus){
        this.desiredStatus = desiredStatus;
    }
    public abstract <T> boolean isTrue(T item);
    public Boolean getDesiredStatus(){
        return desiredStatus;
    };
}
