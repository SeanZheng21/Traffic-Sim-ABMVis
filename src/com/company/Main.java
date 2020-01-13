package com.company;

import com.sun.tools.javac.util.Pair;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import model.MandatorySimModel;
import model.OptionalSimModel;
import org.javatuples.Pair;
import parser.ParserConfigurer;

import java.util.Map;
import java.util.Set;

public class Main extends Application {

    public static void main(String[] args) {
	// write your code here
//        launch(args);
        ParserConfigurer parserConfigurer =  new ParserConfigurer("./input/ParserConfig.json");
        parserConfigurer.setPackageName("MatSimModel");
        Pair<Map<Class, Set<MandatorySimModel>>, Map<Class, Set<OptionalSimModel>>> pair = parserConfigurer.startParse();
        Map<Class, Set<MandatorySimModel>> mandatorySimModel = pair.getValue0();
        Map<Class, Set<OptionalSimModel>> optionalSimModel = pair.getValue1();
        for (Class c: mandatorySimModel.keySet()) {
            Set<MandatorySimModel> set = mandatorySimModel.get(c);
            set.forEach( elt -> {
                System.out.println(elt.toString());
            });
        }
        for (Class c: optionalSimModel.keySet()) {
            Set<OptionalSimModel> set = optionalSimModel.get(c);
            set.forEach( elt -> {
                System.out.println(elt.toString());
            });
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Group group = new Group();
        Scene scene = new Scene(group ,1280, 768);
        scene.setFill(Color.WHITE);
        primaryStage.setTitle("Sample Application");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
