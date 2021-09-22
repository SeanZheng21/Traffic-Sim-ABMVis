package MatsimParser;

import AbmModel.MandatorySimModel;
import AbmParser.MandatoryParser;
import AbmParser.ParserConfigurer;
import AbmParser.ParserException;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

/**
 * Mandatory Parser implementation to parse MatSim mandatory models
 */
public class MSMandatoryParser extends MandatoryParser {

    /**
     * Creates a mandatory Matsim parser, called by the parser configurer
     * @param inputFile The implementation model class'es name
     * @param modelName The implementation model class'es name
     * @param path The path to look in the input file for instances
     * @param superModeName The abstract model class'es name
     * @param types Set of attribute names that can be considered as type
     */
    public MSMandatoryParser(String inputFile, String modelName, String path, String superModeName, Set<String> types) {
        super(inputFile, modelName, path, superModeName, types);
    }

    /**
     * Creates an optional Matsim parser, called by the parser configurer
     * default constructor for the newInstance method of the class
     */
    public MSMandatoryParser() {
        super("", "", "", "", new HashSet<>());
    }


    /**
     * Method that launches a configured matsim mandatory parser's parsing process
     * to produce mandatory model data, called by the parser configurer
     * @return the set of instances found under the input path
     */
    @Override
    public Set<MandatorySimModel> parse() throws ParserException {
        Set<MandatorySimModel> res = new HashSet<>();

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc;
            String fileName = getInputFile();
            if (!fileName.startsWith("jar:")) {
                File inputFile = new File(fileName);
                doc = dBuilder.parse(inputFile);
            } else {
                String s = fileName.substring(4);
                System.out.println(s);
                InputStream is = ParserConfigurer.class.getClassLoader().getResourceAsStream(s);
                doc = dBuilder.parse(is);
            }
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName(getPath());

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
//                System.out.println("\nCurrent Element :" + nNode.getNodeName());
                MandatorySimModel model = (MandatorySimModel) Class.forName(getModelName()).newInstance();
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    NamedNodeMap attributes = eElement.getAttributes();
                    for (int i = 0; i < attributes.getLength(); i++) {
                        Node attr = attributes.item(i);
//                        System.out.println(attr.getNodeName() + " = \"" + attr.getNodeValue() + "\"\n");
                        try {
                            Field field = Class.forName(getModelName()).getDeclaredField(attr.getNodeName());
                            assignField(model, field, attr);
                        } catch (Exception ex) {
                            // Unknown field
                        }
                        try {
                            Field field = Class.forName(getModelName()).getSuperclass().getDeclaredField(attr.getNodeName());
                            assignField(model, field, attr);
                        } catch (Exception ex) {
                            // Unknown field
                        }
                        if(getTypes().contains(attr.getNodeName())) {
                            assignType(model, attr);
                        }
                    }
                }
                res.add(model);
            }
        } catch (ParserConfigurationException | IOException | SAXException | ClassNotFoundException | InstantiationException | IllegalAccessException  e) {
            e.printStackTrace();
            throw new ParserException(e.getMessage());
        }

        return res;
    }

    /**
     * Assignment method that assigns the value in node attr to model's field field.
     * @param model  the model that gets assigned
     * @param field field to assign value
     * @param attr the XML attribute node
     * @throws IllegalAccessException if the field is not found in model class or its super class
     */
    private void assignField(MandatorySimModel model, Field field, Node attr) throws IllegalAccessException {
        field.setAccessible(true);
        switch (field.getType().getName()) {
            case "int":
                field.set(model, Integer.parseInt(attr.getNodeValue()));
                break;
            case "double":
                field.set(model, Double.parseDouble(attr.getNodeValue()));
                break;
            case "float":
                field.set(model, Float.parseFloat(attr.getNodeValue()));
                break;
            case "long":
                field.set(model, Long.parseLong(attr.getNodeValue()));
                break;
            case "boolean":
                field.set(model, Boolean.parseBoolean(attr.getNodeValue()));
                break;
            case "char":
                field.set(model, attr.getNodeValue().charAt(0));
                break;
            default:
                field.set(model, field.getType().cast(attr.getNodeValue()));
                break;
        }
    }

    /**
     * Assignment method that assigns the type and its value to the Type object
     * of the SimModel model and to the global TypeCollector single instance
     * @param model the model that gets assigned
     * @param attr the XML attribute node
     */
    private void assignType(MandatorySimModel model, Node attr) {
        model.addTypeEntry(attr.getNodeName(), attr.getNodeValue());
    }

}
