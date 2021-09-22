package UIAbmModel;

import AbmModel.*;
import MatsimModel.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UIScenarioTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void ScenarioWithNode(){
        Scenario s = new MSScenario("Test", "m");
        Node n1 = new MSNode(1, 1, 100, "test");
        Node n2 = new MSNode(2, 1, 100, "test");
        Node n3 = new MSNode(3, 1, 100, "test");
        s.getNetwork().addNode(n1);
        s.getNetwork().addNode(n2);
        s.getNetwork().addNode(n3);
        UIScenario scenario = new UIScenario(s);

        assertEquals(3, scenario.getUINodesSize());
    }

    @Test
    void ScenarioWithVehicle(){
        Scenario s = new MSScenario("Test", "m");
        Vehicle v1 = new MSVehicle(1, new ArrayList<>());
        Vehicle v2 = new MSVehicle(2, new ArrayList<>());
        Vehicle v3 = new MSVehicle(3, new ArrayList<>());
        s.addVehicle(v1);
        s.addVehicle(v2);
        s.addVehicle(v3);
        UIScenario scenario = new UIScenario(s);

        assertEquals(3, scenario.getUIVehicleSize());
        assertEquals(3, scenario.getUIInstanceperIDSize());
    }

    @Test
    void ScenarioWithPerson(){
        Scenario s = new MSScenario("Test", "m");
        Person p1 = new MSPerson(1,"M",24,"", "", "", new ArrayList<>());
        Person p2 = new MSPerson(2,"M",24,"", "", "", new ArrayList<>());
        Person p3 = new MSPerson(3,"M",24,"", "", "", new ArrayList<>());
        s.addPerson(p1);
        s.addPerson(p2);
        s.addPerson(p3);
        UIScenario scenario = new UIScenario(s);

        assertEquals(3, scenario.getUIPersonSize());
        assertEquals(3, scenario.getUIInstanceperIDSize());
    }

    @Test
    void ScenarioWithEvent(){
        
        Scenario s = new MSScenario("Test", "m");
        Facility f1 = new MSFacility(1, 100,100, 1);
        Facility f2 = new MSFacility(2, 0,100, 2);
        Facility f3 = new MSFacility(3, 0,100, 3);
        s.addFacility(f1);
        s.addFacility(f2);
        s.addFacility(f3);

        Node n1 = new MSNode(1, 0, 0, "test");
        Node n2 = new MSNode(2, 100, 100, "test");
        Node n3 = new MSNode(3, 0, 100, "test");
        s.getNetwork().addNode(n1);
        s.getNetwork().addNode(n2);
        s.getNetwork().addNode(n3);

        Link l1 = new MSLink(0,1,50,"","", new HashSet<>(),1,1,n1,2,n2,1000);
        Link l2 = new MSLink(0,1,50,"","", new HashSet<>(),2,2,n2,3,n3,1000);
        Link l3 = new MSLink(0,1,50,"","", new HashSet<>(),3,3,n3,1,n1,1000);
        s.getNetwork().addLink(l1);
        s.getNetwork().addLink(l2);
        s.getNetwork().addLink(l3);

        Person p1 = new MSPerson(1,"M",24,"", "", "", new ArrayList<>());
        Person p2 = new MSPerson(2,"M",24,"", "", "", new ArrayList<>());
        Person p3 = new MSPerson(3,"M",24,"", "", "", new ArrayList<>());
        s.addPerson(p1);
        s.addPerson(p2);
        s.addPerson(p3);

        Vehicle v1 = new MSVehicle(1, new ArrayList<>());
        Vehicle v2 = new MSVehicle(2, new ArrayList<>());
        Vehicle v3 = new MSVehicle(3, new ArrayList<>());
        s.addVehicle(v1);
        s.addVehicle(v2);
        s.addVehicle(v3);

        MSPersonEntersVehicle e1 = new MSPersonEntersVehicle(1, 0, "", 0,0,1, 1, -1, -1, new HashMap<>(), v1, p1);
        MSPersonEntersVehicle e2 = new MSPersonEntersVehicle(2, 0, "", 0,0,2, 1, -1, -1, new HashMap<>(), v1, p2);
        MSVehicleEntersLink e3 = new MSVehicleEntersLink(3, 10, "", 0,0,-1, 1, 1, -1, new HashMap<>(), v1, l1);
        MSVehicleLeavesLink e4 = new MSVehicleLeavesLink(4, 100, "", 100,100,-1, 1, 1, -1, new HashMap<>(), v1, l1);
        MSPersonLeavesVehicle e5 = new MSPersonLeavesVehicle(5, 110, "", 100,100,1, 1, -1, -1, new HashMap<>(), v1, p1);
        MSPersonLeavesVehicle e6 = new MSPersonLeavesVehicle(6, 110, "", 100,100,2, 1, -1, -1, new HashMap<>(), v1, p2);
        MSActStart e7 = new MSActStart(7, 120, "", 100,100,2, -1, -1, 1, new HashMap<>(), p2, f1);
        MSActEnd e8 = new MSActEnd(8, 200, "", 100,100,2, -1, -1, 1, new HashMap<>(), p2, f1);
        s.addEvent(e1);
        s.addEvent(e2);
        s.addEvent(e3);
        s.addEvent(e4);
        s.addEvent(e5);
        s.addEvent(e6);
        s.addEvent(e7);
        s.addEvent(e8);

        UIScenario scenario = new UIScenario(s);

        assertEquals(8, scenario.getUIEventSize());
        assertEquals(23, scenario.getUIInstanceperIDSize());


    }

    @Test
    void ScenarioWithLink(){
        Scenario s = new MSScenario("Test", "m");
        Node n1 = new MSNode(1, 1, 100, "test");
        Node n2 = new MSNode(2, 1, 100, "test");
        Node n3 = new MSNode(3, 1, 100, "test");
        s.getNetwork().addNode(n1);
        s.getNetwork().addNode(n2);
        s.getNetwork().addNode(n3);

        Link l1 = new MSLink(0,1,50,"","", new HashSet<>(),1,1,n1,2,n2,1000);
        Link l2 = new MSLink(0,1,50,"","", new HashSet<>(),2,2,n2,3,n3,1000);
        Link l3 = new MSLink(0,1,50,"","", new HashSet<>(),3,3,n3,1,n1,1000);
        s.getNetwork().addLink(l1);
        s.getNetwork().addLink(l2);
        s.getNetwork().addLink(l3);


        UIScenario scenario = new UIScenario(s);

        assertEquals(3, scenario.getUILinkSize());
        assertEquals(6, scenario.getUIInstanceperIDSize());
    }

    @Test
    void ScenarioWithFacility(){
        Scenario s = new MSScenario("Test", "m");
        Facility f1 = new MSFacility(1, 0,100, 0);
        Facility f2 = new MSFacility(2, 0,100, 0);
        Facility f3 = new MSFacility(3, 0,100, 0);
        s.addFacility(f1);
        s.addFacility(f2);
        s.addFacility(f3);
        UIScenario scenario = new UIScenario(s);

        assertEquals(3, scenario.getUIFacilitySize());
        assertEquals(3, scenario.getUIInstanceperIDSize());
    }

    @Test
    void goTo() {
        Scenario s = new MSScenario("Test", "m");
        Facility f1 = new MSFacility(1, 100,100, 1);
        Facility f2 = new MSFacility(2, 0,100, 2);
        Facility f3 = new MSFacility(3, 0,100, 3);
        s.addFacility(f1); s.addFacility(f2); s.addFacility(f3);

        Node n1 = new MSNode(1, 0, 0, "test");
        Node n2 = new MSNode(2, 100, 100, "test");
        Node n3 = new MSNode(3, 0, 100, "test");
        s.getNetwork().addNode(n1); s.getNetwork().addNode(n2); s.getNetwork().addNode(n3);

        Link l1 = new MSLink(0,1,50,"","", new HashSet<>(),1,1,n1,2,n2,1000);
        Link l2 = new MSLink(0,1,50,"","", new HashSet<>(),2,2,n2,3,n3,1000);
        Link l3 = new MSLink(0,1,50,"","", new HashSet<>(),3,3,n3,1,n1,1000);
        s.getNetwork().addLink(l1); s.getNetwork().addLink(l2); s.getNetwork().addLink(l3);

        Person p1 = new MSPerson(1,"M",24,"", "", "", new ArrayList<>());
        Person p2 = new MSPerson(2,"M",24,"", "", "", new ArrayList<>());
        Person p3 = new MSPerson(3,"M",24,"", "", "", new ArrayList<>());
        s.addPerson(p1); s.addPerson(p2); s.addPerson(p3);

        Vehicle v1 = new MSVehicle(1, new ArrayList<>());
        Vehicle v2 = new MSVehicle(2, new ArrayList<>());
        Vehicle v3 = new MSVehicle(3, new ArrayList<>());
        s.addVehicle(v1); s.addVehicle(v2); s.addVehicle(v3);

        MSPersonEntersVehicle e1 = new MSPersonEntersVehicle(1, 1, "PersonEntersVehicle", 0,0,1, 1, -1, -1, new HashMap<>(), v1, p1);
        MSPersonEntersVehicle e2 = new MSPersonEntersVehicle(2, 1, "PersonEntersVehicle", 0,0,2, 1, -1, -1, new HashMap<>(), v1, p2);
        MSVehicleEntersLink e3 = new MSVehicleEntersLink(3, 10, "entered link", 0,0,-1, 1, 1, -1, new HashMap<>(), v1, l1);
        MSVehicleLeavesLink e4 = new MSVehicleLeavesLink(4, 100, "left link", 100,100,-1, 1, 1, -1, new HashMap<>(), v1, l1);
        MSPersonLeavesVehicle e5 = new MSPersonLeavesVehicle(5, 110, "PersonLeavesVehicle", 100,100,1, 1, -1, -1, new HashMap<>(), v1, p1);
        MSPersonLeavesVehicle e6 = new MSPersonLeavesVehicle(6, 110, "PersonLeavesVehicle", 100,100,2, 1, -1, -1, new HashMap<>(), v1, p2);
        MSActStart e7 = new MSActStart(7, 120, "actstart", 100,100,2, -1, -1, 1, new HashMap<>(), p2, f1);
        MSActEnd e8 = new MSActEnd(8, 200, "actend", 100,100,2, -1, -1, 1, new HashMap<>(), p2, f1);
        s.addEvent(e1); s.addEvent(e2); s.addEvent(e3); s.addEvent(e4);
        s.addEvent(e5); s.addEvent(e6); s.addEvent(e7); s.addEvent(e8);

        List<Event> v1Events = v1.getRelatedEvents();
        List<Event> p1Events = p1.getRelatedEvents();
        List<Event> p2Events = p2.getRelatedEvents();

        v1Events.add(e1); v1Events.add(e2); v1Events.add(e3); v1Events.add(e4); v1Events.add(e5); v1Events.add(e6);
        p1Events.add(e1); p1Events.add(e5);
        p2Events.add(e2); p2Events.add(e6); p2Events.add(e7); p2Events.add(e8);

        v1.setRelatedEvents(v1Events);
        p1.setRelatedEvents(p1Events);
        p2.setRelatedEvents(p2Events);

        UIScenario scenario = new UIScenario(s);

        UIVehicle uiv1 = (UIVehicle) scenario.getInstancePerID(UIVehicle.class, 1);

        UIPerson uip1 = (UIPerson) scenario.getInstancePerID(UIPerson.class, 1);
        UIPerson uip2 = (UIPerson) scenario.getInstancePerID(UIPerson.class, 2);

        UIFacility uif1 = (UIFacility) scenario.getInstancePerID(UIFacility.class, 1);

        scenario.goTo(0.0);
        assertTrue(uip1.getVehicleOnBoard().isEmpty());
        assertTrue(uip2.getVehicleOnBoard().isEmpty());
        assertEquals(0, uif1.getInsidePersons().size());
        assertTrue(uip2.getFacilityLocation().isEmpty());

        assertEquals(0, uip1.getX(), 0.01);
        assertEquals(0, uip1.getY(), 0.01);

        scenario.goTo(55.0);

        assertEquals(2, uiv1.getOnBoardPersons().size());
        assertEquals(1, uip1.getVehicleOnBoard().get().getVehicle().getId());
        assertEquals(1, uip2.getVehicleOnBoard().get().getVehicle().getId());
        assertFalse(uip1.getVehicleOnBoard().isEmpty());
        assertFalse(uip2.getVehicleOnBoard().isEmpty());
        assertEquals(0, uif1.getInsidePersons().size());
        assertTrue(uip2.getFacilityLocation().isEmpty());

        assertEquals(50, uip1.getX(), 0.01);
        assertEquals(50, uip1.getY(), 0.01);
        assertEquals(50, uiv1.getX(), 0.01);
        assertEquals(50, uiv1.getY(), 0.01);

        scenario.goTo(160.0);

        assertEquals(0, uiv1.getOnBoardPersons().size());
        assertTrue(uip1.getVehicleOnBoard().isEmpty());
        assertTrue(uip2.getVehicleOnBoard().isEmpty());
        assertEquals(1, uif1.getInsidePersons().size());
        assertFalse(uip2.getFacilityLocation().isEmpty());
        assertEquals(1, uip2.getFacilityLocation().get().getFacility().getId());

        assertEquals(100, uip1.getX(), 0.01);
        assertEquals(100, uip1.getY(), 0.01);
        assertEquals(100, uip2.getX(), 0.01);
        assertEquals(100, uip2.getY(), 0.01);

        scenario.goTo(77.5);

        assertEquals(2, uiv1.getOnBoardPersons().size());
        assertEquals(1, uip1.getVehicleOnBoard().get().getVehicle().getId());
        assertEquals(1, uip2.getVehicleOnBoard().get().getVehicle().getId());
        assertFalse(uip1.getVehicleOnBoard().isEmpty());
        assertFalse(uip2.getVehicleOnBoard().isEmpty());
        assertEquals(0, uif1.getInsidePersons().size());
        assertTrue(uip2.getFacilityLocation().isEmpty());

        assertEquals(75, uip1.getX(), 0.01);
        assertEquals(75, uip1.getY(), 0.01);
        assertEquals(75, uiv1.getX(), 0.01);
        assertEquals(75, uiv1.getY(), 0.01);

    }
}