package AbmParser;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Utility class for ParserConfigurer to load customized parser implementation jar artifacts
 * Called by parser configurers, generate
 */
public class ParserLoader {
    /**
     * The target of the loader, can be mandatory/optional parser, and scenario processor
     */
    private LoaderTarget target;
    /**
     * The caller of new instance
     */
    private final ParserConfigurer caller;
    /**
     * The path to JAR archive file
     */
    private String jarPath;
    /**
     * The name of the class to create in JAR
     */
    private String className;

    /**
     * Creates a parser loader at:
     * @param configurer caller attr
     * @param target target attr
     * @param jarPath jarPath attr
     * @param className calssName attr
     */
    public ParserLoader(ParserConfigurer configurer, LoaderTarget target, String jarPath, String className) {
        this.target = target;
        caller = configurer;
        this.jarPath = jarPath;
        this.className = className;
    }

    /**
     * Generate a mandatory parser form the given info
     * @return the mandatory parser instance if the target is correct, null if not
     * @throws ParserConfigurerException if jar url is incorrect or the impl class can't be found
     */
    public MandatoryParser loadMandatoryParser() throws ParserConfigurerException {
        if (target != LoaderTarget.MANDATORY_PARSER)
            return null;
        else {
            try {
                URL[] urls = new URL[] {new URL(jarPath)};
                URLClassLoader loader = new URLClassLoader(urls, caller.getClass().getClassLoader());
                return (MandatoryParser) Class.forName(className, true, loader).newInstance();
            } catch (MalformedURLException e) {
                throw new ParserConfigurerException("Malformed URL for mandatory parser: " + e.getMessage());
            } catch (IllegalAccessException | InstantiationException | ClassNotFoundException e) {
                throw new ParserConfigurerException("Class not found for mandatory parser: " + e.getMessage());
            }
        }
    }

    /**
     * Generate an optional parser form the given info
     * @return the optional parser instance if the target is correct, null if not
     * @throws ParserConfigurerException if jar url is incorrect or the impl class can't be found
     */
    public OptionalParser loadOptionalParser() throws ParserConfigurerException {
        if (target != LoaderTarget.OPTIONAL_PARSER)
            return null;
        else {
            try {
                URL[] urls = new URL[] {new URL(jarPath)};
                URLClassLoader loader = new URLClassLoader(urls, caller.getClass().getClassLoader());
                return (OptionalParser) Class.forName(className, true, loader).newInstance();
            } catch (MalformedURLException e) {
                throw new ParserConfigurerException("Malformed URL for optional parser: " + e.getMessage());
            } catch (IllegalAccessException | InstantiationException | ClassNotFoundException e) {
                throw new ParserConfigurerException("Class not found for optional parser: " + e.getMessage());
            }
        }
    }

    /**
     * Generate a scenario processor form the given info
     * @return the scenario processor instance if the target is correct, null if not
     * @throws ParserConfigurerException if jar url is incorrect or the impl class can't be found
     */
    public ScenarioProcessor loadScenarioProcessor() throws ParserConfigurerException {
        if (target != LoaderTarget.SCENARIO_PROCESSOR)
            return null;
        else {
            try {
                URL[] urls = new URL[] {new URL(jarPath)};
                URLClassLoader loader = new URLClassLoader(urls, caller.getClass().getClassLoader());
                return (ScenarioProcessor) Class.forName(className, true, loader).newInstance();
            } catch (MalformedURLException e) {
                throw new ParserConfigurerException("Malformed URL for scenario processor: " + e.getMessage());
            } catch (IllegalAccessException | InstantiationException | ClassNotFoundException e) {
                throw new ParserConfigurerException("Class not found for scenario processor: " + e.getMessage());
            }
        }
    }

    public LoaderTarget getTarget() {
        return target;
    }

    public void setTarget(LoaderTarget target) {
        this.target = target;
    }

    public String getJarPath() {
        return jarPath;
    }

    public void setJarPath(String jarPath) {
        this.jarPath = jarPath;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}

/**
 * Types of targets for a parser loader,
 * can be mandatory/optional parser, and scenario processor
 */
enum LoaderTarget {
    /**
     * Target for the MandatoryParser
     */
    MANDATORY_PARSER,
    /**
     * Target for the OptionalParser
     */
    OPTIONAL_PARSER,
    /**
     * Target for the ScenarioProcessor
     */
    SCENARIO_PROCESSOR,
}
