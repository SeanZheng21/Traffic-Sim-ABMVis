package Parsers;

import AbmModel.*;
import AbmParser.ParserConfigurer;
import AbmParser.ParserConfigurerException;
import MatsimModel.*;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class TestMSMandatoryParser {
    ParserConfigurer parserConfigurer;
    Scenario scenario;
    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        parserConfigurer =  new ParserConfigurer("./input/ParserConfig.json", false);
        scenario = parserConfigurer.parse();
    }
    @org.junit.jupiter.api.Test
    void testMandatoryMapKeys() {
        assertEquals(6, scenario.getMandatorySimModels().size());
        assertTrue(scenario.getMandatorySimModels().containsKey(MSPerson.class));
        assertTrue(scenario.getMandatorySimModels().containsKey(MSLink.class));
        assertTrue(scenario.getMandatorySimModels().containsKey(MSVehicle.class));
        assertTrue(scenario.getMandatorySimModels().containsKey(MSEvent.class));
        assertTrue(scenario.getMandatorySimModels().containsKey(MSNode.class));
    }

    @Test
    void testParsingPersonSize() {
        assertEquals(100, scenario.getMandatorySimModels().get(MSPerson.class).size());
    }

    @Test
    void testParsingPersonId() {
        Person person = (Person) scenario.getMandatorySimModels().get(MSPerson.class).stream().findFirst().get();
        assertTrue(person.getId() >= 0);
    }

    @Test
    void testParsingPersonSex() {
        Set<MandatorySimModel> personSet = scenario.getMandatorySimModels().get(MSPerson.class);
        for (MandatorySimModel p: personSet) {
            if (((Person)p).getSex().equals("m"))
                return;
        }
        fail();
    }

    @Test
    void testParsingPersonAge() {
        Set<MandatorySimModel> personSet = scenario.getMandatorySimModels().get(MSPerson.class);
        for (MandatorySimModel p: personSet) {
            if (((Person)p).getAge() == 18)
                return;
        }
        fail();
    }

    @Test
    void testParsingVehicleId() {
        Set<MandatorySimModel> vehicleSet = scenario.getMandatorySimModels().get(MSVehicle.class);
        for (MandatorySimModel v: vehicleSet) {
            if (((Vehicle)v).getId() == 66128)
                return;
        }
        fail();
    }

    @Test
    void testParsingVehicleSize() {
        assertEquals(24, scenario.getMandatorySimModels().get(MSVehicle.class).size());
    }

    @Test
    void testParsingNodeSize() {
        assertEquals(15, scenario.getMandatorySimModels().get(MSNode.class).size());
    }

    @Test
    void testParsingNodeId() {
        Set<MandatorySimModel> nodeSet = scenario.getMandatorySimModels().get(MSNode.class);
        for (MandatorySimModel n: nodeSet) {
            if (((Node)n).getId() == 10)
                return;
        }
        fail();
    }

    @Test
    void testParsingNodeX() {
        Set<MandatorySimModel> nodeSet = scenario.getMandatorySimModels().get(MSNode.class);
        for (MandatorySimModel n: nodeSet) {
            if (((Node)n).getX() == -4698)
                return;
        }
        fail();
    }

    @Test
    void testParsingNodeY() {
        Set<MandatorySimModel> nodeSet = scenario.getMandatorySimModels().get(MSNode.class);
        for (MandatorySimModel n: nodeSet) {
            if (((Node)n).getY() == -1711)
                return;
        }
        fail();
    }

    @Test
    void testParsingLinkSize() {
        assertEquals(23, scenario.getMandatorySimModels().get(MSLink.class).size());
    }

    @Test
    void testParsingLinkId() {
        Set<MandatorySimModel> linkSet = scenario.getMandatorySimModels().get(MSLink.class);
        for (MandatorySimModel l: linkSet) {
            if (((Link)l).getId() == 1)
                return;
        }
        fail();
    }

    @Test
    void testParsingLinkFrom() {
        Set<MandatorySimModel> linkSet = scenario.getMandatorySimModels().get(MSLink.class);
        for (MandatorySimModel l: linkSet) {
            if (((Link)l).getFrom() == 1)
                return;
        }
        fail();
    }
    @Test
    void testParsingLinkTo() {
        Set<MandatorySimModel> linkSet = scenario.getMandatorySimModels().get(MSLink.class);
        for (MandatorySimModel l: linkSet) {
            if (((Link)l).getTo() == 2)
                return;
        }
        fail();
    }

    @Test
    void testParsingLinkLength() {
        Set<MandatorySimModel> linkSet = scenario.getMandatorySimModels().get(MSLink.class);
        for (MandatorySimModel l: linkSet) {
            if (((Link)l).getLength() == 10000.00)
                return;
        }
        fail();
    }

    @Test
    void testParsingLinkCapacity() {
        Set<MandatorySimModel> linkSet = scenario.getMandatorySimModels().get(MSLink.class);
        for (MandatorySimModel l: linkSet) {
            if (((Link)l).getCapacity() == 36000)
                return;
        }
        fail();
    }

    @Test
    void testParsingLinkFreeSpeed() {
        Set<MandatorySimModel> linkSet = scenario.getMandatorySimModels().get(MSLink.class);
        for (MandatorySimModel l: linkSet) {
            if (((Link)l).getFreespeed() == 27.78)
                return;
        }
        fail();
    }

    @Test
    void testParsingLinkLanes() {
        Set<MandatorySimModel> linkSet = scenario.getMandatorySimModels().get(MSLink.class);
        for (MandatorySimModel l: linkSet) {
            if (((Link)l).getPermlanes() == 1)
                return;
        }
        fail();
    }

    @Test
    void testParsingEventSize() {
        assertEquals(34, scenario.getMandatorySimModels().get(MSEvent.class).size());
    }

    @Test
    void testParsingEventTime() {
        Set<MandatorySimModel> evtSet = scenario.getMandatorySimModels().get(MSEvent.class);
        for (MandatorySimModel l: evtSet) {
            if (((Event)l).getTime() == 21600.0)
                return;
        }
        fail();
    }

    @Test
    void testParsingEventType() {
        Set<MandatorySimModel> evtSet = scenario.getMandatorySimModels().get(MSEvent.class);
        for (MandatorySimModel l: evtSet) {
            if (((Event)l).getType().equals("leg"))
                return;
        }
        fail();
    }
}
