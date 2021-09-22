package Parsers;

import AbmModel.*;
import AbmParser.ParserConfigurer;
import MatsimModel.*;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class TestTypesParser {
    ParserConfigurer parserConfigurer;
    Scenario scenario;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        MSTypeCollector.clear();
        parserConfigurer =  new ParserConfigurer("./input/ParserConfig.json", false);
        scenario = parserConfigurer.parse();

    }

    @Test
    void testTypeCollectorClasses() {
        Set<Class<? extends SimModel>> classes = MSTypeCollector.getClasses();

        assertEquals(5, classes.size());

        assertTrue(classes.containsAll(Set.of(MSPerson.class, MSVehicle.class, MSLink.class, MSNode.class, MSFacility.class)));
    }

    @Test
    void testTypeCollectorTypes() {
        Set<String> personTypes = MSTypeCollector.getTypes(MSPerson.class);
        Set<String> vehicleTypes = MSTypeCollector.getTypes(MSVehicle.class);
        Set<String> linkTypes = MSTypeCollector.getTypes(MSLink.class);
        Set<String> nodeTypes = MSTypeCollector.getTypes(MSNode.class);
        Set<String> facilityTypes = MSTypeCollector.getTypes(MSFacility.class);

        assertEquals(4, personTypes.size());
        assertEquals(1, vehicleTypes.size());
        assertEquals(2, linkTypes.size());
        assertEquals(1, nodeTypes.size());
        assertEquals(3, facilityTypes.size());

        assertTrue(personTypes.containsAll(Set.of("sex", "license", "car_avail", "job")));
        assertTrue(vehicleTypes.containsAll(Set.of("type")));
        assertTrue(linkTypes.containsAll(Set.of("modes", "type")));
        assertTrue(nodeTypes.containsAll(Set.of("type")));
        assertTrue(facilityTypes.containsAll(Set.of("access", "type", "opentime")));
    }

    @Test
    void testTypeCollectorValues() {
        Set<String> jobValues = MSTypeCollector.getValues(MSPerson.class, "job");
        Set<String> vTypeValues = MSTypeCollector.getValues(MSVehicle.class, "type");
        Set<String> modesValues = MSTypeCollector.getValues(MSLink.class, "modes");
        Set<String> nTypeValues = MSTypeCollector.getValues(MSNode.class, "type");
        Set<String> opentimeValues = MSTypeCollector.getValues(MSFacility.class, "opentime");

        assertEquals(4, jobValues.size());
        assertEquals(2, vTypeValues.size());
        assertEquals(6, modesValues.size());
        assertEquals(3, nTypeValues.size());
        assertEquals(7, opentimeValues.size());

        assertTrue(jobValues.containsAll(Set.of("teacher", "thesis", "student", "unemployed")));
        assertTrue(vTypeValues.containsAll(Set.of("defaultVehicleType", "actor")));
        assertTrue(modesValues.containsAll(Set.of("car", "bus", "truck", "motorcycle", "pedestrian", "bicycle")));
        assertTrue(nTypeValues.containsAll(Set.of("pedestrians", "streets", "publictransport")));
        assertTrue(opentimeValues.containsAll(Set.of("mon", "tue", "wen", "thu", "fri", "sat", "sun")));
    }

    @Test
    void testTypeCollectorInstances() {
        Set<SimModel> malePersons = MSTypeCollector.getInstances(MSPerson.class, "sex", "m");
        Set<SimModel> actorVehicles = MSTypeCollector.getInstances(MSVehicle.class, "type", "actor");
        Set<SimModel> publictransportLinks = MSTypeCollector.getInstances(MSLink.class, "type", "publictransport");
        Set<SimModel> pedestriansNodes = MSTypeCollector.getInstances(MSNode.class, "type", "pedestrians");
        Set<SimModel> openThuFacilities = MSTypeCollector.getInstances(MSFacility.class, "opentime", "thu");

        assertEquals(5, malePersons.size());
        assertEquals(6, actorVehicles.size());
        assertEquals(10, publictransportLinks.size());
        assertEquals(6, pedestriansNodes.size());
        assertEquals(3, openThuFacilities.size());

        Map<Integer, Person> p = scenario.getPersons();
        Map<Integer, Vehicle> v = scenario.getVehicles();
        Map<Long, Link> l = scenario.getNetwork().getLinks();
        Map<Long, Node> n = scenario.getNetwork().getNodes();
        Map<Integer, Facility> f = scenario.getFacilities();

        assertTrue(malePersons.containsAll(Set.of(p.get(1), p.get(2), p.get(3), p.get(4), p.get(5))));
        assertTrue(actorVehicles.containsAll(Set.of(v.get(1), v.get(2), v.get(3), v.get(4), v.get(5), v.get(6))));
        assertTrue(publictransportLinks.containsAll(Set.of(l.get((long)6), l.get((long)7), l.get((long)8), l.get((long)9), l.get((long)10), l.get((long)11), l.get((long)12), l.get((long)13), l.get((long)14), l.get((long)15))));
        assertTrue(pedestriansNodes.containsAll(Set.of(n.get((long)7), n.get((long)8), n.get((long)9), n.get((long)10), n.get((long)11), n.get((long)12))));
        assertTrue(openThuFacilities.containsAll(Set.of(f.get(1), f.get(10), f.get(100))));
    }

    @Test
    void testTypesObjectsType() {
        Set<String> pTypes = scenario.getPerson(2).getTypes().getTypes();
        Set<String> vTypes = scenario.getVehicle(1).getTypes().getTypes();
        Set<String> lTypes = scenario.getNetwork().getLink(9).getTypes().getTypes();
        Set<String> nTypes = scenario.getNetwork().getNode(2).getTypes().getTypes();
        Set<String> fTypes = scenario.getFacility(1).getTypes().getTypes();

        assertEquals(4, pTypes.size());
        assertEquals(1, vTypes.size());
        assertEquals(2, lTypes.size());
        assertEquals(1, nTypes.size());
        assertEquals(3, fTypes.size());

        assertTrue(pTypes.containsAll(Set.of("sex", "car_avail", "license", "job")));
        assertTrue(vTypes.containsAll(Set.of("type")));
        assertTrue(lTypes.containsAll(Set.of("modes", "type")));
        assertTrue(nTypes.containsAll(Set.of("type")));
        assertTrue(fTypes.containsAll(Set.of("access", "type", "opentime")));
    }

    @Test
    void testTypesObjectsValues() {
        Set<String> jobs = scenario.getPerson(2).getTypes().getValues("job");
        Set<String> vTypes = scenario.getVehicle(1).getTypes().getValues("type");
        Set<String> modes = scenario.getNetwork().getLink(9).getTypes().getValues("modes");
        Set<String> nTypes = scenario.getNetwork().getNode(9).getTypes().getValues("type");
        Set<String> opentimes = scenario.getFacility(1).getTypes().getValues("opentime");

        assertEquals(2, jobs.size());
        assertEquals(2, vTypes.size());
        assertEquals(3, modes.size());
        assertEquals(3, nTypes.size());
        assertEquals(6, opentimes.size());

        assertTrue(jobs.containsAll(Set.of("teacher", "thesis")));
        assertTrue(vTypes.containsAll(Set.of("defaultVehicleType", "actor")));
        assertTrue(modes.containsAll(Set.of("car", "bus", "motorcycle")));
        assertTrue(nTypes.containsAll(Set.of("streets", "publictransport", "pedestrians")));
        assertTrue(opentimes.containsAll(Set.of("mon", "tue", "wen", "thu", "fri", "sat")));
    }

    @Test
    void testDisp() {/*
        Set<SimModel> models = new HashSet<>();
        for(Class<? extends SimModel> cls : MSTypeCollector.getClasses()) {
            System.out.println(cls);
            for(String type : MSTypeCollector.getTypes(cls)) {
                System.out.println("\t" + type);
                for(String value : MSTypeCollector.getValues(cls, type)) {
                    System.out.println("\t\t" + value);
                    for(SimModel instance : MSTypeCollector.getInstances(cls, type, value)) {
                        System.out.println("\t\t\t" + instance);
                        models.add(instance);
                    }
                }
            }
        }
        for(SimModel instance : models) {
            if(instance instanceof MSLink) System.out.println(((MSLink)instance).getTypes().getValues("modes"));
            if(instance instanceof MSVehicle) System.out.println(((MSVehicle)instance).getTypes().getValues("type"));
            if(instance instanceof MSPerson) System.out.println(((MSPerson)instance).getTypes().getValues("sex"));
        }*/
    }
}
