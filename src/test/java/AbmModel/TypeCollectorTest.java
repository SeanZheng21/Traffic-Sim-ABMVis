package AbmModel;

import MatsimModel.*;
import jdk.dynalink.linker.MethodTypeConversionStrategy;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class TypeCollectorTest {

    Node node0; Node node1; Node node2; Node node3; Node node4;
    Node node5; Node node6; Node node7; Node node8; Node node9;

    Link link0; Link link1; Link link2; Link link3; Link link4;
    Link link5; Link link6; Link link7; Link link8; Link link9;

    MSVehicleEntersLink vel0;
    MSVehicleEntersLink vel1;
    MSVehicleEntersLink vel2;
    MSVehicleEntersLink vel3;
    MSVehicleEntersLink vel4;
    MSVehicleEntersLink vel5;

    @BeforeEach
    void setUp() {
        node0 = new MSNode(0, 0.0, 0.0, "Ori");
        node1 = new MSNode(1, 0.0, 1.0, "Ord");
        node2 = new MSNode(2, 0.0, 2.0, "Ord");
        node4 = new MSNode(4, 1.0, 3.0, "Out");
        node5 = new MSNode(5, 2.0, 3.0, "Out");
        node6 = new MSNode(6, 3.0, 2.0, "Out");
        node7 = new MSNode(7, 3.0, 1.0, "Out");
        node8 = new MSNode(8, 2.0, 0.0, "Abs");
        node9 = new MSNode(9, 1.0, 0.0, "Abs");

        link0 = new MSLink(10.0, 1.0, 50.0, "car, bus, bicycle", "road", new HashSet(), 0, 0, node0, 1, node1, 100.0);
        link1 = new MSLink(10.0, 1.0, 50.0, "car, bus", "highway", new HashSet(), 1, 1, node1, 2, node2, 100.0);
        link2 = new MSLink(10.0, 1.0, 50.0, "bicycle, moto", "road", new HashSet(), 2, 2, node2, 3, node3, 100.0);
        link3 = new MSLink(10.0, 1.0, 50.0, "bus", "busline", new HashSet(), 3, 3, node3, 4, node4, 100.0);
        link4 = new MSLink(10.0, 1.0, 50.0, "pedestrian", "path", new HashSet(), 4, 4, node4, 5, node5, 100.0);
        link5 = new MSLink(10.0, 1.0, 50.0, "pedestrian, roller, skate", "path", new HashSet(), 5, 5, node5, 6, node6, 100.0);
        link6 = new MSLink(10.0, 1.0, 50.0, "plane, tank", "unclassified", new HashSet(), 6, 6, node6, 7, node7, 100.0);
        link7 = new MSLink(10.0, 1.0, 50.0, "car, bus, moto", "road", new HashSet(), 7, 7, node7, 8, node8, 100.0);
        link8 = new MSLink(10.0, 1.0, 50.0, "bicycle, roller, skate", "path", new HashSet(), 8, 8, node8, 9, node9, 100.0);
        link9 = new MSLink(10.0, 1.0, 50.0, "", "unclassified", new HashSet(), 9, 9, node9, 0, node0, 100.0);

        vel0 = new MSVehicleEntersLink(0, 0.0, "", 0.0, 0.0, 0, 0, 0, 0, new HashMap<>(), null, null);
        vel1 = new MSVehicleEntersLink(1, 0.0, "", 0.0, 0.0, 0, 0, 0, 0, new HashMap<>(), null, null);
        vel2 = new MSVehicleEntersLink(2, 0.0, "", 0.0, 0.0, 0, 0, 0, 0, new HashMap<>(), null, null);
        vel3 = new MSVehicleEntersLink(3, 0.0, "", 0.0, 0.0, 0, 0, 0, 0, new HashMap<>(), null, null);
        vel4 = new MSVehicleEntersLink(4, 0.0, "", 0.0, 0.0, 0, 0, 0, 0, new HashMap<>(), null, null);
        vel5 = new MSVehicleEntersLink(5, 0.0, "", 0.0, 0.0, 0, 0, 0, 0, new HashMap<>(), null, null);

        MSTypeCollector.clear();

        MSTypeCollector.addEntry(MSNode.class, "type", "ori", node0);
        MSTypeCollector.addEntries(MSNode.class, "type", "ord", Set.of(node1, node2));
        MSTypeCollector.addEntries(MSNode.class, "type", "out", Set.of(node4, node5, node6, node7));
        MSTypeCollector.addEntries(MSNode.class, "type", "abs", Set.of(node8, node9));

        MSTypeCollector.addEntries(MSLink.class, "type", "road", Set.of(link0, link2, link7,link8));
        MSTypeCollector.addEntry(MSLink.class, "type", "highway", link1);
        MSTypeCollector.addEntry(MSLink.class, "type", "busline", link3);
        MSTypeCollector.addEntries(MSLink.class, "type", "path", Set.of(link4, link5, link8, link2));
        MSTypeCollector.addEntries(MSLink.class, "type", "unclassified", Set.of(link6, link9));
        MSTypeCollector.addEntries(MSLink.class, "modes", "car", Set.of(link0, link1, link7, link8));
        MSTypeCollector.addEntries(MSLink.class, "modes", "bus", Set.of(link0, link1, link3, link7, link2));
        MSTypeCollector.addEntries(MSLink.class, "modes", "bicycle", Set.of(link0, link2, link8));
        MSTypeCollector.addEntries(MSLink.class, "modes", "moto", Set.of(link2, link7));
        MSTypeCollector.addEntries(MSLink.class, "modes", "pedestrian", Set.of(link4, link5));
        MSTypeCollector.addEntries(MSLink.class, "modes", "roller", Set.of(link5, link8));
        MSTypeCollector.addEntries(MSLink.class, "modes", "skate", Set.of(link5, link8));
        MSTypeCollector.addEntry(MSLink.class, "modes", "plane", link6);
        MSTypeCollector.addEntry(MSLink.class, "modes", "tank", link6);
        MSTypeCollector.addEntries(MSLink.class, "modes", "blabla", Set.of(link0, link1, link3, link7, link2));
        MSTypeCollector.addEntries(MSLink.class, "nothing", "nowhere", Set.of(link5, link8));
        MSTypeCollector.addEntry(MSLink.class, "nothing", "nobody", link4);
        MSTypeCollector.removeEntry(MSLink.class, "type", "road", link8);
        MSTypeCollector.removeEntry(MSLink.class, "type", "path", link2);
        MSTypeCollector.removeEntry(MSLink.class, "modes", "car", link8);
        MSTypeCollector.removeEntry(MSLink.class, "modes", "bus", link2);
        MSTypeCollector.removeValue(MSLink.class, "modes", "blabla");
        MSTypeCollector.removeType(MSLink.class, "nothing");

        MSTypeCollector.addValue(MSVehicle.class, "type", "SUV");
        MSTypeCollector.addValue(MSVehicle.class, "type", "urban");
        MSTypeCollector.addValue(MSVehicle.class, "type", "unusedType");
        MSTypeCollector.addValue(MSVehicle.class, "model", "Peugeot");
        MSTypeCollector.addValue(MSVehicle.class, "model", "Lamborghini");
        MSTypeCollector.addValue(MSVehicle.class, "model", "outOfDate");
        MSTypeCollector.addValue(MSVehicle.class, "null", "thing1");
        MSTypeCollector.addValue(MSVehicle.class, "null", "thing2");
        MSTypeCollector.removeValue(MSVehicle.class, "model", "outOfDate");
        MSTypeCollector.removeValue(MSVehicle.class, "type", "unusedType");
        MSTypeCollector.removeType(MSVehicle.class, "null");

        MSTypeCollector.addType(MSPerson.class, "job");
        MSTypeCollector.addType(MSPerson.class, "nationality");
        MSTypeCollector.addType(MSPerson.class, "uselessType");
        MSTypeCollector.removeType(MSPerson.class, "uselessType");

        MSTypeCollector.addClass(MSFacility.class);

        MSTypeCollector.addClass(MSEvent.class);
        MSTypeCollector.addType(MSLegEvent.class, "leg");
        MSTypeCollector.addValue(MSTrafficEvent.class, "traffic", "high");
        MSTypeCollector.addValue(MSTrafficEvent.class, "traffic", "low");
        MSTypeCollector.addEntries(MSVehicleEntersLink.class, "type1", "value1", Set.of(vel0, vel1));
        MSTypeCollector.addEntry(MSVehicleEntersLink.class, "type1", "value2", vel2);
        MSTypeCollector.addEntries(MSVehicleEntersLink.class, "type2", "value1", Set.of(vel3, vel4));
        MSTypeCollector.addEntry(MSVehicleEntersLink.class, "type2", "value2", vel5);
        MSTypeCollector.removeClass(MSEvent.class);
        MSTypeCollector.removeClass(MSLegEvent.class);
        MSTypeCollector.removeClass(MSTrafficEvent.class);
        MSTypeCollector.removeClass(MSVehicleEntersLink.class);

    }

    @Test
    void testClasses() {
        Set<Class<? extends SimModel>> classes = MSTypeCollector.getClasses();

        assertFalse(classes.isEmpty());

        assertEquals(5, classes.size());

        assertTrue(classes.containsAll(Set.of(MSNode.class, MSLink.class, MSVehicle.class, MSPerson.class, MSFacility.class)));
    }

    @Test
    void testTypes() {
        Set<String> nodeTypes = MSTypeCollector.getTypes(MSNode.class);
        Set<String> linkTypes = MSTypeCollector.getTypes(MSLink.class);
        Set<String> vehicleTypes = MSTypeCollector.getTypes(MSVehicle.class);
        Set<String> personTypes = MSTypeCollector.getTypes(MSPerson.class);
        Set<String> facilityTypes = MSTypeCollector.getTypes(MSFacility.class);
        Set<String> eventTypes = MSTypeCollector.getTypes(MSEvent.class);

        assertFalse(nodeTypes.isEmpty());
        assertFalse(linkTypes.isEmpty());
        assertFalse(vehicleTypes.isEmpty());
        assertFalse(personTypes.isEmpty());
        assertTrue(facilityTypes.isEmpty());
        assertTrue(eventTypes.isEmpty());

        assertEquals(1, nodeTypes.size());
        assertEquals(2, linkTypes.size());
        assertEquals(2, vehicleTypes.size());
        assertEquals(2, personTypes.size());

        assertTrue(nodeTypes.containsAll(Set.of("type")));
        assertTrue(linkTypes.containsAll(Set.of("type", "modes")));
        assertTrue(vehicleTypes.containsAll(Set.of("type", "model")));
        assertTrue(personTypes.containsAll(Set.of("job", "nationality")));
    }

    @Test
    void testValues() {
        Set<String> nodeTypeValues = MSTypeCollector.getValues(MSNode.class, "type");
        Set<String> linkModesValues = MSTypeCollector.getValues(MSLink.class, "modes");
        Set<String> vehicleModelValues = MSTypeCollector.getValues(MSVehicle.class, "model");
        Set<String> personJobValues = MSTypeCollector.getValues(MSPerson.class, "job");
        Set<String> facilityTypeValues = MSTypeCollector.getValues(MSFacility.class, "type");
        Set<String> eventTypeValues = MSTypeCollector.getValues(MSEvent.class, "type");

        assertFalse(nodeTypeValues.isEmpty());
        assertFalse(linkModesValues.isEmpty());
        assertFalse(vehicleModelValues.isEmpty());
        assertTrue(personJobValues.isEmpty());
        assertTrue(facilityTypeValues.isEmpty());
        assertTrue(eventTypeValues.isEmpty());

        assertEquals(4, nodeTypeValues.size());
        assertEquals(9, linkModesValues.size());
        assertEquals(2, vehicleModelValues.size());

        assertTrue(nodeTypeValues.containsAll(Set.of("ori", "abs", "ord", "out")));
        assertTrue(linkModesValues.containsAll(Set.of("car", "bus", "bicycle", "moto", "pedestrian", "roller", "skate", "plane", "tank")));
        assertTrue(vehicleModelValues.containsAll(Set.of("Peugeot", "Lamborghini")));
    }

    @Test
    void testInstances() {
        Set<SimModel> outNodes = MSTypeCollector.getInstances(MSNode.class, "type", "out");
        Set<SimModel> busLinks = MSTypeCollector.getInstances(MSLink.class, "modes", "bus");
        Set<SimModel> peugeotVehicles = MSTypeCollector.getInstances(MSVehicle.class, "model", "Peugeot");
        Set<SimModel> teacherPersons = MSTypeCollector.getInstances(MSPerson.class, "job", "teacher");
        Set<SimModel> marketFacilities = MSTypeCollector.getInstances(MSFacility.class, "type", "market");
        Set<SimModel> personEntersVehicleEvents = MSTypeCollector.getInstances(MSEvent.class, "type", "PersonEntersVehicle");

        assertFalse(outNodes.isEmpty());
        assertFalse(busLinks.isEmpty());
        assertTrue(peugeotVehicles.isEmpty());
        assertTrue(teacherPersons.isEmpty());
        assertTrue(marketFacilities.isEmpty());
        assertTrue(personEntersVehicleEvents.isEmpty());

        assertEquals(4, outNodes.size());
        assertEquals(4, busLinks.size());

        assertTrue(outNodes.containsAll(Set.of(node4, node5, node6, node7)));
        assertTrue(busLinks.containsAll(Set.of(link0, link1, link3, link7)));
    }

    @Test
    void testException() {
        assertThrows(IllegalArgumentException.class, () -> MSTypeCollector.addEntry(MSVehicle.class, "type", "SUV", link0));
        assertThrows(IllegalArgumentException.class, () -> MSTypeCollector.addEntries(MSVehicle.class, "type", "SUV", Set.of(link0, link1)));
    }
}
