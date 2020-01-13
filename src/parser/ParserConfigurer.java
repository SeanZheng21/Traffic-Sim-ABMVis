package parser;

import model.MandatorySimModel;
import model.OptionalSimModel;
import model.SimModel;
import org.javatuples.Pair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.*;


/**
 * This class takes JSON configuration files to configure parsers
 */
public final class ParserConfigurer {
    private String jsonString;
    private String packageName;
    private List<MandatoryParser> mandatoryParsers;
    private List<OptionalParser> optionalParsers;

    public ParserConfigurer(String configJsonPath) {
        // Initialize lists from JSON property list
        mandatoryParsers = new ArrayList<>();
        optionalParsers = new ArrayList<>();
        System.out.println(configJsonPath);
        String jsonString = createStringFromFile(configJsonPath);
        this.jsonString = jsonString;
        JSONObject obj = new JSONObject(jsonString);
        packageName = obj.getString("PackageName");

        JSONArray arr = obj.getJSONArray("MandatoryModules");
        for (int i = 0; i < arr.length(); i++) {
            String modelName = arr.getJSONObject(i).getString("ModelName");
            String superModelName = arr.getJSONObject(i).getString("SuperModelName");
            String xPath = arr.getJSONObject(i).getString("XPath");
            String inputName = arr.getJSONObject(i).getString("InputName");
            MandatoryParser mandatoryParser = new MandatoryParser(inputName, modelName, xPath, superModelName);
            mandatoryParsers.add(mandatoryParser);
        }
        arr = obj.getJSONArray("OptionalModules");
        for (int i = 0; i < arr.length(); i++) {
            String modelName = arr.getJSONObject(i).getString("ModelName");
            String superModelName = arr.getJSONObject(i).getString("SuperModelName");
            String xPath = arr.getJSONObject(i).getString("XPath");
            String inputName = arr.getJSONObject(i).getString("InputName");
            OptionalParser optionalParser = new OptionalParser(inputName, modelName, xPath, superModelName);
            optionalParsers.add(optionalParser);
        }
        System.out.println("End of parser configuration");
    }

    public void test() {
        System.out.println(jsonString);
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

    public Pair<Map<Class, Set<MandatorySimModel>>, Map<Class, Set<OptionalSimModel>>> startParse() {
        Map<Class, Set<MandatorySimModel>> mandatoryObjects = new HashMap<>();
        Map<Class, Set<OptionalSimModel>> optionalObjects = new HashMap<>();
        for (MandatoryParser mp: mandatoryParsers) {
            mandatoryObjects.put(mp.getModelClass(), mp.parse());
        }
        for (OptionalParser op: optionalParsers) {
            optionalObjects.put(op.getModelClass(), op.parse());
        }
        return Pair.with(mandatoryObjects, optionalObjects);
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
}
