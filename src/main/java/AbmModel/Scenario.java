package AbmModel;

import java.util.*;


/**
 * The concept of a simulation result.
 * A scenario contains the information produced by one launch of a traffic simulation
 * It contains the some information of a simulation, and the data of simulation
 */
public class Scenario {

    /**
     * Some basic information of a simulation result, maybe more?
     * THe name of the simulation
     */
    protected String name;
    /**
     * Some basic information of a simulation result, maybe more
     * THe unit of the simulation, metric or imperial
     */
    protected String unit;

    /**
     * The Network of the scenario
     */
    protected Network network;

    /**
     * The Map of persons in the scenario
     */
    protected Map<Integer, Person> persons;

    /**
     * The Map of vehicles in the scenario
     */
    protected Map<Integer, Vehicle> vehicles;

    /**
     * The Map of events in the scenario
     */
    protected Map<Integer, Event> events;

    /**
     * The Map of facility in the scenario
     */
    protected Map<Integer, Facility> facilities;

    /**
     * the private map of mandatory data of a simulation, organized by Class
     * key: the class of the model
     * value: a set that contains all the instances of said model
     */
    protected final Map<Class<? extends MandatorySimModel>, Set<MandatorySimModel>> mandatorySimModels;

    /**
     * the private map of optional data of a simulation, organized by Class
     * key: the class of the model
     * value: a set that contains all the instances of said model
     */
    protected final Map<Class<? extends OptionalSimModel>, Set<OptionalSimModel>> optionalSimModels;


    /**
     * Constructor that creates a scenario from its basic information
     * In order to create a scenario, all basic information is needed
     * Initialize the data to empty
     *
     * @param name the name of the simulation result
     * @param unit the unit of the simulation result
     * @param network the network of the scenario
     */
    public Scenario(String name, String unit, Network network) {
        this.name = name;
        this.unit = unit;
        mandatorySimModels = new HashMap<>();
        optionalSimModels = new HashMap<>();
        persons = new HashMap<>();
        vehicles = new HashMap<>();
        events = new HashMap<>();
        facilities = new HashMap<>();
        this.network = network;
    }

