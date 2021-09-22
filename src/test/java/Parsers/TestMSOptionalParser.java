package Parsers;

import AbmModel.MandatorySimModel;
import AbmModel.Facility;
import AbmModel.OptionalSimModel;
import AbmModel.Scenario;
import AbmParser.ParserConfigurer;
import MatsimModel.*;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.fail;

public class TestMSOptionalParser {
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
    void testFacilitySize() {
        assertEquals(6, scenario.getOptionalSimModels().get(MSFacility.class).size());
    }

    @Test
    void testParsingFacilityId() {
        Set<OptionalSimModel> nodeSet = scenario.getOptionalSimModels().get(MSFacility.class);
        for (OptionalSimModel n: nodeSet) {
            if (((Facility)n).getId() == 1)
                return;
        }
        fail();
    }

    @Test
    void testParsingFacilityX() {
        Set<OptionalSimModel> nodeSet = scenario.getOptionalSimModels().get(MSFacility.class);
        for (OptionalSimModel n: nodeSet) {
            if (((Facility)n).getX() != 0)
                return;
        }
        fail();
    }

    @Test
    void testParsingFacilityY() {
        Set<OptionalSimModel> nodeSet = scenario.getOptionalSimModels().get(MSFacility.class);
        for (OptionalSimModel n: nodeSet) {
            if (((Facility)n).getY() != 0)
                return;
        }
        fail();
    }
}