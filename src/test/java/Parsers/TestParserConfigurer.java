package Parsers;

import AbmModel.Scenario;
import AbmParser.ParserConfigurer;
import AbmParser.ParserConfigurerException;
import org.junit.jupiter.api.Test;

import static MainGUI.Main.startParser;
import static org.junit.jupiter.api.Assertions.*;

public class TestParserConfigurer {

    ParserConfigurer parserConfigurer;
    Scenario scenario;
    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        parserConfigurer =  new ParserConfigurer("./input/ParserConfig.json", false);
        scenario = parserConfigurer.parse();

    }

    @org.junit.jupiter.api.Test
    void testScenarioName() {
        assertEquals("Processed EquilExtended", scenario.getName());
    }

    @org.junit.jupiter.api.Test
    void testScenarioUnit() {
        assertEquals("Metric", scenario.getUnit());
    }

    @org.junit.jupiter.api.Test
    void testScenarioMandatoryAttr() {
        assertNotNull(scenario.getMandatorySimModels());
    }

    @org.junit.jupiter.api.Test
    void testScenarioOptionalAttr() {
        assertNotNull(scenario.getOptionalSimModels());
    }

    @org.junit.jupiter.api.Test
    void testGenerateTemplateConfig() {
        assertTrue(ParserConfigurer.generateTemplateConfig("./input"));
    }

    @Test
    void testGetPreviewDescription() {
        assertEquals("Scenario found: EquilExtended at ./input, found mandatory modules: 5, found optional modules: 1",
                parserConfigurer.getPreviewDescription());
    }
}