    /**
     * reason: this constructor did not initialize the network and makes Null pointer Exception
     * @param name the name of the scenario
     * @param unit the unit system of the scenario
     */
    @Deprecated
    public Scenario(String name, String unit) {
        this.name = name;
        this.unit = unit;
        mandatorySimModels = new HashMap<>();
        optionalSimModels = new HashMap<>();
        persons = new HashMap<>();
        vehicles = new HashMap<>();
        events = new HashMap<>();
        facilities = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Network getNetwork() {
        return network;
    }

    public void setNetwork(Network network) {
        this.network = network;
    }

    public Map<Integer, Person> getPersons() {
        return persons;
    }

    public void setPersons(Map<Integer, Person> persons) {
        this.persons = persons;
    }

    public void setPersonsCollection(Set<MandatorySimModel> persons) {
        for (MandatorySimModel model: persons) {
            if (model instanceof Person)
                addPerson((Person)model);
        }
    }

    public Map<Integer, Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(Map<Integer, Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public void setVehiclesCollection(Set<MandatorySimModel> persons) {
        for (MandatorySimModel model: persons) {
            if (model instanceof Vehicle)
                addVehicle((Vehicle)model);
        }
    }

    public Map<Integer, Event> getEvents() {
        return events;
    }

    public void setEvents(Map<Integer, Event> events) {
        this.events = events;
    }

    public void setEventsCollection(Set<MandatorySimModel> events) {
        for (MandatorySimModel model: events) {
            if (model instanceof Event)
                addEvent((Event)model);
        }
    }

    public Map<Integer, Facility> getFacilities() {
        return facilities;
    }

    public void setFacilityCollection(Set<OptionalSimModel> persons) {
        for (OptionalSimModel model: persons) {
            if (model instanceof Facility)
                addFacility((Facility) model);
        }
    }

    public void setFacilities(Map<Integer, Facility> facilities) {
        this.facilities = facilities;
    }

    public Map<Class<? extends MandatorySimModel>, Set<MandatorySimModel>> getMandatorySimModels() {
        return mandatorySimModels;
    }

    public Map<Class<? extends OptionalSimModel>, Set<OptionalSimModel>> getOptionalSimModels() {
        return optionalSimModels;
    }

    /**
     * Get a person from his id
     * @param id The id of the person
     * @return The actual Person if it exists
     */
    public Person getPerson(int id) {
        return persons.get(id);
    }

    /**
     * Get a vehicle from his id
     * @param id The id of the vehicle
     * @return The actual Vehicle if it exists
     */
    public Vehicle getVehicle(int id) {
        return vehicles.get(id);
    }

    /**
     * Get an event from his id
     * @param id The id of the event
     * @return The actual Event if it exists
     */
    public Event getEvent(int id) {
        return events.get(id);
    }

    /**
     * Get a facility from his id
     * @param id The id of the facility
     * @return The actual Facility if it exists
     */
    public Facility getFacility(int id) {
        return facilities.get(id);
    }

    /**
     * Add a person to the map of persons
     * @param person The person to add
     */
    public void addPerson(Person person) {
        persons.put(person.getId(), person);
    }

    /**
     * Add a vehicle to the map of vehicles
     * @param vehicle The vehicle to add
     */
    public void addVehicle(Vehicle vehicle) {
        vehicles.put(vehicle.getId(), vehicle);
    }

    /**
     * Add an event to the map of events
     * @param event The event to add
     */
    public void addEvent(Event event) {
        events.put(event.getId(), event);
    }

    /**
     * Add a facility to the map of facilities
     * @param facility The facility to add
     */
    public void addFacility(Facility facility) {
        facilities.put(facility.getId(), facility);
    }

    /**
     * Add a set of mandatory models to the scenario
     * Create a new entry if the class is new to the map
     * If not, add to the existing entry's set
     * @param cls the class of the mandatory model
     * @param mandatorySimModels the result set
     */
    public void addMandatoryModels(Class<? extends MandatorySimModel> cls, Set<MandatorySimModel> mandatorySimModels) {
        if (this.mandatorySimModels.containsKey(cls)) {
            this.mandatorySimModels.get(cls).addAll(mandatorySimModels);
        } else {
            this.mandatorySimModels.put(cls, mandatorySimModels);
        }
    }

    /**
     * add a mandatory element in the scenario
     * @param cls the class of the element
     * @param mandatorySimModel the object added
     */
    public void addMandatoryModel(Class<? extends MandatorySimModel> cls, MandatorySimModel mandatorySimModel) {
        if (this.mandatorySimModels.containsKey(cls)) {
            this.mandatorySimModels.get(cls).add(mandatorySimModel);
        } else {
            Set<MandatorySimModel> s = new HashSet<>();
            s.add(mandatorySimModel);
            this.mandatorySimModels.put(cls, s);
        }
    }

    /**
     * Add a set of optional models to the scenario
     * Create a new entry if the class is new to the map
     * If not, add to the existing entry's set
     * @param cls the class of the optional model
     * @param optionalSimModels the result set
     */
    public void addOptionalModels(Class<? extends OptionalSimModel> cls, Set<OptionalSimModel> optionalSimModels) {
        if (this.optionalSimModels.containsKey(cls)) {
            this.optionalSimModels.get(cls).addAll(optionalSimModels);
        } else {
            this.optionalSimModels.put(cls, optionalSimModels);
        }
    }

    /**
     * add a optional element in the scenario
     * @param cls the class of the element
     * @param optionalSimModel the object added
     */
    public void addOptionalModel(Class<? extends OptionalSimModel> cls, OptionalSimModel optionalSimModel) {
        if (this.optionalSimModels.containsKey(cls)) {
            this.optionalSimModels.get(cls).add(optionalSimModel);
        } else {
            Set<OptionalSimModel> s = new HashSet<>();
            s.add(optionalSimModel);
            this.optionalSimModels.put(cls, s);
        }
    }

    /**
     * Description of the scenario in string
     * @return description of the scenario
     */
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Scenario:\n");
        stringBuilder.append("\tname: ").append(name).append("\n");
        stringBuilder.append("\tunit: ").append(unit).append("\n");
        stringBuilder.append("\tmandatory models:\n");
        for (Class<? extends MandatorySimModel> cls: mandatorySimModels.keySet()) {
            stringBuilder.append("\t\t").append(cls.getName()).append(": \n");
            Set<MandatorySimModel> set = mandatorySimModels.get(cls);
            for(MandatorySimModel elt: set)
                stringBuilder.append("\t\t\t").append(elt.toString()).append("\n");
        }
        stringBuilder.append("\toptional models:\n");
        for (Class<? extends OptionalSimModel> cls: optionalSimModels.keySet()) {
            stringBuilder.append("\t\t").append(cls.getName()).append(": \n");
            Set<OptionalSimModel> set = optionalSimModels.get(cls);
            for(OptionalSimModel elt: set)
                stringBuilder.append("\t\t\t").append(elt.toString()).append("\n");
        }
        return stringBuilder.toString();
    }
}