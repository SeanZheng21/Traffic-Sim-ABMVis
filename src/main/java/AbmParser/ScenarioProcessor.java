package AbmParser;

import AbmModel.Scenario;

/**
 * Defines the concept of a scenario processor.
 * All acceptable scenario processor implementations should extends this abstract class.
 * A parser is instantiated by the parser configurer according to the configuration file.
 * scenario processor should be accessed by the parser configurer and only
 * A user should never instantiate a scenario processor manually
 * A scenario processor should be created by a configurer according to its configuration instructions
 */
public interface ScenarioProcessor {
    /**
     * Post-process a scenario when the auto parser lanches are finished.
     * The user can perform customized modifications on a scenario level.
     * Impl allows override on this method and get called by the parser configurer
     * @param input input scenario with instances from parsers
     * @return The processed scenario
     */
    Scenario process(Scenario input);
}
