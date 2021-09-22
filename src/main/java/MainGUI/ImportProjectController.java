package MainGUI;

import AbmParser.ParserConfigurer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * Controller for the Project importation window
 */
public class ImportProjectController {

    /**
     * superController to call some function
     */
    private Controller superController;

    /**
     * This field is the path of the json config file
     */
    @FXML private TextField configFilePath;

    /**
     * TO fill with the jar file path
     */
    @FXML private TextField configJarPath;

    /**
     * To fill with the config template file path
     */
    @FXML private TextField configTemplatePath;

    /**
     * Preview displaying area
     */
    @FXML private TextArea previewTextArea;

    /**
     * Checkbox for custom parser
     */
    @FXML private CheckBox customizedCheckBox;

    /**
     * The scenario details label
     */
    @FXML private Label scenarioDetailLabel;

    /**
     * To display error message if the specified file is not well formatted
     */
    @FXML private Label invalidFileMessage;

    /**
     * to load the project from the specified path
     * @param actionEvent the action event which calls the function
     * @throws IOException if the json File don't exist
     */
    @FXML
    public void load(ActionEvent actionEvent) throws IOException {
        if(configFilePath.getText().endsWith(".json")) {
            invalidFileMessage.setText("");

            superController.setPath(configFilePath.getText());
            superController.setCustomizedParsers(customizedCheckBox.isSelected());
            if (customizedCheckBox.isSelected()) {
                superController.setJarPath(configJarPath.getText());
            }
            superController.loadScenario();

            final Node source = (Node) actionEvent.getSource();
            final Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        } else {
            invalidFileMessage.setText("Le chemin spécifié doit mener vers un fichier de configuration json");
            invalidFileMessage.setTextFill(Color.RED);
        }
    }

    /**
     * to choose the path of the json config file
     * @param actionEvent the action event which calls the function
     */
    @FXML
    public void chooseFilePath(ActionEvent actionEvent) {
        final Node source = (Node) actionEvent.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();

        FileChooser chooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");
        chooser.getExtensionFilters().add(extFilter);
        File file = chooser.showOpenDialog(stage);
        if (file != null) {
            String fileAsString = file.toString();
            configFilePath.setText(fileAsString);
        } else {
            configFilePath.setText("");
        }
        ParserConfigurer __p = new ParserConfigurer(configFilePath.getText(), customizedCheckBox.isSelected());

        previewTextArea.setText(getFileContent(configFilePath.getText()));
        scenarioDetailLabel.setText(__p.getPreviewDescription());
    }

    /**
     * to get the content of the file to be displayed as a preview
     * @param filePath The string path of the file
     * @return The string content for the preview
     */
    private String getFileContent(String filePath) {
        //String res = "";
        StringBuilder res = new StringBuilder();
        try {
            File myObj = new File(filePath);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                res.append(myReader.nextLine());
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return res.toString();
    }

    /**
     * to choose the path of the jar file
     * @param actionEvent the action event which calls the function
     */
    @FXML
    public void chooseJarPath(ActionEvent actionEvent) {
        if (!customizedCheckBox.isSelected())
            return;
        final Node source = (Node) actionEvent.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();

        FileChooser chooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Jar files (*.jar)", "*.jar");
        chooser.getExtensionFilters().add(extFilter);
        File file = chooser.showOpenDialog(stage);
        if (file != null) {
            String fileAsString = file.toString();
            configJarPath.setText(fileAsString);
        } else {
            configJarPath.setText("");
        }
    }

    /**
     * to choose the path of a template
     * @param actionEvent the action event which calls the function
     */
    @FXML
    public void chooseTemplatePath(ActionEvent actionEvent) {
        final Node source = (Node) actionEvent.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();

        DirectoryChooser chooser = new DirectoryChooser();
        File selectedDirectory = chooser.showDialog(stage);
        if (selectedDirectory != null) {
            String fileAsString = selectedDirectory.toString();
            ParserConfigurer.generateTemplateConfig(fileAsString);
            configTemplatePath.setText(fileAsString);
        } else {
            configTemplatePath.setText("");
        }
    }

    /**
     * setter for the superController
     * @param superController the super Controller to call functions
     */
    public void setSuperController(Controller superController) {
        this.superController = superController;
    }
}
