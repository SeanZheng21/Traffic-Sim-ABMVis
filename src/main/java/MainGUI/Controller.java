package MainGUI;

import AbmModel.*;
import AbmParser.ParserConfigurer;
import UIAbmModel.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * the main Controller class of the application
 */
public class Controller {

    /**
     * path to the json config file
     */
    String configJsonPath;

    /**
     * jar path for external parser
     */
    String jarPath;

    /**
     * boolean value true if it's an external parser
     */
    boolean customizedParsers;

    /**
     * Scenario to draw
     */
    UIScenario scenario;

    /**
     * the root pane
     */
    @FXML
    private Pane root;

    /**
     * pane to draw on
     */
    @FXML
    private Pane pane;

    /**
     * the drawing scroll pane
     */
    @FXML
    private ScrollPane scrollPane;

    /**
     * the filter view pane controller
     */
    @FXML
    private FilterViewController filterViewController;

    /**
     * the time pane controller
     */
    @FXML
    private TimePaneController timePaneController;

    /**
     * the observer pane controller
     */
    @FXML
    private ObserverController observerController;

    /**
     * map storing all the key shortcuts
     */
    private HashMap<KeyCode, Pair<String,Consumer<KeyCode>>> keyBinding;

    /**
     * Map storing all analysis handled by the application for the visualization
     * It maps analysis menu options string to the corresponding Analysis object
     * from the UIModel needed to call AnalysisController
     */
    private Map<String, Analysis> analyzes;

    /**
     * Menu listing all the possible analyzes from the fxml
     */
    @FXML
    private Menu analyzesMenu;

    /**
     * Initialize FXML function
     */
    @FXML
    public void initialize() {
        this.initializeKeyBinding();
        this.timePaneController.setSuperControlleur(this);
        this.filterViewController.setSuperController(this);

        pane.setBackground(new Background(new BackgroundFill(Color.GRAY, null,null)));
        this.updatePaneSize();

        Platform.runLater(() -> {
            try {
                loadScenario();
            } catch(IOException e) {
                e.printStackTrace();
            }
        });

        root.setOnKeyPressed( ke -> {
            KeyCode keyCode = ke.getCode();
            Controller.this.keyBinding.getOrDefault(keyCode,new Pair<>("Default", Controller.this::defaultFunctionKeyPressed)).getValue().accept(keyCode);
        });
    }

    /**
     * customized parser setter
     * @param customizedParsers the new value for the boolean
     */
    public void setCustomizedParsers(boolean customizedParsers) {
        this.customizedParsers = customizedParsers;
    }

    /**
     * parser jarPath setter
     * @param jarPath the new value for the parser jar path
     */
    public void setJarPath(String jarPath) {
        this.jarPath = jarPath;
    }

    public String getConfigPreview() {
        ParserConfigurer p = new ParserConfigurer(configJsonPath, customizedParsers);
        return p.getPreviewDescription();
    }

