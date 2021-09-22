package AbmModel;

import MatsimModel.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class TypeTest {

    MSNode n, n1, n2, n3, n4;
    MSLink l;
    MSPerson p;
    MSVehicle v;
    MSFacility f;

    @BeforeEach
    void setUp() {

        MSTypeCollector.clear();

        n = new MSNode(0, 0, 0, "simple");
        n1 = new MSNode(1, 0, -500, "from");
        n2 = new MSNode(2, 0, 500, "to");
        n3 = new MSNode(3, 0, 0, "simple");
        n4 = new MSNode(4, 0, 0, "simple");
        l = new MSLink(0,1,50,"car, truck, motorcycle","highway", new HashSet<>(),0,1, n1,2, n2,1000);
        p = new MSPerson(0,"M",24,"yes", "always", "teacher", new ArrayList<>());
        v = new MSVehicle(0, new ArrayList<>());
        f = new MSFacility(0, 100,100, 1);

        n.addTypeEntry("Location", "Ord");
        n1.addTypeEntry("Location", "Ord");
        n2.addTypeEntry("Location", "Ord");
        n3.addTypeEntry("Location", "Ord, Abs");
        n4.addTypeEntry("Location", "Ord, Abs");
        n3.getTypes().removeEntry("Location", "Ord");
        n3.getTypes().removeEntry("Location", "Abs");
        n4.getTypes().removeType("Location");

        l.addTypeEntry("modes", "car, truck, motorcycle");
        l.addTypeEntry("type", "highway, unexpected");
        l.addTypeEntry("useless", "nothing, none, to_be_deleted");
        l.getTypes().removeEntry("type", "unexpected");
        l.getTypes().removeType("useless");

        p.addTypeEntry("sex", "M");
        p.addTypeEntry("license", "yes");
        p.addTypeEntry("car_avail", "always");
        p.addTypeEntry("job", "teacher");

        v.addTypeEntry("Type", "Car");
        v.addTypeEntry("Model", "Audi");
        v.addTypeEntry("Characteristics", "Small, Black");

        f.addTypeEntry("visibility", "public");
        f.addTypeEntry("type", "entertainment, movie_theatre");
    }

    @Test
    void testAddTypeEntryForType() {
        Set<String> nLocationValues = n.getTypes().getValues("Location");
        Set<String> nNoneValues = n.getTypes().getValues("none");

        Set<String> lModesValues = l.getTypes().getValues("modes");
        Set<String> lTypeValues = l.getTypes().getValues("type");
        Set<String> lNoneValues = l.getTypes().getValues("none");

        Set<String> pSexValues = p.getTypes().getValues("sex");
        Set<String> pLicenseValues = p.getTypes().getValues("license");
        Set<String> pCarAvailValues = p.getTypes().getValues("car_avail");
        Set<String> pJobValues = p.getTypes().getValues("job");
        Set<String> pNoneValues = p.getTypes().getValues("none");

        Set<String> vTypeValues = v.getTypes().getValues("Type");
        Set<String> vModelValues = v.getTypes().getValues("Model");
        Set<String> vCharacteristicsValues = v.getTypes().getValues("Characteristics");
        Set<String> vNoneValues = v.getTypes().getValues("none");

        Set<String> fVisibilityValues = f.getTypes().getValues("visibility");
        Set<String> fTypeValues = f.getTypes().getValues("type");
        Set<String> fNoneValues = f.getTypes().getValues("none");

        assertEquals(1, nLocationValues.size());
        assertEquals(0, nNoneValues.size());

        assertEquals(3, lModesValues.size());
        assertEquals(1, lTypeValues.size());
        assertEquals(0, lNoneValues.size());

        assertEquals(1, pSexValues.size());
        assertEquals(1, pLicenseValues.size());
        assertEquals(1, pCarAvailValues.size());
        assertEquals(1, pJobValues.size());
        assertEquals(0, pNoneValues.size());

        assertEquals(1, vTypeValues.size());
        assertEquals(1, vModelValues.size());
        assertEquals(2, vCharacteristicsValues.size());
        assertEquals(0, vNoneValues.size());

        assertEquals(1, fVisibilityValues.size());
        assertEquals(2, fTypeValues.size());
        assertEquals(0, fNoneValues.size());

        assertTrue(nLocationValues.contains("Ord"));

        assertTrue(lModesValues.containsAll(Set.of("car", "truck", "motorcycle")));
        assertTrue(lTypeValues.contains("highway"));

        assertTrue(pSexValues.contains("M"));
        assertTrue(pLicenseValues.contains("yes"));
        assertTrue(pCarAvailValues.contains("always"));
        assertTrue(pJobValues.contains("teacher"));

        assertTrue(vTypeValues.contains("Car"));
        assertTrue(vModelValues.contains("Audi"));
        assertTrue(vCharacteristicsValues.containsAll(Set.of("Small", "Black")));

        assertTrue(fVisibilityValues.contains("public"));
        assertTrue(fTypeValues.containsAll(Set.of("entertainment", "movie_theatre")));
    }

    @Test
    void testAddTypeEntryForTypeCollector() {
        Set<Class<? extends SimModel>> classes = MSTypeCollector.getClasses();

        Set<String> nodeTypes = MSTypeCollector.getTypes(MSNode.class);
        Set<String> linkTypes = MSTypeCollector.getTypes(MSLink.class);
        Set<String> personTypes = MSTypeCollector.getTypes(MSPerson.class);
        Set<String> vehicleTypes = MSTypeCollector.getTypes(MSVehicle.class);
        Set<String> facilityTypes = MSTypeCollector.getTypes(MSFacility.class);

        Set<String> linkModesValues = MSTypeCollector.getValues(MSLink.class, "modes");
        Set<String> linkTypeValues = MSTypeCollector.getValues(MSLink.class, "type");

        Set<SimModel> nodeOrdLocation = MSTypeCollector.getInstances(MSNode.class, "Location", "Ord");

        assertEquals(5, classes.size());

        assertEquals(1, nodeTypes.size());
        assertEquals(2, linkTypes.size());
        assertEquals(4, personTypes.size());
        assertEquals(3, vehicleTypes.size());
        assertEquals(2, facilityTypes.size());

        assertEquals(3, linkModesValues.size());
        assertEquals(1, linkTypeValues.size());

        assertEquals(3, nodeOrdLocation.size());

        assertTrue(classes.containsAll(Set.of(MSNode.class, MSLink.class, MSPerson.class, MSVehicle.class, MSFacility.class)));

        assertTrue(nodeTypes.containsAll(Set.of("Location")));
        assertTrue(linkTypes.containsAll(Set.of("modes", "type")));
        assertTrue(personTypes.containsAll(Set.of("sex", "license", "car_avail", "job")));
        assertTrue(vehicleTypes.containsAll(Set.of("Type", "Model", "Characteristics")));
        assertTrue(facilityTypes.containsAll(Set.of("visibility", "type")));

        assertTrue(linkModesValues.containsAll(Set.of("car", "truck", "motorcycle")));
        assertTrue(linkTypeValues.containsAll(Set.of("highway")));

        assertTrue(nodeOrdLocation.containsAll(Set.of(n, n1, n2)));
    }

    @Test
    void testIsPresent() {
        assertTrue(n.getTypes().isPresent("Location"));
        assertTrue(n1.getTypes().isPresent("Location"));
        assertTrue(n2.getTypes().isPresent("Location"));

        assertTrue(l.getTypes().isPresent("modes"));
        assertTrue(l.getTypes().isPresent("type"));

        assertTrue(p.getTypes().isPresent("sex"));
        assertTrue(p.getTypes().isPresent("license"));
        assertTrue(p.getTypes().isPresent("car_avail"));
        assertTrue(p.getTypes().isPresent("job"));

        assertTrue(v.getTypes().isPresent("Type"));
        assertTrue(v.getTypes().isPresent("Model"));
        assertTrue(v.getTypes().isPresent("Characteristics"));

        assertTrue(f.getTypes().isPresent("visibility"));
        assertTrue(f.getTypes().isPresent("type"));

        assertTrue(n.getTypes().isPresent("Location", "Ord"));
        assertTrue(n1.getTypes().isPresent("Location", "Ord"));
        assertTrue(n2.getTypes().isPresent("Location", "Ord"));

        assertTrue(l.getTypes().isPresent("modes", Set.of("car", "truck", "motorcycle")));
        assertTrue(l.getTypes().isPresent("type", "highway"));

        assertTrue(p.getTypes().isPresent("sex", "M"));
        assertTrue(p.getTypes().isPresent("license", "yes"));
        assertTrue(p.getTypes().isPresent("car_avail", "always"));
        assertTrue(p.getTypes().isPresent("job", "teacher"));

        assertTrue(v.getTypes().isPresent("Type", "Car"));
        assertTrue(v.getTypes().isPresent("Model", "Audi"));
        assertTrue(v.getTypes().isPresent("Characteristics", Set.of("Small", "Black")));

        assertTrue(f.getTypes().isPresent("visibility", "public"));
        assertTrue(f.getTypes().isPresent("type", Set.of("entertainment", "movie_theatre")));

        assertFalse(l.getTypes().isPresent("useless"));
        assertFalse(l.getTypes().isPresent("type", "unexpected"));
    }
}