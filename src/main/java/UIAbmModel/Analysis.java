package UIAbmModel;

import AbmModel.SimModel;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * Abstract concept to compute an analysis from the model part
 */
public abstract class Analysis {

    /**
     * The current visualized scenario
     */
    protected UIScenario uiScenario;

    /**
     * Analysis title
     */
    protected String title;

    /**
     * SimModel class analyzed
     */
    protected Class<? extends SimModel> cls;

    /**
     * Starting abscissa value for the graphic chart
     */
    protected double x0;

    /**
     * Ending abscissa value for the graphic chart
     */
    protected double xf;

    /**
     * Step value for the graphic chart
     */
    protected int step;

    /**
     * List of points to display in the diagram per type
     */
    protected HashMap<String, List<Pair<Double, Double>>> resPerType;

    /**
     * Constructor
     * @param uiScenario The current uiScenario to be analyzed
     */
    Analysis(UIScenario uiScenario) {
        this.uiScenario = uiScenario;
        resPerType = new HashMap<>();
        resPerType.put("All", new ArrayList<>());
        List<RunnableEvent> events = uiScenario.getUievents();
        x0 = events.get(0).getEvent().getTime();
        xf = events.get(events.size() - 1).getEvent().getTime();
        step = (int)((xf - x0) / 10);
    }

    /**
     * Computation method to implement in subclass, the computation
     * is based on the scenario visualized and depend on the analysis performed
     * If a type is specified, then only the points for this type will be generated
     * @param type Optional pair with K=V for type=value
     */
    public abstract void computeAnalysis(Optional<Pair<String, String>> type);

    public String getTitle() {
        return title;
    }

    public HashMap<String, List<Pair<Double, Double>>> getResPerType() {
        return resPerType;
    }

    public Class<? extends SimModel> getCls() {
        return cls;
    }

    public double getX0() {
        return x0;
    }

    public double getXf() {
        return xf;
    }

    public int getStep() {
        return step;
    }

}
