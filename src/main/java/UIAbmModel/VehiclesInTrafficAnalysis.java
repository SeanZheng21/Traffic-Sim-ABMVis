package UIAbmModel;

import AbmModel.Event;
import AbmModel.TrafficEvent;
import AbmModel.Vehicle;
import AbmModel.VehicleEntersLink;
import MatsimModel.MSVehicle;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Analyze implementation for the number of vehicles in traffic over the time
 */
public class VehiclesInTrafficAnalysis extends Analysis {

    /**
     * Constructor
     * @param uiScenario The current uiScenario to be analyzed
     */
    public VehiclesInTrafficAnalysis(UIScenario uiScenario) {
        super(uiScenario);
        title = "Véhicules en circulation au cours du temps";
        cls = Vehicle.class;
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
            if(event instanceof RunnableTrafficEvent && event.getEvent().isValid()) {
                TrafficEvent e = (TrafficEvent) event.getEvent();
                if(type.isEmpty() || e.getVehicleObject().getTypes().getValues(type.get().getKey()).contains(type.get().getValue())) {
                    result.add(new Pair(event.getEvent().getTime(), amplitude));
                    if(event instanceof RunnableVehicleEntersLink) amplitude += 1.0;
                    if(event instanceof RunnableVehicleLeavesLink) amplitude -= 1.0;
                    result.add(new Pair(event.getEvent().getTime(), amplitude));
                    if(amplitude < min) min = amplitude;
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
