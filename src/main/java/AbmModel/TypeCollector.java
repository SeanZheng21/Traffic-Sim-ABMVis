package AbmModel;

import com.google.common.annotations.VisibleForTesting;

import java.util.*;

/**
 * Abstract TypeCollector concept gathering and sorting all the SimModel objects by types
 * Optional concept
 */
public abstract class TypeCollector {

    /**
     * Main static collector attribute sorting all the SimModel objects
     * by classes, by types and by values as follow Map(Class, Map(Type, Map(Value, Instance)))
     */
    private static Map<Class<? extends SimModel>, Map<String, Map<String, Set<SimModel>>>> COLLECTOR = new HashMap<>();

    /**
     * To purge the collector, should only be used for test purposes
     */
    @VisibleForTesting
    public static void clear() {
        COLLECTOR = new HashMap<>();
    }

    /**
     * Get the set of all SimModel classes handle by the collector
     * @return The set of classes, empty set if the collector is empty
     */
    public static Set<Class<? extends SimModel>> getClasses() {
        return COLLECTOR.keySet();
    }

    /**
     * Get the set of all possible types related to the SimModel instances of class cls
     * @param cls The SimModel class
     * @return The set of types, empty set if the class isn't handled
     */
    public static Set<String> getTypes(Class<? extends SimModel> cls) {
        if(COLLECTOR.containsKey(cls)) {
            return COLLECTOR.get(cls).keySet();
        } else {
            return new HashSet<>();
        }
    }

    /**
     * Get the set of all possible values assigned to specified type for the SimModel instances of class cls
     * @param cls The SimModel class
     * @param type The specified type
     * @return The set of values related to the type, empty set if the class or the type isn't handled
     */
    public static Set<String> getValues(Class<? extends SimModel> cls, String type) {
        if(COLLECTOR.containsKey(cls) && COLLECTOR.get(cls).containsKey(type)) {
            return COLLECTOR.get(cls).get(type).keySet();
        } else {
            return new HashSet<>();
        }
    }

    /**
     * Get the set of all instances of SimModel class cls that has the specified value assigned to the specified type
     * @param cls The SimModel class
     * @param type The specified type
     * @param value The specified value assigned to the type
     * @return The set of all SimModel instances concerned, empty set if the class or the type isn't handled
     * or if the value is never assigned
     */
    public static Set<SimModel> getInstances(Class<? extends SimModel> cls, String type, String value) {
        if(COLLECTOR.containsKey(cls) && COLLECTOR.get(cls).containsKey(type) && COLLECTOR.get(cls).get(type).containsKey(value)) {
            return COLLECTOR.get(cls).get(type).get(value);
        } else {
            return new HashSet<>();
        }
    }

    /**
     * Add a new class to the collector
     * If the class is already handled, nothing happen
     * @param cls The SimModel class to add
     */
    public static void addClass(Class<? extends SimModel> cls) {
        if(!COLLECTOR.containsKey(cls)) {
            COLLECTOR.put(cls, new HashMap<>());
        }
    }

    /**
     * Add a new type to the collector for the SimModel class cls
     * If the type is already handled for the class, nothing happen
     * @param cls The SimModel class to add
     * @param type string type to add
     */
    public static void addType(Class<? extends SimModel> cls, String type) {
        addClass(cls);
        if(!COLLECTOR.get(cls).containsKey(type)) {
            COLLECTOR.get(cls).put(type, new HashMap<>());
        }
    }

    /**
     * Add a new value to the type for the SimModel class cls in the collector
     * If the value is already handled for the type with the class, nothing happen
     * @param cls The SimModel class to add
     * @param type string type concerned
     * @param value string value to add, assigned to the type
     */
    public static void addValue(Class<? extends SimModel> cls, String type, String value) {
        addType(cls, type);
        if(!COLLECTOR.get(cls).get(type).containsKey(value)) {
            COLLECTOR.get(cls).get(type).put(value, new HashSet<>());
        }
    }

    /**
     * Add a SimModel class 'cls' instance 'instance' to have the value 'value' assigned to the type 'type'
     * @param cls The SimModel class to add
     * @param type The string type
     * @param value The string value assigned to the type
     * @param instance The instance that has type assigned to value
     */
    public static void addEntry(Class<? extends SimModel> cls, String type, String value, SimModel instance) {
        addValue(cls, type, value);
        if(!(instance.getClass().equals(cls))) {
            throw new IllegalArgumentException("The instance class has to be the same as cls");
        }
        COLLECTOR.get(cls).get(type).get(value).add(instance);
    }

    /**
     * Add a SimModel class 'cls' instances 'instance' to have the value 'value' assigned to the type 'type'
     * @param cls The SimModel class to add
     * @param type The string type
     * @param value The string value assigned to the type
     * @param instances The instances that have type assigned to value
     */
    public static void addEntries(Class<? extends SimModel> cls, String type, String value, Set<SimModel> instances) {
        for(SimModel instance : instances) {
            addEntry(cls, type, value, instance);
        }
    }

    /**
     * Remove the SimModel class from the collector if present
     * All the related values will types will be removed from the collector as well
     * @param cls The SimModel class to remove
     */
    public static void removeClass(Class<? extends SimModel> cls) {
        COLLECTOR.remove(cls);
    }

    /**
     * Remove the type for the SimModel class if present
     * All the related values will be removed from the collector as well
     * @param cls The SimModel class involved
     * @param type The string type to delete
     */
    public static void removeType(Class<? extends SimModel> cls, String type) {
        if(COLLECTOR.containsKey(cls)) {
            COLLECTOR.get(cls).remove(type);
        }
    }

    /**
     * Remove the value assigned to the type for the SimModel class if present
     * All the related instances will be removed from the collector as well
     * @param cls The SimModel class involved
     * @param type The string type concerned
     * @param value The value to remove
     */
    public static void removeValue(Class<? extends SimModel> cls, String type, String value) {
        if(COLLECTOR.containsKey(cls) && COLLECTOR.get(cls).containsKey(type)) {
            COLLECTOR.get(cls).get(type).remove(value);
        }
    }

    /**
     * Remove a SimModel class 'cls' instance 'instance' from having the value 'value' assigned to the type 'type'
     * @param cls The SimModel class
     * @param type The string type
     * @param value The value assigned to the type
     * @param instance The instance involved
     */
    public static void removeEntry(Class<? extends SimModel> cls, String type, String value, SimModel instance) {
        if(COLLECTOR.containsKey(cls) && COLLECTOR.get(cls).containsKey(type) && COLLECTOR.get(cls).get(type).containsKey(value)) {
            COLLECTOR.get(cls).get(type).get(value).remove(instance);
        }
    }
}