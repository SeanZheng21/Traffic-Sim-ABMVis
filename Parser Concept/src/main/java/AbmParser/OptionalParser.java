package AbmParser;

import AbmModel.OptionalSimModel;

import java.util.Set;

public abstract class OptionalParser extends Parser {
    public OptionalParser(String inputFile, String modelName, String path, String superModeName) {
        super(inputFile, modelName, path, superModeName);
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
    public abstract Set<OptionalSimModel> parse();

}

