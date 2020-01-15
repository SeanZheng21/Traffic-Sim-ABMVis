package MatsimParser;

import AbmModel.MandatorySimModel;
import AbmParser.MandatoryParser;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

public class MSMandatoryParser extends MandatoryParser {
    public MSMandatoryParser(String inputFile, String modelName, String path, String superModeName) {
        super(inputFile, modelName, path, superModeName);
    }

    // Constructor for the newInstance
    public MSMandatoryParser() {
        super("", "", "", "");
    }

    @Override
    public Set<MandatorySimModel> parse() {
        Set<MandatorySimModel> res = new HashSet<>();

        File inputFile = new File(getInputFile());
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
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
                            field.setAccessible(true);
                            if (field.getType().equals(Integer.class))
                                field.set(model, Integer.parseInt(attr.getNodeValue()));
                            else if (field.getType().equals(Double.class))
                                field.set(model, Double.parseDouble(attr.getNodeValue()));
                            else
                                field.set(model, field.getType().cast(attr.getNodeValue()));
                        } catch (Exception ex) {
                            // Unknown field
                        }
                        try {
                            Field field = Class.forName(getModelName()).getSuperclass().getDeclaredField(attr.getNodeName());
                            field.setAccessible(true);
                            Class o = field.getType();
                            String b = o.getName();
                            if (field.getType().getName().equals("int"))
                                field.set(model, Integer.parseInt(attr.getNodeValue()));
                            else if (field.getType().getName().equals("double"))
                                field.set(model, Double.parseDouble(attr.getNodeValue()));
                            else
                                field.set(model, field.getType().cast(attr.getNodeValue()));
                        } catch (Exception ex) {
                            // Unknown field
                        }
                    }
                }
                res.add(model);
            }
        } catch (ParserConfigurationException | IOException | SAXException | ClassNotFoundException | InstantiationException | IllegalAccessException  e) {
            e.printStackTrace();
        }

        return res;
    }

    //    Set<MandatorySimModel> parse() {
//        System.out.println("Parsing input: " + getInputFile() + " model: " + getModelName() + ": " + getSuperModeName()
//                + " XPath:" + getPath());
//        return new HashSet<MandatorySimModel>();
//    }
}
