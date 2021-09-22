package MainGUI;

import AbmModel.SimModel;
import AbmModel.Type;
import AbmModel.TypeCollector;
import AbmModel.Vehicle;
import MatsimModel.MSVehicle;
import UIAbmModel.Analysis;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * Controller displaying the chosen analysis on a LineChart
 */
public class AnalysisController {

    /**
     * Analysis uimodel instance to use
     */
    private Analysis analysis;

    /**
     * Pane to draw the chart
     */
    @FXML
    private Pane chartPane;

    /**
     * Combo Box to list of the type that can be applied
     */
    @FXML
    private ComboBox comboBox;

    /**
     * Line Chart to display the analysis
     */
    private LineChart<Number,Number> lineChart;

    /**
     * Map linking type values to their data series in the line chart
     */
    private HashMap<String, XYChart.Series> series;

    /**
     * Initialisation of the map
     */
    @FXML
    public void initialize() {
        series = new HashMap<>();
    }

    public void setAnalysis(Analysis analysis) {
        this.analysis = analysis;
    }

    /**
     * Generate the chart showing the results of the analysis
     * @param type Optional pair with K=V for type=value
     */
    public void generateChartResult(Optional<Pair<String, String>> type) {
        // LineChart and ComboBox initialization
        if(type.isEmpty()) {
            initiateChart();
            fillComboBox();
        }

        // Get the new serie tag
        String typeValue;
        if(type.isEmpty() || type.get().getValue().equals("All")) {
            typeValue = "All";
        } else {
            typeValue = type.get().getKey() + "/" + type.get().getValue();
        }

        // Get the result of the analysis from the UIModel
        analysis.computeAnalysis(type);
        List<Pair<Double, Double>> results = analysis.getResPerType().get(typeValue);

        // Initiate the new serie
        XYChart.Series serie = new XYChart.Series();
        serie.setName(typeValue);

        // Place the point on the chart
        for(Pair<Double, Double> point : results) {
            serie.getData().add(new XYChart.Data(point.getKey(), point.getValue()));
        }

        // Add the new serie to the chart and in the HashMap to store it
        lineChart.getData().addAll(serie);
        series.put(typeValue, serie);
    }

    /**
     * Initialize the graphic chart with starting/ending abscissa and step value
     */
    private void initiateChart() {
        NumberAxis xAxis = new NumberAxis(analysis.getX0(), analysis.getXf(), analysis.getStep());
        NumberAxis yAxis = new NumberAxis();
        lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle(analysis.getTitle());
        lineChart.setCreateSymbols(false);
        lineChart.setPrefSize(800, 600);
        chartPane.getChildren().add(lineChart);
    }

    /**
     * Fill the ComboBox with all type values that can be applied for this analysis
     */
    private void fillComboBox() {
        comboBox.getItems().add("All");
        for(Class<? extends SimModel> cls : TypeCollector.getClasses()) {
            if(analysis.getCls().isAssignableFrom(cls)) {
                for(String type : TypeCollector.getTypes(cls)) {
                    for(String value : TypeCollector.getValues(cls, type)) {
                        comboBox.getItems().add(type + "/" + value);
                    }
                }
            }
        }
        comboBox.getSelectionModel().selectFirst();
    }

    /**
     * Add line on the chart based on the selected type value in the combo box (if it hasn't been added yet)
     */
    public void add() {
        String typeValue = comboBox.getSelectionModel().getSelectedItem().toString();
        if(typeValue.equals("All")) {
            generateChartResult(Optional.of(new Pair<>("All", "All")));
        } else {
            String[] s = typeValue.split("/");
            if(!series.containsKey(s[0] + "/" + s[1])) {
                generateChartResult(Optional.of(new Pair(s[0], s[1])));
            }
        }
    }

    /**
     * Remove line from the chart based on the selected type value in the combo box (if it has been already added)
     */
    public void remove() {
        String typeValue = comboBox.getSelectionModel().getSelectedItem().toString();
        if(series.containsKey(typeValue)) {
            lineChart.getData().remove(series.get(typeValue));
            series.remove(typeValue);
        }
    }
}
