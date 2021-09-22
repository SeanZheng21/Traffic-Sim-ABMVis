package AbmModel;

import java.util.*;

/**
 * Abstract Person concept that represents an acceptable person for Abm
 * A person is an agent
 * Mandatory concept
 */
public abstract class Person implements MandatorySimModel {

    /**
     * the unique id of a person
     */
    protected int id;

    /**
     * the sex of the person
     * <ul>
     *     <li> "m" : male </li>
     *     <li> "f" : female </li>
     * </ul>
     */
    protected String sex;

    /**
     * the age of the person
     */
    protected int age;

    /**
     * define if the person has a driver license or not
     */
    protected String license;

    /**
     * define how often the person has a car available
     */
    protected String car_avail;

    /**
     * define the occupation of the person
     */
    protected String job;

    /**
     * related events of the Person
     */
    protected List<Event> relatedEvents;

    /**
     * all the types of the person
     */
    protected final Type types;

    /**
     * constructor
     * @param id id of the person
     * @param sex sex of the person
     * @param age age of the person
     * @param license driver licenses of the person
     * @param car_avail how often the person has a car available
     * @param job job of the person
     * @param relatedEvents the related event of the person
     * @param types the types of the person
     */
    public Person(int id, String sex, int age, String license, String car_avail, String job, List<Event> relatedEvents, Type types) {
        this.id = id;
        this.sex = sex;
        this.age = age;
        this.license = license;
        this.car_avail = car_avail;
        this.job = job;
        this.relatedEvents = relatedEvents;
        this.types = types;
    }

    /**
     * Get the nearest PersonEntersVehicle event from the specified timestamp
     * @param time The timestamp
     * @return The nearest event, null if there isn't any event after the timestamp
     */
    public PersonEntersVehicle getNearestVehicleEntering(double time) {
        PersonEntersVehicle nearestEvent = null;
        double delta = Integer.MAX_VALUE;
        for(Event e : relatedEvents) {
            if(e.isValid() && e instanceof PersonEntersVehicle) {
                if(0 <= (e.getTime() - time) && (e.getTime() - time) < delta) {
                    nearestEvent = (PersonEntersVehicle) e;
                    delta = e.getTime() - time;
                }
            }
        }
        return nearestEvent;
    }

    /**
     * Get the latest PersonLeavesVehicle event from the specified timestamp
     * @param time The timestamp
     * @return The latest event, null if there isn't any event prior to the timestamp
     */
    public PersonLeavesVehicle getLatestVehicleLeaving(double time) {
        PersonLeavesVehicle latestEvent = null;
        double delta = Integer.MAX_VALUE;
        for(Event e : relatedEvents) {
            if(e.isValid() && e instanceof PersonLeavesVehicle) {
                if(0 <= (time - e.getTime()) && (time - e.getTime()) < delta) {
                    latestEvent = (PersonLeavesVehicle) e;
                    delta = time - e.getTime();
                }
            }
        }
        return latestEvent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getCar_avail() {
        return car_avail;
    }

    public void setCar_avail(String car_avail) {
        this.car_avail = car_avail;
    }

    public String getJob() {
        return job;
    }

    public Type getTypes() {
        return types;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public List<Event> getRelatedEvents() {
        return relatedEvents;
    }

    public void setRelatedEvents(List<Event> relatedEvents) {
        this.relatedEvents = relatedEvents;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                ", license='" + license + '\'' +
                ", car_avail='" + car_avail + '\'' +
                ", job='" + job + '\'' +
                ", types=" + types +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id == person.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * function to add an event
     * @param event th event to add
     */
    public  void appendRelatedEvents(Event event) {
        relatedEvents.add(event);
    }

    @Override
    public void addTypeEntry(String type, String value) {
        types.addEntry(type, value);
    }
}