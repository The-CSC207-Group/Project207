package useCases.managers;

import database.DataMapperGateway;
import database.Database;
import entities.TimeBlock;
import entities.Clinic;
import utilities.ZonedDateTimeCreator;

public class TimeBlockManager {

    public TimeBlockManager(Database database){

    }

    public TimeBlock createTimeBlock(Integer year, Integer month, Integer day, Integer hour){
        return new TimeBlock(ZonedDateTimeCreator(year, month, day, hour, 0, ),
    }
}
