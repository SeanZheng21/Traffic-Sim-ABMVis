package AbmParser;

import AbmModel.MandatorySimModel;
import java.util.Set;

public abstract class MandatoryParser extends Parser {

    public MandatoryParser(String inputFile, String modelName, String path, String superModeName) {
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
    public abstract Set<MandatorySimModel> parse();

}
