package useCases.query;

import entities.Prescription;

import javax.swing.text.html.parser.Entity;

public abstract class QueryCondition {

    public abstract <T> boolean isTrue(T item);
}
