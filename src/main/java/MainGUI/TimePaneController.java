package MainGUI;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextInputControl;
import javafx.util.Duration;
import javafx.util.converter.NumberStringConverter;

/**
 * this class is the controller for the timeline part of the application
 */
public class TimePaneController {

    /**
     * the Main Controller
     */
    private Controller superControlleur;

    /**
     * the button for the pause
     */
    @FXML private Button pauseButton;

    /**
     * Input for the time Period
     */
    @FXML private TextInputControl timePeriodField;

    /**
     * Input for the step
     */
    @FXML private TextInputControl stepField;

    /**
     * Input for the speed
     */
    @FXML private TextInputControl speedField;

    /**
     * slider for the time period
     */
    @FXML private Slider timeSlider;

    /**
     * Slider for the speed
     */
    @FXML private Slider speedSlider;

    /**
     * time periode
     */
    private IntegerProperty i;

    /**
     * step when manual moving
     */
    private IntegerProperty step;

    /**
     * step when auto moving
     */
    private IntegerProperty speed;

    /**
     * boolean value true if the simulation is in break
     */
    private boolean pause = true;

    /**
     * timeline object tu deal with the auto simulation
     */
    private Timeline timeline;

    /**
     * Maximum value of the time in the simulation
     */
    private double maxDuration = 0;

    /**
     * Minimum value of the time in the simulation
     */
    private double minDuration = 0;

    //=====================================================
    //initialisation
    //=====================================================

    /**
     * Initialize function FXML function
     */
    @FXML
    public void initialize(){
        i = new SimpleIntegerProperty((int) minDuration);
        step = new SimpleIntegerProperty(1);
        speed = new SimpleIntegerProperty(1);
        this.initializeField();
        this.initializeTimeLine();
    }

    /**
     * initialize the bindings for the fields
     */
    private void initializeField() {
        timePeriodField.textProperty().bindBidirectional(this.i, new NumberStringConverter());
        stepField.textProperty().bindBidirectional(this.step, new NumberStringConverter());
        speedField.textProperty().bindBidirectional(this.speed, new NumberStringConverter());
        timeSlider.setMax(this.maxDuration);
        timeSlider.setMin(this.minDuration);
        timeSlider.valueProperty().bindBidirectional(this.i);
        timeSlider.setOnMouseClicked(mouseEvent -> superControlleur.goTo(this.i.get()));
        speedSlider.valueProperty().bindBidirectional(this.speed);
    }

    /**
     * initialize the timeline
     */
    private void initializeTimeLine(){
        KeyFrame keyFrame = new KeyFrame(Duration.millis(200),
                event2 -> {
                    this.i.setValue(i.get() + speed.get());
                    if(speed.get() > 0 && i.get() > maxDuration) {
                        this.stopScenario();
                        this.i.setValue((int)maxDuration);
                    }
                    if(i.get() < 0 && speed.get() < minDuration) {
                        this.stopScenario();
                        this.i.setValue((int)minDuration);
                    }
                    //System.out.println("avancer à " + i);
                    superControlleur.goTo(i.get());
                }
        );
        this.timeline = new Timeline(keyFrame);
    }

    //=====================================================
    //setters
    //=====================================================

    /**
     * setter for the super controller
     * @param superControlleur the superController to call functions
     */
    public void setSuperControlleur(Controller superControlleur) {
        this.superControlleur = superControlleur;

    }

    /**
     * set all the durations for the min and the max for the slider bar
     */
    public void setDurations() {
        this.minDuration = this.superControlleur.getMinDuration();
        this.maxDuration = this.superControlleur.getMaxDuration();
        timeSlider.setMax(this.maxDuration);
        timeSlider.setMin(this.minDuration);
        this.i.setValue(this.minDuration);
    }

    //=====================================================
    //functions
    //=====================================================
    /**
     * function called when manual progress is done
     */
    protected void manualProgress(){
        this.i.setValue(i.get()+step.get());
        superControlleur.goTo(i.get());
    }

    /**
     * function called when manual reduce is done
     */
    protected void manualReduce(){
        if(i.get() - minDuration > step.get()) {
            this.i.setValue(i.get()-step.get());
        } else {
            this.i.setValue(minDuration);
        }
        superControlleur.goTo(i.get());
    }


    /**
     * function called when increasing the step
     */
    protected void stepIncrease(){
        step.setValue(step.get() + 1);
        superControlleur.draw();
    }

    /**
     * function called when decreasing the step
     */
    protected void stepDecrease(){
        if (step.get() > 1){
            step.setValue(step.get() - 1);
            superControlleur.draw();
        }
    }

    /**
     * function called when increasing the speed
     */
    protected void speedIncrease(){
        speed.setValue(speed.get()+1);
        superControlleur.draw();
    }


    /**
     * function called when decreasing the speed
     */
    protected void speedDecrease(){
        speed.setValue(speed.get()-1);
        superControlleur.draw();
    }

    /**
     * continue the scenario execution
     */
    protected void startScenario(){
        this.timeline.setCycleCount(Animation.INDEFINITE);
        this.timeline.play();
        pause = false;
        this.pauseButton.setText("||");
    }

    /**
     * continue the scenario execution
     */
    protected void stopScenario(){
        this.timeline.stop();
        pause = true;
        this.pauseButton.setText("|>");
    }

    /**
     * getter for the time period
     * @return the time moment i
     */
    public int getI() {
        return i.get();
    }

    //=====================================================
    //FXML functions
    //=====================================================

    /**
     * function called when the pause button is pressed
     * @param event the button pressed event
     */
    @FXML
    private void pauseButtonPressed(ActionEvent event) {
        if (pause) {
            this.startScenario();
        } else {
            this.stopScenario();
        }
    }

    /**
     * function called when the step decrease button is pressed
     * @param event the button pressed event
     */
    @FXML
    private void stepDecreaseButtonPressed(ActionEvent event) {
        this.manualReduce();
    }

    /**
     * function called when the step increase button is pressed
     * @param event the button pressed event
     */
    @FXML
    private void stepIncreaseButtonPressed(ActionEvent event) {
        this.manualProgress();
    }
}
