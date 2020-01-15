package AbmModel;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class Scenario {
    private String name;
    private String unit;
    private Map<Class, Set<MandatorySimModel>> mandatorySimModels;
    private Map<Class, Set<OptionalSimModel>> optionalSimModels;

    public Scenario(String name, String unit) {
        this.name = name;
        this.unit = unit;
        mandatorySimModels = new HashMap<>();
        optionalSimModels = new HashMap<>();
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

    public Map<Class, Set<MandatorySimModel>> getMandatorySimModels() {
        return mandatorySimModels;
    }

    public Map<Class, Set<OptionalSimModel>> getOptionalSimModels() {
        return optionalSimModels;
    }

    public void addMandatoryModels(Class cls, Set<MandatorySimModel> mandatorySimModels) {
        if (this.mandatorySimModels.containsKey(cls)) {
            this.mandatorySimModels.get(cls).addAll(mandatorySimModels);
        } else {
            this.mandatorySimModels.put(cls, mandatorySimModels);
        }
    }

    public void addOptionalModels(Class cls, Set<OptionalSimModel> optionalSimModels) {
        if (this.optionalSimModels.containsKey(cls)) {
            this.optionalSimModels.get(cls).addAll(optionalSimModels);
        } else {
            this.optionalSimModels.put(cls, optionalSimModels);
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Scenario:\n");
        stringBuilder.append("\tname: ").append(name).append("\n");
        stringBuilder.append("\tunit: ").append(unit).append("\n");
        stringBuilder.append("\tmandatory models:\n");
        for (Class cls: mandatorySimModels.keySet()) {
            stringBuilder.append("\t\t").append(cls.getName()).append(": \n");
            Set<MandatorySimModel> set = mandatorySimModels.get(cls);
            for(MandatorySimModel elt: set)
                stringBuilder.append("\t\t\t").append(elt.toString()).append("\n");
        }
        stringBuilder.append("\toptional models:\n");
        for (Class cls: optionalSimModels.keySet()) {
            stringBuilder.append("\t\t").append(cls.getName()).append(": \n");
            Set<OptionalSimModel> set = optionalSimModels.get(cls);
            for(OptionalSimModel elt: set)
                stringBuilder.append("\t\t\t").append(elt.toString()).append("\n");
        }
        return stringBuilder.toString();
    }
}