    /**
     * Load the scenario and the visualization
     */
    public void loadScenario() throws IOException {
        if(configJsonPath != null) {
            //Scenario s = this.initScenario();
            System.out.println("parsing Beginning");
            Scenario s = this.startParser(configJsonPath, customizedParsers, jarPath);
            System.out.println("parsing end");
            this.scenario = new UIScenario(s);
            System.out.println("End Creation UIScenario");

            this.resetFilters();
            this.addDefaultFilter();

            this.timePaneController.setDurations();

            double minX = this.getMinX(s);
            double maxY = this.getMaxY(s);
            double minY = this.getMinY(s);
            double maxX = this.getMaxX(s);

            DrawingPositionCalculator.setMaxY(maxY);
            DrawingPositionCalculator.setMinX(minX);
            DrawingPositionCalculator.setMaxX(maxX);
            DrawingPositionCalculator.setMinY(minY);

            pane.setBackground(new Background(new BackgroundFill(Color.GRAY, null,null)));
            this.updatePaneSize();

            System.out.println("Positions Computation Beginning");
            this.goTo(1);
            System.out.println("Positions Computation End");

            System.out.println("Drawing Beginning");
            this.appliedFilters();
            System.out.println("NodeSize :"+this.scenario.getUINodesSize());
            System.out.println("LinkSize :"+this.scenario.getUILinkSize());
            System.out.println("PersonSize :"+this.scenario.getUIPersonSize());
            System.out.println("EventSize :"+this.scenario.getUIEventSize());
            System.out.println("VehicleSize :"+this.scenario.getUIVehicleSize());
            System.out.println("MaxX :"+maxX);
            System.out.println("MinX :"+minX);
            System.out.println("MaxY :"+maxY);
            System.out.println("MinY :"+minY);
            /*this.scenario.getUilinks().forEach(
                    l -> System.out.println(
                            "id :" + l.getLink().getId() +
                            ", x1 :" + l.getLink().getFromeObject().getX() +
                            ", y1 :" + l.getLink().getFromeObject().getY() +
                            ", x2 :" + l.getLink().getToObject().getX() +
                            ", y2 :" + l.getLink().getToObject().getY()

                    )
            );*/
            System.out.println("fin du dessin");

            // Loading analyzes

            this.analyzes = Map.of(
                    "Véhicules en circulation", new VehiclesInTrafficAnalysis(this.scenario),
                    "Personnes en activité", new PersonsInActivityAnalysis(this.scenario)
            );

            for(String analysisLabel : this.analyzes.keySet()) {
                MenuItem mi = new MenuItem(analysisLabel);
                mi.setOnAction(e -> {
                    try {
                        launchAnalysis(analysisLabel);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
                analyzesMenu.getItems().add(mi);
            }

            //getObserverController().personClicked(null, (UIPerson) this.scenario.getInstancePerID(UIPerson.class, 1));
            //getObserverController().vehicleClicked(null, (UIVehicle) this.scenario.getInstancePerID(UIVehicle.class, 1));
        }
    }

    /**
     * set the configJsonPath to the specified path
     * @param path String path
     */
    public void setPath(String path) {
        configJsonPath = path;
    }

    /**
     * function to add the default filters
     * @throws IOException if the Fxml Path couldn't be opened
     */
    private void addDefaultFilter() throws IOException {
        Filter fFacility = new FilterDefault(UIFacility.class, Color.BROWN);
        Filter fLink = new FilterDefault(UILink.class, Color.BLUE);
        Filter fNode = new FilterDefault(UINode.class, Color.BLACK);
        Filter fPerson = new FilterDefault(UIPerson.class, Color.RED);
        Filter fVehicle = new FilterDefault(UIVehicle.class, Color.GREEN);

        this.filterViewController.addFilters(List.of(fFacility,fLink,fNode,fPerson,fVehicle), "Default", true);
    }

    /**
     * Function to initilize the bindings
     */
    private void initializeKeyBinding() {
        this.keyBinding = new HashMap<>();
        this.keyBinding.put(KeyCode.U, new Pair<>("Zoom",key -> this.zoom()));
        this.keyBinding.put(KeyCode.Z, new Pair<>("Dézoom",key -> this.unzoom()));

        this.keyBinding.put(KeyCode.D, new Pair<>("Augmentation manuel",key -> this.timePaneController.manualProgress()));
        this.keyBinding.put(KeyCode.Q, new Pair<>("Diminution manuel",key -> this.timePaneController.manualReduce()));

        this.keyBinding.put(KeyCode.A, new Pair<>("Diminution du pas",key -> this.timePaneController.stepDecrease()));
        this.keyBinding.put(KeyCode.E, new Pair<>("Augmentation du pas",key -> this.timePaneController.stepIncrease()));

        this.keyBinding.put(KeyCode.W, new Pair<>("Diminution de la vitesse",key -> this.timePaneController.speedDecrease()));
        this.keyBinding.put(KeyCode.C, new Pair<>("Augmentation de la vitesse",key -> this.timePaneController.speedIncrease()));

        this.keyBinding.put(KeyCode.S, new Pair<>("Reprendre",key -> this.timePaneController.startScenario()));
        this.keyBinding.put(KeyCode.P, new Pair<>("Pause",key -> this.timePaneController.stopScenario()));
    }

    /**
     * function to change the time of the visualisation
     * @param time the new time
     */
    protected void goTo(double time){
        this.scenario.goTo(time);
        this.draw();
    }

    /**
     * function to apply all the filters
     */
    protected void appliedFilters(){
        this.filterViewController.appliedFilters(this.scenario);
        this.goTo(this.timePaneController.getI());
    }

    /**
     * function to get the minimum X value in the Scenario Node Set
     * @param s the scenario
     * @return minimum X value
     */
    private double getMinX(Scenario s){
        double result = Double.MAX_VALUE;
        for (Node n : s.getNetwork().getNodes().values()){
            if(n.getX() < result){
                result = n.getX();
            }
        }

        return result;
    }

    /**
     * function to get the maximum X value in the Scenario Node Set
     * @param s the scenario
     * @return maximum X value
     */
    private double getMaxX(Scenario s){
        double result = Double.MIN_VALUE;
        for (Node n : s.getNetwork().getNodes().values()){
            if(n.getX() > result){
                result = n.getX();
            }
        }

        return result;
    }

    /**
     * function to get the maximum Y value in the Scenario Node Set
     * @param s the scenario
     * @return maximum Y value
     */
    private double getMaxY(Scenario s){
        double result = Double.MIN_VALUE;
        for (Node n : s.getNetwork().getNodes().values()){
            if(n.getY() > result){
                result = n.getY();
            }
        }
        return result;
    }

    /**
     * function to get the minimum Y value in the Scenario Node Set
     * @param s the scenario
     * @return minimum Y value
     */
    private double getMinY(Scenario s){
        double result = Double.MAX_VALUE;
        for (Node n : s.getNetwork().getNodes().values()){
            if(n.getY() < result){
                result = n.getY();
            }
        }

        return result;
    }

    /**
     * the function to draw all the elements one by one
     */
    protected void draw(){
        Controller.this.pane.getChildren().clear();
        for(UILink link: this.scenario.getUilinks()){
            link.draw(pane, this);
        }
        for(UIFacility facility: this.scenario.getUifacilities()){
            facility.draw(pane, this);
        }
        for(UIVehicle vehicle: this.scenario.getUivehicles()){
            vehicle.draw(pane, this);
        }
        for(UIPerson person: this.scenario.getUipersons()){
            person.draw(pane, this);
        }
        for(UINode node: this.scenario.getUinodes()){
            node.draw(pane, this);
        }
        Controller.this.updatePaneSize();
    }
    /**
     * Start the parser and create a scenario result from a path
     * @param configJsonPath the path of the parser configuration file
     * @param customizedParsers the boolean value for the customized parser
     * @param jarPath the jar path for the customize parser
     * @return the result scenario
     */
    public Scenario startParser(String configJsonPath, boolean customizedParsers, String jarPath) {
        // Create a parser from the given configuration path
        ParserConfigurer parserConfigurer =  new ParserConfigurer(configJsonPath,customizedParsers);
        if (customizedParsers) {
            parserConfigurer.setJarPath(jarPath);
        }
        // Launch the parser and return the parsing result
        return parserConfigurer.parse();
    }

    /**
     * met à jour le panneau dans le scroll pane (zone de dessin)
     */
    private void updatePaneSize(){
        //calculer xmax et ymax
        double xmax = Math.max(scrollPane.getWidth(), DrawingPositionCalculator.getPaneXSize());
        double ymax = Math.max(scrollPane.getHeight(), DrawingPositionCalculator.getPaneYSize());
        pane.setMaxSize(xmax, ymax);
        pane.setMinSize(xmax,ymax);
    }

    /**
     * function called to zoom the map
     */
    private void zoom() {
        //System.out.println("ZOOM");
        DrawingPositionCalculator.setZoom(DrawingPositionCalculator.getZoom() / 2);
        Controller.this.draw();
    }

    /**
     * function called to unzoom the map
     */
    private void unzoom() {
        //System.out.println("UNZOOM");
        DrawingPositionCalculator.setZoom(DrawingPositionCalculator.getZoom() * 2);
        Controller.this.draw();
        //System.out.println("X :"+DrawingPostionCalulator.getPaneXSize());
        //System.out.println("Y :"+DrawingPostionCalulator.getPaneYSize());
    }


    /**
     * default function when key pressed
     * @param keyCode the keyCode of the key Pressed
     */
    private void defaultFunctionKeyPressed(KeyCode keyCode){
        System.out.println("Unknown bindings " + keyCode.getName());
    }

    /**
     * exit the application
     */
    @FXML
    private void exit() {
        System.exit(0);
    }

    /**
     * function called the button to Import a project is pressed
     * @param event the button pressed event
     * @throws IOException if the FXML file couldn't be opened
     */
    @FXML
    private void importProject(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("views/importProject.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("Import project");
        ((ImportProjectController)loader.getController()).setSuperController(this);
        // Specifies the modality for new window.
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setScene(new Scene(root,450,450));
        stage.initOwner(pane.getScene().getWindow());
        stage.show();
    }

    /**
     * function called the button to add a filter is pressed
     * @param event the button pressed event
     * @throws IOException if the FXML file couldn't be opened
     */
    @FXML
    private void addFilter(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("views/addFilter.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("Add Filter");
        ((AddFilterController)loader.getController()).setSuperController(this);
        // Specifies the modality for new window.
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setScene(new Scene(root));
        stage.initOwner(pane.getScene().getWindow());
        stage.show();
    }

    /**
     * function called when the user finished to add filter to add it in the filter tab
     * @param filtres the filters
     * @param name the filters Set Name
     */
    public void addFilters(List<Filter> filtres, String name) {
        try {
            this.filterViewController.addFilters(filtres, name, false);
            this.appliedFilters();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * this function is used to delete all the Filters
     */
    public void resetFilters() {
        this.filterViewController.resetFilters();
        this.appliedFilters();
    }

    /**
     * this function is used to delete all the Filters except default one
     */
    public void removeAllFilters() {
        this.filterViewController.deleteAllFilters();
        this.appliedFilters();
    }

    /**
     * this function is used to delete all the non applied filters
     */
    public void removeAllNonCheckedFilters() {
        this.filterViewController.deleteAllNonCheckedFilters();
        this.appliedFilters();
    }

    /**
     * function to get the observer Controller
     * @return observer Controller
     */
    public ObserverController getObserverController() {
        return observerController;
    }

    /**
     * function to get the minimum time value
     * @return the minimum time value
     */
    public double getMinDuration() {
        return this.scenario.getUievents().get(0).getEvent().getTime();
    }

    /**
     * function to get the maximum time value
     * @return the maximum time value
     */
    public double getMaxDuration() {
        return this.scenario.getUievents().get(this.scenario.getUievents().size() - 1).getEvent().getTime();
    }

    /**
     * function called when the the pane to show shortcuts is called
     * @throws IOException if the FXML file couldn't be opened
     */
    @FXML
    public void showRaccourcis() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("views/racourcis.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("Raccourcis");
        ((RacourcisController)loader.getController()).setRaccourcis(this.keyBinding);
        // Specifies the modality for new window.
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setScene(new Scene(root,600,450));
        stage.initOwner(pane.getScene().getWindow());
        stage.show();
    }

    /**
     * Method called from the Analysis menu options launching the AnalysisController
     * with the right analysis uimodel instance to visualize an analysis based on the scenario
     * @param analysisLabel The analysis label name
     */
    public void launchAnalysis(String analysisLabel) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("views/analysis.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle(analysisLabel);
        AnalysisController analysisController = (AnalysisController) loader.getController();
        analysisController.setAnalysis(analyzes.get(analysisLabel));
        analysisController.generateChartResult(Optional.empty());
        // Specifies the modality for new window.
        stage.initModality(Modality.NONE);
        stage.setScene(new Scene(root,820,660));
        stage.initOwner(pane.getScene().getWindow());
        stage.show();
    }

    /**
     * function to load a default MatSim Scenario (Equil)
     * @param actionEvent
     */
    public void loadDefaultScenario(ActionEvent actionEvent) throws IOException {
        setPath("jar:inputs/matsimDefault/ParserConfig.json");
        DrawingPositionCalculator.setZoom(32);
        this.loadScenario();
    }
}
