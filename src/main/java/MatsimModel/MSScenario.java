package MatsimModel;

import AbmModel.Scenario;

/**
 * The concept implementation of a simulation result.
 * A scenario contains the information produced by one launch of a traffic simulation
 * It contains the some information of a simulation, and the data of simulation
 *
 * WHAT THE PROCESSOR SHOULD DO HERE :
 *
 * For now we only deal with all Mandatory classes and eventually Facility class
 * We don't care about other classes (Relation, TypeCollector...) immediatly
 * And we don't take the types attributes into account either, whose implementation
 * is likely to change in the future if we have time to deal with it at the
 * end of the project
 *
 * The processor have to fill the following Maps (like network with nodes and links) :
 * persons -> with all the persons of the scenario
 * vehicles -> with all the vehicles of the scenario
 * events -> with all the events of the scenario
 * facilities -> with all the facilities of the scenario
 * They don't have to be sorted by id, however events need to be sorted by time
 * so dynamic model can easily makes time jump
 * /!\ Now we use map(id, obj) rather than list, because this way
 * will be much more easier and efficient to recover instance from id
 * and we don't need to use instancesPerID anymore
 *
 * The Scenario class will also have to be abstract
 */
public class MSScenario extends Scenario {

    /**
     * Constructor
     * @param name the name of the scenario
     * @param unit the unit system of the scenario
     */
    public MSScenario(String name, String unit) {
        super(name, unit, new MSNetwork());
    }

    /**
     * default constructor
     */
    public MSScenario() {
        super("", "", new MSNetwork());
    }
}
