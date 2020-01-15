package MainGUI;
import AbmModel.Scenario;
import AbmParser.ParserConfigurer;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application{

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {

        Scenario scenario = startParser("./input/ParserConfig.json");

        Text text = new Text();
        text.setFont(new Font(8));
        text.setX(10);
        text.setY(10);
        text.setText(scenario.toString());
        System.out.println(text.getText());

        Group group = new Group();
        Scene scene = new Scene(group ,800, 600);
        scene.setFill(Color.WHITE);
        ObservableList list = group.getChildren();
        list.add(text);
        primaryStage.setTitle("ABMVis3000");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Scenario startParser(String configJsonPath) {
        ParserConfigurer parserConfigurer =  new ParserConfigurer(configJsonPath);
        return parserConfigurer.parse();
    }
}
