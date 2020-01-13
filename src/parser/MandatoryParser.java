package parser;

import model.MandatorySimModel;

import java.util.HashSet;
import java.util.Set;

public final class MandatoryParser extends Parser {

    public MandatoryParser(String inputName, String modelName, String xPath, String superModeName) {
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
    Set<MandatorySimModel> parse() {
        System.out.println("Parsing input: " + getInputName() + " model: " + getModelName() + ": " + getSuperModeName()
                + " XPath:" + getxPath());
        return new HashSet<MandatorySimModel>();
    }
}
