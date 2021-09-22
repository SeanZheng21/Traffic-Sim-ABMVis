package AbmModel;

import java.util.*;

/**
 * This class allows to get and types for the model Elements for exemple Vehicle, Links...
 */
public abstract class Type {

    /**
     * Instance which have this types
     */
    protected SimModel instance;

    /**
     * All the types of the instance
     */
    protected final Map<String, Set<String>> types;

    /**
     * Constructor
     * @param instance the instance related to this type Object
     */
    public Type(SimModel instance) {
        this.instance = instance;
        this.types = new HashMap<>();
    }

    /**
     * Constructor
     */
    public Type() {
        this.instance = null;
        this.types = new HashMap<>();
    }

    public void setInstance(SimModel instance) {
        this.instance = instance;
    }

    /**
     * get all the types handled by the instance
     * @return set of types
     */
    public Set<String> getTypes() {
        return types.keySet();
    }

    /**
     * get all values assigned to the specified type for this instance
     * @param type string type
     * @return set of values
     */
    public Set<String> getValues(String type) {
        if(isPresent(type)) {
            return types.get(type);
        } else {
            return new HashSet<>();
        }
    }

    /**
     * add a type category to the map
     * @param type string type
     */
    public void addType(String type) {
        if(!isPresent(type)) {
            this.types.put(type, new HashSet<>());
        }
    }

    /**
     * allows us to add a type value
     * @param type String type
     * @param value String value (it can be several values separated by comma in one String)
     */
    public void addEntry(String type, String value){
        addType(type);
        if(value.matches(".*,.*")) {
            value = value.replaceAll("\\s+", "");
            HashSet<String> values = new HashSet<>(Arrays.asList(value.split(",")));
            addEntry(type, values);
        } else {
            this.types.get(type).add(value);
            TypeCollector.addEntry(instance.getClass(), type, value, instance);
        }
    }

    /**
     * allows us to add type values
     * @param type String type
     * @param values Set of values
     */
    public void addEntry(String type, Set<String> values){
        addType(type);
        for(String value : values) {
            this.types.get(type).add(value);
            TypeCollector.addEntry(instance.getClass(), type, value, instance);
        }
    }

    /**
     * to remove a type from the map
     * @param type string type
     */
    public void removeType(String type) {
        if(isPresent(type)) {
            for(String value : types.get(type)) {
                TypeCollector.removeEntry(instance.getClass(), type, value, instance);
                if(TypeCollector.getInstances(instance.getClass(), type, value).isEmpty()) {
                    TypeCollector.removeValue(instance.getClass(), type, value);
                }
            }
            if(TypeCollector.getTypes(instance.getClass()).isEmpty()) {
                TypeCollector.removeClass(instance.getClass());
            } else {
                if(TypeCollector.getValues(instance.getClass(), type).isEmpty()) {
                    TypeCollector.removeType(instance.getClass(), type);
                }
            }
            types.remove(type);
        }
    }

    /**
     * to remove a type value from the map
     * @param type string type
     * @param value String value of this type for this object
     */
    public void removeEntry(String type, String value) {
        if(isPresent(type, value)) {
            types.get(type).remove(value);
            TypeCollector.removeEntry(instance.getClass(), type, value, instance);
            if(TypeCollector.getTypes(instance.getClass()).isEmpty()) {
                TypeCollector.removeClass(instance.getClass());
            } else {
                if(TypeCollector.getValues(instance.getClass(), type).isEmpty()) {
                    TypeCollector.removeType(instance.getClass(), type);
                } else {
                    if(TypeCollector.getInstances(instance.getClass(), type, value).isEmpty()) {
                        TypeCollector.removeValue(instance.getClass(), type, value);
                    }
                }
            }
        }
    }

    /**
     * allows us to know if the type "type" is present for this instance
     * @param type string type
     * @return true if present
     */
    public boolean isPresent(String type){
        return types.containsKey(type);
    }

    /**
     * allows us to know if the value "value" is assigned to the type "type"
     * @param type string type
     * @param value string value of the type
     * @return true if present
     */
    public boolean isPresent(String type, String value){
        if(isPresent(type)) {
            return types.get(type).contains(value);
        } else {
            return false;
        }

    }

    /**
     * allows us to know if all the values "values" are assigned to the type "type"
     * @param type string type
     * @param values string value of the type
     * @return true if present
     */
    public boolean isPresent(String type, Set<String> values){
        for(String value : values) {
            if(!isPresent(type, value)) {
                return false;
            }
        }
        return true;
    }
    
}
