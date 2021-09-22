package MainGUI;

import AbmModel.Scenario;
import AbmParser.ParserConfigurer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;

/**
 * Main class to launch the application
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("views/main.fxml"));
        Parent root = loader.load();
        
        primaryStage.setTitle("ABMVis3000");
        primaryStage.setMaximized(true);
        //primaryStage.setWidth(1000);
        //primaryStage.setHeight(1000);
        
        Scene mainScene = new Scene(root);
        mainScene.getStylesheets().add("styles/main.css");
        //loader.<Controller>getController().setPath("input/guenaelExemple/ParserConfigGueno.json");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    /**
     * command line launched
     * @param args comand line arguments
     */
    public static void main(String []args) {
        launch(args);
    }


    /**
     * Start the parser and create a scenario result from a path
     * @param configJsonPath the path of the parser configuration file
     * @return the result scenario
     */
    public static Scenario startParser(String configJsonPath) {
        // Create a parser from the given configuration path
        ParserConfigurer parserConfigurer =  new ParserConfigurer(configJsonPath, false);
        // Launch the parser and return the parsing result
        return parserConfigurer.parse();
    }
}