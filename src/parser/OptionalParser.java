package parser;

import model.OptionalSimModel;

import java.util.HashSet;
import java.util.Set;

public final class OptionalParser extends Parser {
    public OptionalParser(String inputName, String modelName, String xPath, String superModeName) {
        super(inputName, modelName, xPath, superModeName);
    }

    @Override
    Class getModelClass() {
        try {
            return Class.forName(getModelName());
        } catch (ClassNotFoundException e) {
            System.err.println("Unknown model class name: " + getModelName());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Set<OptionalSimModel> parse() {
        System.out.println("Parsing input: " + getInputName() + " model: " + getModelName() + ": " + getSuperModeName()
            + " XPath:" + getxPath());
        return new HashSet<OptionalSimModel>();
    }
}
