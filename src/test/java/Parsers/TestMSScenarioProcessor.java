package Parsers;

import AbmModel.*;
import AbmParser.ParserConfigurer;
import MatsimModel.*;
import UIAbmModel.RunnableEvent;
import UIAbmModel.UIPerson;
import UIAbmModel.UIScenario;
import UIAbmModel.UIVehicle;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static MainGUI.Main.startParser;
import static org.junit.jupiter.api.Assertions.*;

public class TestMSScenarioProcessor {
    ParserConfigurer parserConfigurer;
    Scenario scenario;
    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        parserConfigurer =  new ParserConfigurer("./input/ParserConfig.json", false);
        scenario = parserConfigurer.parse();

    }

    @org.junit.jupiter.api.Test
    void testMandatoryMapKeys() {
        assertEquals(1, scenario.getOptionalSimModels().size());
        assertTrue(scenario.getOptionalSimModels().containsKey(MSFacility.class));
    }

    @Test
    void testEventCoordinates() {

        MSActEnd ae4 = null;
        MSPersonEntersVehicle pev4 = null;
        MSVehicleEntersLink vel4 = null;

        MSVehicleLeavesLink vll4 = null;
        MSPersonLeavesVehicle plv4 = null;
        MSActStart ac4 = null;

        MSLink l3 = (MSLink) scenario.getNetwork().getLink(3);

        MSNode n31 = (MSNode) l3.getFromeObject();
        MSNode n32 = (MSNode) l3.getToObject();

        for(Event e : scenario.getEvents().values()) {
            if((e.getPerson() == 4 || e.getVehicle() == 4) && e.getTime() == 21600.0) {
                if(e.getType().equals("actend")) ae4 = (MSActEnd) e;
                if(e.getType().equals("PersonEntersVehicle")) pev4 = (MSPersonEntersVehicle) e;
                if(e.getType().equals("entered link")) vel4 = (MSVehicleEntersLink) e;
            } else if((e.getPerson() == 4 || e.getVehicle() == 4) && e.getTime() >= 21601.0) {
                if(e.getType().equals("left link")) vll4 = (MSVehicleLeavesLink) e;
                if(e.getType().equals("PersonLeavesVehicle")) plv4 = (MSPersonLeavesVehicle) e;
                if(e.getType().equals("actstart")) ac4 = (MSActStart) e;
            }
        }

        assertEquals(n31.getX(), ae4.getX(), 0.001);
        assertEquals(n31.getY(), ae4.getY(), 0.001);
        assertEquals(n31.getX(), pev4.getX(), 0.001);
        assertEquals(n31.getY(), pev4.getY(), 0.001);
        assertEquals(n31.getX(), vel4.getX(), 0.001);
        assertEquals(n31.getY(), vel4.getY(), 0.001);

        assertEquals(n32.getX(), vll4.getX(), 0.001);
        assertEquals(n32.getY(), vll4.getY(), 0.001);
        assertEquals(n32.getX(), plv4.getX(), 0.001);
        assertEquals(n32.getY(), plv4.getY(), 0.001);
        assertEquals(n32.getX(), ac4.getX(), 0.001);
        assertEquals(n32.getY(), ac4.getY(), 0.001);

    }

    @Test
    void testEventsCoordinatesOnRelativeLinkPosition() {
        MSVehicleEntersLink vel0 = null;
        MSVehicleLeavesLink vll0 = null;
        MSVehicleEntersLink vel1 = null;
        MSVehicleLeavesLink vll1 = null;
        MSVehicleEntersLink vel2 = null;
        MSVehicleLeavesLink vll2 = null;

        MSLink l7 = (MSLink) scenario.getNetwork().getLink(7);

        MSNode n7f = (MSNode) l7.getFromeObject();
        MSNode n7t = (MSNode) l7.getToObject();

        for(Event e : scenario.getEvents().values()) {
            if(e.getTime() >= 21800.0) {
                if(e.getType().equals("entered link")) vel0 = (MSVehicleEntersLink) e;
                if(e.getType().equals("left link")) vll2 = (MSVehicleLeavesLink) e;
                if(e.getType().equals("vehicle enters traffic") && e.getRelativePosition() == 0.34) vel1 = (MSVehicleEntersLink) e;
                if(e.getType().equals("vehicle enters traffic") && e.getRelativePosition() == 0.83) vel2 = (MSVehicleEntersLink) e;
                if(e.getType().equals("vehicle leaves traffic") && e.getRelativePosition() == 0.34) vll0 = (MSVehicleLeavesLink) e;
                if(e.getType().equals("vehicle leaves traffic") && e.getRelativePosition() == 0.83) vll1 = (MSVehicleLeavesLink) e;
            }
        }

        double relative0_34X = n7f.getX() + (n7t.getX() - n7f.getX()) * 0.34;
        double relative0_34Y = n7f.getY() + (n7t.getY() - n7f.getY()) * 0.34;
        double relative0_83X = n7f.getX() + (n7t.getX() - n7f.getX()) * 0.83;
        double relative0_83Y = n7f.getY() + (n7t.getY() - n7f.getY()) * 0.83;

        assertEquals(n7f.getX(), vel0.getX(), 0.001);
        assertEquals(n7f.getY(), vel0.getY(), 0.001);
        assertEquals(relative0_34X, vll0.getX(), 0.001);
        assertEquals(relative0_34Y, vll0.getY(), 0.001);
        assertEquals(relative0_34X, vel1.getX(), 0.001);
        assertEquals(relative0_34Y, vel1.getY(), 0.001);
        assertEquals(relative0_83X, vll1.getX(), 0.001);
        assertEquals(relative0_83Y, vll1.getY(), 0.001);
        assertEquals(relative0_83X, vel2.getX(), 0.001);
        assertEquals(relative0_83Y, vel2.getY(), 0.001);
        assertEquals(n7t.getX(), vll2.getX(), 0.001);
        assertEquals(n7t.getY(), vll2.getY(), 0.001);
    }

    @Test
    void testEventValidity() {
        for(Event e : scenario.getEvents().values()) {
            if(e.getType().equals("departure") || e.getType().equals("arrival") || e.getType().equals("leg")) {
                assertFalse(e.isValid());
            } else if((e.getTime() < 21600.0 || e.getTime() >= 21780.0) && e.getTime() < 21800.0) {
                assertFalse(e.isValid());
            } else {
                assertTrue(e.isValid());
            }
        }
    }

    @Test
    void testEventRelatedObjects() {
        for(Event e : scenario.getEvents().values()) {
            if(e instanceof TrafficEvent) {
                TrafficEvent te = (TrafficEvent) e;
                assertEquals(scenario.getVehicle(te.getVehicle()).getId(), te.getVehicleObject().getId());
                assertEquals(scenario.getNetwork().getLink(te.getLink()).getId(), te.getLinkObject().getId());
            } else if(e instanceof BoardingEvent) {
                BoardingEvent be = (BoardingEvent) e;
                assertEquals(scenario.getVehicle(be.getVehicle()).getId(), be.getVehicleObject().getId());
                assertEquals(scenario.getPerson(be.getPerson()).getId(), be.getPersonObject().getId());
            } else if(e instanceof ActEvent) {
                ActEvent ae = (ActEvent) e;
                assertEquals(scenario.getPerson(ae.getPerson()).getId(), ae.getPersonObject().getId());
                if(ae.getFacility() != -1) {
                    assertEquals(scenario.getFacility(ae.getFacility()).getId(), ae.getFacilityObject().getId());
                } else {
                    assertNull(ae.getFacilityObject());
                }
            }
        }
    }

    @Test
    void testPersonsRelatedEvents() {
        assertEquals(3, scenario.getPerson(1).getRelatedEvents().size());
        assertEquals(1, scenario.getPerson(2).getRelatedEvents().size());
        assertEquals(0, scenario.getPerson(3).getRelatedEvents().size());
        assertEquals(4, scenario.getPerson(4).getRelatedEvents().size());
        assertEquals(2, scenario.getPerson(5).getRelatedEvents().size());
        assertEquals(4, scenario.getPerson(6).getRelatedEvents().size());

        for(Person p : scenario.getPersons().values()) {
            for(Event e : p.getRelatedEvents()) {
                if(!(e instanceof BoardingEvent) && !(e instanceof ActEvent)) {
                    fail();
                }
            }
        }
    }

    @Test
    void testVehiclesRelatedEvents() {
        assertEquals(5, scenario.getVehicle(1).getRelatedEvents().size());
        assertEquals(6, scenario.getVehicle(2).getRelatedEvents().size());
        assertEquals(0, scenario.getVehicle(3).getRelatedEvents().size());
        assertEquals(5, scenario.getVehicle(4).getRelatedEvents().size());
        assertEquals(4, scenario.getVehicle(5).getRelatedEvents().size());
        assertEquals(2, scenario.getVehicle(6).getRelatedEvents().size());

        for(Vehicle v : scenario.getVehicles().values()) {
            for(Event e : v.getRelatedEvents()) {
                if(!(e instanceof LegEvent)) {
                    fail();
                }
            }
        }
    }

    @Test
    void testLinksNodes() {
        for(Link l : scenario.getNetwork().getLinks().values()) {
            assertNotNull(l.getFromeObject());
            assertNotNull(l.getToObject());
            assertEquals(l.getFrom(), l.getFromeObject().getId());
            assertEquals(l.getTo(), l.getToObject().getId());
        }
    }
}
