package AbmParser;

import java.io.*;
import java.util.Set;

import AbmModel.MandatorySimModel;
import AbmModel.OptionalSimModel;
import AbmModel.Scenario;
import org.json.JSONArray;
import org.json.JSONObject;

public final class ParserConfigurer {
    private JSONObject jsonObject;

    public ParserConfigurer(String configJsonPath) {
        String jsonString = createStringFromFile(configJsonPath);
        jsonObject = new JSONObject(jsonString);
    }

    public Scenario parse() {
        String scenarioName = jsonObject.getString("ScenarioName");
        String scenarioUnit = jsonObject.getString("Unit");
        String scenarioPath = jsonObject.getString("InputPath");
        Scenario scenario = new Scenario(scenarioName, scenarioUnit);

        String packageName = jsonObject.getString("PackageName");
        String parserPackage = jsonObject.getString("ParserPackage");

        JSONArray arr = jsonObject.getJSONArray("MandatoryModules");
        for (int i = 0; i < arr.length(); i++) {
            JSONObject theObject = arr.getJSONObject(i);
            String modelName = theObject.getString("ModelName");
            String superModelName = theObject.getString("SuperModelName");
            String path = theObject.getString("Path");
            String inputName = theObject.getString("InputFile");
            String parserName = theObject.getString("ParserName");
            try {
                MandatoryParser mandatoryParser =
                        (MandatoryParser) Class.forName(parserPackage + "." + parserName).newInstance();
                mandatoryParser.setInputFile(scenarioPath + "/" + inputName);
                mandatoryParser.setModelName(packageName + "." + modelName);
                mandatoryParser.setPath(path);
                mandatoryParser.setSuperModeName(superModelName);
                Class c = mandatoryParser.getModelClass();
                Set<MandatorySimModel> s = mandatoryParser.parse();
                scenario.addMandatoryModels(mandatoryParser.getModelClass(), mandatoryParser.parse());
            } catch (ClassNotFoundException e) {
                System.err.println("Unknown model class name: " + parserPackage + "." + parserName);
                e.printStackTrace();
            } catch (IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        }

        arr = jsonObject.getJSONArray("OptionalModules");
        for (int i = 0; i < arr.length(); i++) {
            JSONObject jsonObject = arr.getJSONObject(i);
            String modelName = jsonObject.getString("ModelName");
            String superModelName = jsonObject.getString("SuperModelName");
            String path = jsonObject.getString("Path");
            String inputName = jsonObject.getString("InputFile");
            String parserName = jsonObject.getString("ParserName");
            try {
                OptionalParser optionalParser =
                        (OptionalParser) Class.forName(parserPackage + "." + parserName).newInstance();
                optionalParser.setInputFile(scenarioPath + "/" + inputName);
                optionalParser.setModelName(packageName + "." + modelName);
                optionalParser.setPath(path);
                optionalParser.setSuperModeName(superModelName);
                Class c = optionalParser.getModelClass();
                Set<OptionalSimModel> s = optionalParser.parse();
                scenario.addOptionalModels(optionalParser.getModelClass(), optionalParser.parse());
            } catch (ClassNotFoundException e) {
                System.err.println("Unknown model class name: " + parserPackage + "." + parserName);
                e.printStackTrace();
            } catch (IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        }
        return scenario;
    }

    private String createStringFromFile(String configJsonPath) {
        InputStream is = null;
        try {
            is = new FileInputStream(configJsonPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.println("Unable to open file: " + configJsonPath);
            return "";
        }
        BufferedReader buf = new BufferedReader(new InputStreamReader(is));
        String line = null;
        StringBuilder sb = new StringBuilder();
        try {
            line = buf.readLine();
            while(line != null){
                sb.append(line).append("\n");
                line = buf.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
