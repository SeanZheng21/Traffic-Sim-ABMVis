package AbmModel;

import MatsimModel.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PersonTest {

    Vehicle v;

    Person p;

    Node n0, n1;

    Link l0, l1;

    Facility f0, f1;

    ActStart e0, e6, e12;
    ActEnd e1, e7, e13;
    PersonEntersVehicle e2, e8;
    VehicleEntersLink e3, e9;
    VehicleLeavesLink e4, e10;
    PersonLeavesVehicle e5, e11;

    @BeforeEach
    void setUp() {
        v = new MSVehicle(0, new ArrayList<>());
        p = new MSPerson(0, "M", 20, "", "", "", new ArrayList<>());
        n0 = new MSNode(0, 24.6, 38.8, "");
        n1 = new MSNode(1, 75.2, 2.4, "");
        f0 = new MSFacility(0, 24.6, 38.8, 0);
        f0 = new MSFacility(1, 75.2, 2.4, 1);
        l0 = new MSLink(3.0, 1.0, 100.0, "", "", new HashSet<>(), 0, 0, n0, 1, n1, Math.sqrt(Math.pow((75.2 - 24.6), 2) + Math.pow((2.4 - 38.8), 2)));
        l1 = new MSLink(3.0, 1.0, 100.0, "", "", new HashSet<>(), 1, 1, n1, 0, n0, Math.sqrt(Math.pow((75.2 - 24.6), 2) + Math.pow((2.4 - 38.8), 2)));

        e0 = new MSActStart(0, 0.0, "actstart", 24.6, 38.8, 0, -1, -1, 0, new HashMap<>(), p, f0);
        e1 = new MSActEnd(1, 10.0, "actend", 24.6, 38.8, 0, -1, -1, 0, new HashMap<>(), p, f0);
        e2 = new MSPersonEntersVehicle(2, 20.0, "PersonEntersVehicle", 24.6, 38.8, 0, 0, -1, -1, new HashMap<>(), v, p);
        e3 = new MSVehicleEntersLink(3, 30.0, "entered link", 24.6, 38.8, -1, 0, 0, -1, new HashMap<>(), v, l0);
        e4 = new MSVehicleLeavesLink(4, 40.0, "left link", 75.2, 2.4, -1, 0, 0, -1, new HashMap<>(), v, l0);
        e5 = new MSPersonLeavesVehicle(5, 50.0, "PersonLeavesVehicle", 75.2, 2.4, 0, 0, -1, -1, new HashMap<>(), v, p);
        e6 = new MSActStart(6, 60.0, "actstart", 75.2, 2.4, 0, -1, -1, 1, new HashMap<>(), p, f1);
        e7 = new MSActEnd(7, 70.0, "actend", 75.2, 2.4, 0, -1, -1, 1, new HashMap<>(), p, f1);
        e8 = new MSPersonEntersVehicle(8, 80.0, "PersonEntersVehicle", 75.2, 2.4, 0, 0, -1, -1, new HashMap<>(), v, p);
        e9 = new MSVehicleEntersLink(9, 90.0, "entered link", 75.2, 2.4, -1, 0, 0, -1, new HashMap<>(), v, l0);
        e10 = new MSVehicleLeavesLink(10, 100.0, "left link", 24.6, 38.8, -1, 0, 0, -1, new HashMap<>(), v, l0);
        e11 = new MSPersonLeavesVehicle(11, 110.0, "PersonLeavesVehicle", 24.6, 38.8, 0, 0, -1, -1, new HashMap<>(), v, p);
        e12 = new MSActStart(12, 120.0, "actstart", 24.6, 38.8, 0, -1, -1, 0, new HashMap<>(), p, f0);
        e13 = new MSActEnd(13, 130.0, "actend", 24.6, 38.8, 0, -1, -1, 0, new HashMap<>(), p, f0);

        v.setRelatedEvents(List.of(e2, e3, e4, e5, e8, e9, e10, e11));
        p.setRelatedEvents(List.of(e0, e1, e2, e5, e6, e7, e8, e11, e12, e13));
    }

    @Test
    void nearestVehicleEntering() {
        Event nearest0, nearest1, nearest2;
        nearest0 = p.getNearestVehicleEntering(e1.getTime());
        nearest1 = p.getNearestVehicleEntering(e7.getTime());
        nearest2 = p.getNearestVehicleEntering(e13.getTime());

        assertEquals(2, nearest0.getId());
        assertEquals(8, nearest1.getId());

        assertNull(nearest2);
    }

    @Test
    void latestVehicleLeaving() {
        Event latest0, latest1, latest2;
        latest0 = p.getLatestVehicleLeaving(e6.getTime());
        latest1 = p.getLatestVehicleLeaving(e12.getTime());
        latest2 = p.getLatestVehicleLeaving(e0.getTime());

        assertEquals(5, latest0.getId());
        assertEquals(11, latest1.getId());

        assertNull(latest2);
    }
}
