package MatsimParser;

import AbmModel.*;
import AbmParser.ScenarioProcessor;
import MatsimModel.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Implementation of the processor for the MatSim Model
 */
public class MSScenarioProcessor implements ScenarioProcessor {
    @Override
    public Scenario process(Scenario input) {
        input.setName("Processed " + input.getName());


        processEvent(input);
        processMSNetwork(input);
        processMSVehicle(input);
        processMSFacility(input);
        processMSPerson(input);
        processMSEventObjects(input);
        processMSEventCoordinate(input);
        return input;
    }

    /**
     * First processing step for the Events
     * The correct event class type is instantiated for each events translated from the parser
     * depending on the string type read in the xml file, then there are added to the dedicated
     * map in the scenario
     * @param input The current scenario issued by the parser
     */
    private void processEvent(Scenario input) {
        Set<MandatorySimModel> eventSet = new HashSet<>();
        input.addMandatoryModels(MSLegEvent.class, new HashSet<>());
        for(MandatorySimModel evt: input.getMandatorySimModels().get(MSEvent.class)) {
            if (evt instanceof MSEvent) {
                MSEvent msEvent = (MSEvent) evt;
                String type = msEvent.getType();
                switch (type) {
                    case "entered link":
                        eventSet.add(new MSVehicleEntersLink(msEvent));
                        break;
                    case "vehicle enters traffic":
                        MSVehicleEntersLink newEvent = new MSVehicleEntersLink(msEvent);
                        newEvent.setRelativePosition(msEvent.getRelativePosition());
                        eventSet.add(newEvent);
                        break;
                    case "left link":
                        eventSet.add(new MSVehicleLeavesLink(msEvent));
                        break;
                    case "vehicle leaves traffic":
                        MSVehicleLeavesLink newEvent2 = new MSVehicleLeavesLink(msEvent);
                        newEvent2.setRelativePosition(msEvent.getRelativePosition());
                        eventSet.add(newEvent2);
                        break;
                    case "PersonEntersVehicle":
                        eventSet.add(new MSPersonEntersVehicle(msEvent));
                        break;
                    case "PersonLeavesVehicle":
                        eventSet.add(new MSPersonLeavesVehicle(msEvent));
                        break;
                    case "actstart":
                        eventSet.add(new MSActStart(msEvent));
                        break;
                    case "actend":
                        eventSet.add(new MSActEnd(msEvent));
                        break;
                    default:
                        msEvent.setValid(false);
                        eventSet.add(msEvent);
                        break;
                }
            }
        }
        input.setEventsCollection(eventSet);
    }

    /**
     * Processing for the Vehicles : fill the dedicated map in the scenario
     * There are also linked to the events in which there are involved
     * @param scenario The current scenario issued by the parser
     */
    private void processMSVehicle(Scenario scenario) {
        scenario.setVehiclesCollection(scenario.getMandatorySimModels().get(MSVehicle.class));
        for (Event event: scenario.getEvents().values()) {
            if(event instanceof LegEvent) {
                try{
                    scenario.getVehicle(event.getVehicle()).appendRelatedEvents(event);
                } catch (NullPointerException e){
                    System.exit(1);
                }
            }
        }
    }

    /**
     * Processing for the Persons : fill the dedicated map in the scenario
     * They are also linked to the events in which they are involved
     * @param scenario The current scenario issued by the parser
     */
    private void processMSPerson(Scenario scenario) {
        scenario.setPersonsCollection(scenario.getMandatorySimModels().get(MSPerson.class));
        for (Event event: scenario.getEvents().values()) {
            if(event instanceof BoardingEvent || event instanceof ActEvent) {
                scenario.getPerson(event.getPerson()).appendRelatedEvents(event);
            }
        }
    }

    /**
     * Processing for the Links : Recover all the translated links in a Set
     * There are also linked to their related "from" and "to" nodes
     * @param input The current scenario issued by the parser
     * @return The set of links created
     */
    private Set<Link> processMSLink(Scenario input) {
        Set<MandatorySimModel> linksGen = input.getMandatorySimModels().get(MSLink.class);
        Set<Link> links = new HashSet<>();
        for (MandatorySimModel model: linksGen) {
            if (model instanceof Link)
                links.add((Link)model);
        }
        for (Link l: links) {
            l.setToObject(input.getNetwork().getNode(l.getTo()));
            l.setFromeObject(input.getNetwork().getNode(l.getFrom()));
        }
        return links;
    }

    /**
     * Processing for the Network : Fill the dedicated maps with all nodes and links
     * @param input The current scenario issued by the parser
     */
    private void processMSNetwork(Scenario input) {
        Network network = new MSNetwork(input.getName(), "MSNetwork");
        input.setNetwork(network);
        input.getNetwork().setNodesCollection(processMSNode(input));
        input.getNetwork().setLinksCollection(processMSLink(input));
    }

