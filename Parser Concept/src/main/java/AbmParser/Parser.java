package AbmParser;

import AbmModel.SimModel;
import java.util.Set;

public abstract class Parser {
    private String inputFile, modelName, path, superModeName;

    Parser(String inputName, String modelName, String path, String superModeName) {
        this.inputFile = inputName;
        this.modelName = modelName;
        this.path = path;
        this.superModeName = superModeName;
    }

    Class getModelClass() {
        try {
            return Class.forName(getModelName());
        } catch (ClassNotFoundException e) {
            System.err.println("Unknown model class name: " + getModelName());
            e.printStackTrace();
            return null;
        }
    }

    abstract Set<? extends SimModel> parse();

    public String getInputFile() {
        return inputFile;
    }

    public void setInputFile(String inputFile) {
        this.inputFile = inputFile;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getSuperModeName() {
        return superModeName;
    }

    public void setSuperModeName(String superModeName) {
        this.superModeName = superModeName;
    }

}
