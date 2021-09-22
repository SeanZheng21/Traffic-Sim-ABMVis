package MatsimModel;

import AbmModel.Event;
import AbmModel.Person;

import java.util.*;

/**
 * Implementation person concept that represents an acceptable person for Matsim simulation results
 * A person is an agent
 * Mandatory concept implementation
 *
 * WHAT THE PROCESSOR SHOULD DO FOR THIS CLASS :
 * Fill the "relatedEvents" list with all the MSEvents in which the person (this) is involved
 * They have to be in the right order according to the time
 */
public class MSPerson extends Person {

    /**
     * constructor
     * @param id id of the person
     * @param sex sex of the person
     * @param age age of the person
     * @param license driver licenses of the person
     * @param car_avail how often the person has a car available
     * @param job job of the person
     * @param relatedEvents the related event of the person
     */
    public MSPerson(int id, String sex, int age, String license, String car_avail, String job, List<Event> relatedEvents) {
        super(id, sex, age, license, car_avail, job, relatedEvents, new MSType());
        this.types.setInstance(this);
    }

    /**
     * default constructor
     */
    public MSPerson() {
        super(-1, "", 0, "", "", "", new ArrayList<>(), new MSType());
        this.types.setInstance(this);
    }
}