    /**
     * Processing for the Facilities : Fill the dedicated map in the scenario
     * @param scenario The current scenario issued by the parser
     */
    private void processMSFacility(Scenario scenario) {
        scenario.setFacilityCollection(scenario.getOptionalSimModels().get(MSFacility.class));
    }

    /**
     * Processing for the nodes : Recover all the translated nodes in a Set
     * @param input The current scenario issued by the parser
     * @return The set of nodes created
     */
    private Set<Node> processMSNode(Scenario input) {
        Set<MandatorySimModel> nodesGen = input.getMandatorySimModels().get(MSNode.class);
        Set<Node> nodes = new HashSet<>();
        for (MandatorySimModel model: nodesGen) {
            if (model instanceof Node)
                nodes.add((Node)model);
        }
        return nodes;
    }

    /**
     * Linking of the events to their related objects
     * @param input The current scenario issued by the parser
     */
    private void processMSEventObjects(Scenario input) {
        for(Event e : input.getEvents().values()) {
            if(e instanceof TrafficEvent) {
                TrafficEvent te = (TrafficEvent) e;
                te.setLinkObject(input.getNetwork().getLinks().get((long)te.getLink()));
                te.setVehicleObject(input.getVehicles().get(te.getVehicle()));
            } else if(e instanceof BoardingEvent) {
                BoardingEvent be = (BoardingEvent) e;
                be.setPersonObject(input.getPersons().get(be.getPerson()));
                be.setVehicleObject(input.getVehicles().get(be.getVehicle()));
            } else if(e instanceof ActEvent) {
                ActEvent ae = (ActEvent) e;
                ae.setPersonObject(input.getPersons().get(ae.getPerson()));
                if(ae.getFacility() != -1) ae.setFacilityObject(input.getFacilities().get(ae.getFacility()));
            }
        }
    }

    /**
     * Computation of the valid events coordinates
     * @param input The current scenario issued by the parser
     */
    private void processMSEventCoordinate(Scenario input) {
        Set<TrafficEvent> trafficEvents = new HashSet<>();
        Set<BoardingEvent> boardingEvents = new HashSet<>();
        Set<ActEvent> actEvents = new HashSet<>();
        for(Event e : input.getEvents().values()) {
            if(e instanceof TrafficEvent) trafficEvents.add((TrafficEvent) e);
            if(e instanceof BoardingEvent) boardingEvents.add((BoardingEvent) e);
            if(e instanceof ActEvent) actEvents.add((ActEvent) e);
        }

        for(TrafficEvent e : trafficEvents) {
            double fromX = e.getLinkObject().getFromeObject().getX();
            double toX = e.getLinkObject().getToObject().getX();
            double fromY = e.getLinkObject().getFromeObject().getY();
            double toY = e.getLinkObject().getToObject().getY();
            if(e.getType().equals("vehicle enters traffic") || e.getType().equals("vehicle leaves traffic")) {
                e.setX(fromX + (toX - fromX) * e.getRelativePosition());
                e.setY(fromY + (toY - fromY) * e.getRelativePosition());
            } else if(e instanceof VehicleEntersLink) {
                e.setX(fromX);
                e.setY(fromY);
            } else if(e instanceof VehicleLeavesLink) {
                e.setX(toX);
                e.setY(toY);
            }
        }

        for(BoardingEvent e : boardingEvents) {
            if(e instanceof PersonEntersVehicle) {
                VehicleEntersLink nearestEvent = e.getVehicleObject().getNearestLinkEntering(e.getTime());
                if(nearestEvent != null) {
                    e.setX(nearestEvent.getX());
                    e.setY(nearestEvent.getY());
                } else {
                    e.setValid(false);
                }
            } else if(e instanceof PersonLeavesVehicle) {
                VehicleLeavesLink latestEvent = e.getVehicleObject().getLatestLinkLeaving(e.getTime());
                if(latestEvent != null) {
                    e.setX(latestEvent.getX());
                    e.setY(latestEvent.getY());
                } else {
                    e.setValid(false);
                }
            }
        }

        for(ActEvent e : actEvents) {
            if(e instanceof ActStart) {
                PersonLeavesVehicle latestEvent = e.getPersonObject().getLatestVehicleLeaving(e.getTime());
                if(latestEvent != null && latestEvent.isValid()) {
                    e.setX(latestEvent.getX());
                    e.setY(latestEvent.getY());
                } else {
                    e.setValid(false);
                }
            } else if(e instanceof ActEnd) {
                PersonEntersVehicle nearestEvent = e.getPersonObject().getNearestVehicleEntering(e.getTime());
                if(nearestEvent != null && nearestEvent.isValid()) {
                    e.setX(nearestEvent.getX());
                    e.setY(nearestEvent.getY());
                } else {
                    e.setValid(false);
                }
            }
        }
    }
}
