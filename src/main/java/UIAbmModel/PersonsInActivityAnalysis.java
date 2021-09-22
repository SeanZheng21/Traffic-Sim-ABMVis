package UIAbmModel;

import AbmModel.ActEvent;
import AbmModel.Person;
import AbmModel.TrafficEvent;
import AbmModel.Vehicle;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Analyze implementation for the number of persons in activity over the time
 */
public class PersonsInActivityAnalysis extends Analysis {

    /**
     * Constructor
     * @param uiScenario The current uiScenario to be analyzed
     */
    public PersonsInActivityAnalysis(UIScenario uiScenario) {
        super(uiScenario);
        title = "Personnes en activité";
        cls = Person.class;
    }

    @Override
    public void computeAnalysis(Optional<Pair<String, String>> type) {
        List<Pair<Double, Double>> result;
        String typeValue = (type.isEmpty()) ? "All" : type.get().getKey() + "/" + type.get().getValue();
        resPerType.put(typeValue, new ArrayList<>());
        result = resPerType.get(typeValue);

        List<RunnableEvent> events = uiScenario.getUievents();
        double amplitude = 0.0;
        double min = 0.0;
        result.add(new Pair(x0, amplitude));

        for(RunnableEvent event : events) {
            if(event instanceof RunnableActEvent && event.getEvent().isValid()) {
                ActEvent e = (ActEvent) event.getEvent();
                if(type.isEmpty() || e.getPersonObject().getTypes().getValues(type.get().getKey()).contains(type.get().getValue())) {
                    result.add(new Pair(event.getEvent().getTime(), amplitude));
                    if (event instanceof RunnableActStartEvent) amplitude += 1.0;
                    if (event instanceof RunnableActEndEvent) amplitude -= 1.0;
                    result.add(new Pair(event.getEvent().getTime(), amplitude));
                    if (amplitude < min) min = amplitude;
                }
            }
        }

        if(!result.isEmpty()) {
            Pair<Double, Double> lastPoint = result.get(result.size() - 1);
            result.add(new Pair(xf, lastPoint.getValue()));
        }

        if(min != 0.0) {
            List<Pair<Double, Double>> update = new ArrayList<>();
            for(Pair<Double, Double> point : result) update.add(new Pair<>(point.getKey(), point.getValue() - min));
            resPerType.put(typeValue, update);
        }
    }
}
