package AbmModel;

import MatsimModel.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class VehicleTest {

    Vehicle v;

    Person p;

    Node n0, n1;

    Link l0, l1;

    PersonEntersVehicle e1, e5, e9;
    VehicleEntersLink e2, e6;
    VehicleLeavesLink e3, e7;
    PersonLeavesVehicle e0, e4, e8;

    @BeforeEach
    void setUp() {
        v = new MSVehicle(0, new ArrayList<>());
        p = new MSPerson(0, "M", 20, "", "", "", new ArrayList<>());
        n0 = new MSNode(0, 24.6, 38.8, "");
        n1 = new MSNode(1, 75.2, 2.4, "");
        l0 = new MSLink(3.0, 1.0, 100.0, "", "", new HashSet<>(), 0, 0, n0, 1, n1, Math.sqrt(Math.pow((75.2 - 24.6), 2) + Math.pow((2.4 - 38.8), 2)));
        l1 = new MSLink(3.0, 1.0, 100.0, "", "", new HashSet<>(), 1, 1, n1, 0, n0, Math.sqrt(Math.pow((75.2 - 24.6), 2) + Math.pow((2.4 - 38.8), 2)));

        e0 = new MSPersonLeavesVehicle(0, 0.0, "PersonLeavesVehicle", 24.6, 38.8, 0, 0, -1, -1, new HashMap<>(), v, p);
        e1 = new MSPersonEntersVehicle(1, 10.0, "PersonEntersVehicle", 24.6, 38.8, 0, 0, -1, -1, new HashMap<>(), v, p);
        e2 = new MSVehicleEntersLink(2, 20.0, "entered link", 24.6, 38.8, -1, 0, 0, -1, new HashMap<>(), v, l0);
        e3 = new MSVehicleLeavesLink(3, 30.0, "left link", 75.2, 2.4, -1, 0, 0, -1, new HashMap<>(), v, l0);
        e4 = new MSPersonLeavesVehicle(4, 40.0, "PersonLeavesVehicle", 75.2, 2.4, 0, 0, -1, -1, new HashMap<>(), v, p);
        e5 = new MSPersonEntersVehicle(5, 50.0, "PersonEntersVehicle", 75.2, 2.4, 0, 0, -1, -1, new HashMap<>(), v, p);
        e6 = new MSVehicleEntersLink(6, 60.0, "entered link", 75.2, 2.4, -1, 0, 0, -1, new HashMap<>(), v, l0);
        e7 = new MSVehicleLeavesLink(7, 70.0, "left link", 24.6, 38.8, -1, 0, 0, -1, new HashMap<>(), v, l0);
        e8 = new MSPersonLeavesVehicle(8, 80.0, "PersonLeavesVehicle", 24.6, 38.8, 0, 0, -1, -1, new HashMap<>(), v, p);
        e9 = new MSPersonEntersVehicle(9, 90.0, "PersonEntersVehicle", 24.6, 38.8, 0, 0, -1, -1, new HashMap<>(), v, p);

        v.setRelatedEvents(List.of(e0, e1, e2, e3, e4, e5, e6, e7, e8, e9));
        p.setRelatedEvents(List.of(e0, e1, e4, e5, e8, e9));
    }

    @Test
    void nearestLinkEntering() {
        Event nearest0, nearest1, nearest2;
        nearest0 = v.getNearestLinkEntering(e1.getTime());
        nearest1 = v.getNearestLinkEntering(e5.getTime());
        nearest2 = v.getNearestLinkEntering(e9.getTime());

        assertEquals(2, nearest0.getId());
        assertEquals(6, nearest1.getId());

        assertNull(nearest2);
    }

    @Test
    void latestLinkLeaving() {
        Event latest0, latest1, latest2;
        latest0 = v.getLatestLinkLeaving(e4.getTime());
        latest1 = v.getLatestLinkLeaving(e8.getTime());
        latest2 = v.getLatestLinkLeaving(e0.getTime());

        assertEquals(3, latest0.getId());
        assertEquals(7, latest1.getId());

        assertNull(latest2);
    }
}
