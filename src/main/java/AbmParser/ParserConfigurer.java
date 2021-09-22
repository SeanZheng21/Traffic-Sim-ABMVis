package AbmParser;

import java.io.*;
import java.net.*;
import java.util.HashSet;
import java.util.Set;

import AbmModel.MandatorySimModel;
import AbmModel.OptionalSimModel;
import AbmModel.Scenario;
import org.json.JSONArray;
import org.json.JSONObject;


/**
 * Configurer that analysis the configuration file and produces scenarios
 * 1. collects information from the config file
 * 2. instantiates the parsers and scenario processor
 * 3. construct the scenario
 * 4. post processing with scenario processor
 * A user should  instantiate a parser confugurer when the GUI needs scenario from input
 */
public final class ParserConfigurer {
    /**
     * Json object for internal storage
     * It's the raw json object converted from the config file
     */
    private final JSONObject jsonObject;
    /**
     * If the parser configurer uses a customized parser or scenario processor
     */
    private final Boolean customizedParsers;
    /**
     * The path to the impl jar file. Empty if customizedParsers is false
     */
    private String jarPath;
    /**
     * Creates a parser configurer that works on a scenario at configJsonPath
     * @param configJsonPath the path of the scenario configuration file
     * @param customizedParsers if the parser configurer uses customized parser implementations
     */
    public ParserConfigurer(String configJsonPath, Boolean customizedParsers) {
        String jsonString = createStringFromFile(configJsonPath);
        jsonObject = new JSONObject(jsonString);
        this.customizedParsers = customizedParsers;
        jarPath = "";
    }


    /**
     * Generate a description for preview the config file
     * Return not valid if it doesn't have basic info
     * Return detail info if there's any
     * @return description
     */
    public String getPreviewDescription() {
        if (jsonObject.getString("ScenarioName").isEmpty() ||
                jsonObject.getString("InputPath").isEmpty()) {
            return "Not a valid parser configuration file";
        } else {
            return "Scenario found: " + jsonObject.getString("ScenarioName") + " at " +
                    jsonObject.getString("InputPath") + ", found mandatory modules: " +
                    jsonObject.getJSONArray("MandatoryModules").length() + ", found optional modules: " +
                    jsonObject.getJSONArray("OptionalModules").length();
        }
    }

