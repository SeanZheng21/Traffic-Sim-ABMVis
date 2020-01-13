package parser;

import model.SimModel;

import java.util.Set;

public abstract class Parser {
    private String inputName, modelName, xPath, superModeName;

    Parser(String inputName, String modelName, String xPath, String superModeName) {
        this.inputName = inputName;
        this.modelName = modelName;
        this.xPath = xPath;
        this.superModeName = superModeName;
    }

    abstract Class getModelClass();

    abstract Set<? extends SimModel> parse();

    public String getInputName() {
        return inputName;
    }

    public void setInputName(String inputName) {
        this.inputName = inputName;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getxPath() {
        return xPath;
    }

    public void setxPath(String xPath) {
        this.xPath = xPath;
    }

    public String getSuperModeName() {
        return superModeName;
    }

    public void setSuperModeName(String superModeName) {
        this.superModeName = superModeName;
    }

}