    /**
     * Generate a template configuration file under the path
     * Template file like this:
     * {
     * 	"ScenarioName": "EquilExtended",
     * 	"InputPath": "./input",
     * 	"PackageName": "MatsimModel",
     * 	"Unit": "Metric",
     * 	"ParserPackage": "MatsimParser",
     * 	"ScenarioProcessor" : "MSScenarioProcessor",
     * 	"_comment" : "mandatory module goes here...",
     * 	"MandatoryModules" : [ ],
     * 	"_comment" : "optional module goes here...",
     * 	"OptionalModules" : [ ]
     * }
     * @param path the path of the generated file
     * @return if correctly generated
     */
    public static boolean generateTemplateConfig(String path) {
        String content = "{\n" +
                "\t\"ScenarioName\": \"EquilExtended\",\n" +
                "\t\"InputPath\": \"./input\",\n" +
                "\t\"PackageName\": \"MatsimModel\",\n" +
                "\t\"Unit\": \"Metric\",\n" +
                "\t\"ParserPackage\": \"MatsimParser\",\n" +
                "\t\"ScenarioProcessor\" : \"MSScenarioProcessor\",\n" +
                "\t\"_comment\" : \"mandatory module goes here...\",\n" +
                "\t\"MandatoryModules\" : [ ],\n" +
                "\t\"_comment\" : \"optional module goes here...\",\n" +
                "\t\"OptionalModules\" : [ ]\n" +
                "}\n";
        try {
            FileWriter myWriter = new FileWriter(path + "/template.xml");
            myWriter.write(content);
            myWriter.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Creates a scenario from the json object and launch the parsers
     * It  produces scenarios in the following order
     * 1. collects information from the config file
     * 2. instantiates the parsers and scenario processor
     * 3. construct the scenario
     * 4. post processing with scenario processor
     * @return the result scenario
     */
    @SuppressWarnings("unchecked")
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
            Set<String> types = new HashSet<>();
            JSONArray typesArr = theObject.getJSONArray("Types");
            for(int j = 0; j < typesArr.length(); j++) {
                types.add((String) typesArr.get(j));
            }
            try {
                MandatoryParser mandatoryParser;
                if (customizedParsers) {
                    if (jarPath.equals("")) {
                        throw new ParserException("Unspecified jar path");
                    } else {
                        ParserLoader loader = new ParserLoader(this, LoaderTarget.MANDATORY_PARSER,
                                jarPath, parserPackage + "." + parserName);
                         mandatoryParser = loader.loadMandatoryParser();
                    }
                } else {
                    mandatoryParser =
                            (MandatoryParser) Class.forName(parserPackage + "." + parserName).newInstance();
                }
                mandatoryParser.setInputFile(scenarioPath + "/" + inputName);
                mandatoryParser.setModelName(packageName + "." + modelName);
                mandatoryParser.setPath(path);
                mandatoryParser.setSuperModeName(superModelName);
                mandatoryParser.setTypes(types);
                scenario.addMandatoryModels((Class<? extends MandatorySimModel>) mandatoryParser.getModelClass(), mandatoryParser.parse());
            } catch (ClassNotFoundException e) {
                System.err.println("Unknown model class name: " + parserPackage + "." + parserName);
                e.printStackTrace();
            } catch (IllegalAccessException | InstantiationException | ParserException | ParserConfigurerException e) {
                e.printStackTrace();
            }
        }

        arr = jsonObject.getJSONArray("OptionalModules");
        for (int i = 0; i < arr.length(); i++) {
            JSONObject theObject = arr.getJSONObject(i);
            String modelName = theObject.getString("ModelName");
            String superModelName = theObject.getString("SuperModelName");
            String path = theObject.getString("Path");
            String inputName = theObject.getString("InputFile");
            String parserName = theObject.getString("ParserName");
            Set<String> types = new HashSet<>();
            JSONArray typesArr = theObject.getJSONArray("Types");
            for(int j = 0; j < typesArr.length(); j++) {
                types.add((String) typesArr.get(j));
            }
            try {
                OptionalParser optionalParser;
                if (customizedParsers) {
                    if (jarPath.equals("")) {
                        throw new ParserException("Unspecified jar path");
                    } else {
                        ParserLoader loader = new ParserLoader(this, LoaderTarget.OPTIONAL_PARSER,
                                jarPath, parserPackage + "." + parserName);
                        optionalParser = loader.loadOptionalParser();
                    }
                } else {
                    optionalParser =
                            (OptionalParser) Class.forName(parserPackage + "." + parserName).newInstance();
                }
                optionalParser.setInputFile(scenarioPath + "/" + inputName);
                optionalParser.setModelName(packageName + "." + modelName);
                optionalParser.setPath(path);
                optionalParser.setSuperModeName(superModelName);
                optionalParser.setTypes(types);
                scenario.addOptionalModels((Class<? extends OptionalSimModel>) optionalParser.getModelClass(), optionalParser.parse());
            } catch (ClassNotFoundException e) {
                System.err.println("Unknown model class name: " + parserPackage + "." + parserName);
                e.printStackTrace();
            } catch (IllegalAccessException | InstantiationException | ParserException | ParserConfigurerException e) {
                e.printStackTrace();
            }
        }

        try {
            String parserConfigurerName = jsonObject.getString("ScenarioProcessor");
            ScenarioProcessor parserConfigurer;
            if (customizedParsers) {
                if (jarPath.equals("")) {
                    throw new ParserException("Unspecified jar path");
                } else {
                    ParserLoader loader = new ParserLoader(this, LoaderTarget.SCENARIO_PROCESSOR,
                            jarPath, parserPackage + "." + parserConfigurerName);
                    parserConfigurer = loader.loadScenarioProcessor();
                }
            } else {
                parserConfigurer =
                        (ScenarioProcessor) Class.forName(parserPackage + "." + parserConfigurerName).newInstance();
            }
            scenario = parserConfigurer.process(scenario);
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | ParserException | ParserConfigurerException e) {
            e.printStackTrace();
        }

        return scenario;
    }

    /**
     * Creates the internal json string from the given config file
     * @param configJsonPath the path of the config file
     * @return generated json string
     */
    static String createStringFromFile(String configJsonPath) {
        InputStream is;
        try {
            if (configJsonPath.startsWith("jar:")) {
                String s = configJsonPath.substring(4);

                is = ParserConfigurer.class.getClassLoader().getResourceAsStream(s);
            } else {
                is = new FileInputStream(configJsonPath);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Unable to open file: " + configJsonPath);
            return "";
        }
        BufferedReader buf = new BufferedReader(new InputStreamReader(is));
        String line;
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

    public String getJarPath() {
        return jarPath;
    }

    public void setJarPath(String jarPath) {
        this.jarPath = jarPath;
    }
}
