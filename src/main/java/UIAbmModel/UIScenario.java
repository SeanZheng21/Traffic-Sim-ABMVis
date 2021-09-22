package UIAbmModel;

import AbmModel.*;

import java.util.*;

/**
 * This classe is the running version of a scenario
 */
public class UIScenario {

    /**
     * scenario in the static model corresponding to this scenario
     */
    private final Scenario scenario;

    /**
     * 
     */
    private double time = 0;

    /**
     * 
     */
    private double zoom;

    /**
     * this map allows us to get something using it's class and it's id
     */
    private Map<Class<? extends UISimModel>, Map<Long, UISimModel>>  UIInstancesPerID;

    /**
     * all the UIVehicles
     */
    private final Set<UIVehicle> uivehicles;

    /**
     * all the UINodes
     */
    private final Set<UINode> uinodes;

    /**
     * all the UILinks
     */
    private final Set<UILink> uilinks;

    /**
     * all the UIPersons
     */
    private final Set<UIPerson> uipersons;

    /**
     * all the RunnableEvent
     */
    private final List<RunnableEvent> uievents;

    /**
     * all the UIFacilities
     */
    private final Set<UIFacility> uifacilities;

    /**
     * final static map linking string type of event to their related runnableEvent type
     */
    private final static Map<String, Class<? extends RunnableEvent>> runnableEventsTypes = Map.of(
            "actstart", RunnableActStartEvent.class,
            "actend", RunnableActEndEvent.class,
            "PersonEntersVehicle", RunnablePersonEntersVehicle.class,
            "PersonLeavesVehicle", RunnablePersonLeavesVehicle.class,
            "entered link", RunnableVehicleEntersLink.class,
            "left link", RunnableVehicleLeavesLink.class
    );



    /**
     * @param scenario the static scenario corresponding to the UIScenario
     */
    public UIScenario(Scenario scenario) {
        this.scenario = scenario;
        this.uifacilities = new HashSet<>();
        this.uilinks = new HashSet<>();
        this.uipersons = new HashSet<>();
        this.uivehicles = new HashSet<>();
        this.uinodes = new HashSet<>();
        this.uievents = new ArrayList<>();
        this.initialiseUIInstancesPerID();
        for (Facility f: scenario.getFacilities().values()){
            this.addUIFacility(f);
        }
        for (Node n: scenario.getNetwork().getNodes().values()){
            this.addUINode(n);
        }
        for (Person p: scenario.getPersons().values()){
            this.addUIPerson(p);
        }
        for (Link l: scenario.getNetwork().getLinks().values()){
            this.addUILink(l);
        }
        for (Vehicle v: scenario.getVehicles().values()){
            this.addUIVehicle(v);
        }
        for (Event e : scenario.getEvents().values()){
            this.addRunnableEvent(e);
        }
        this.uievents.sort(Comparator.comparingDouble(e -> e.getEvent().getTime()));

        // TODO implement here les Evenements
    }



    /**
     * this function initialise all the hashMap for all the classes
     */
    private void initialiseUIInstancesPerID(){
        this.UIInstancesPerID = new HashMap<>();
        this.UIInstancesPerID.put(UIFacility.class, new HashMap<>());
        this.UIInstancesPerID.put(UINode.class, new HashMap<>());
        this.UIInstancesPerID.put(UIPerson.class, new HashMap<>());
        this.UIInstancesPerID.put(UILink.class, new HashMap<>());
        this.UIInstancesPerID.put(UIVehicle.class, new HashMap<>());
        this.UIInstancesPerID.put(RunnableEvent.class, new HashMap<>());
    }

    /**
     * jump to the specified timestamp
     * @param time the timestamp
     */
    public void goTo(double time) {
        if (time - this.time > 0) {
            for (RunnableEvent e : uievents) {
                //System.out.println(e.getEvent().getTime());
                if (e.getEvent().getTime() <= time) {
                    if (!e.isExecuteCalled()) {
                        e.execute();
                    }
                } else {
                    if (e.isExecuteCalled()) {
                        e.undo();
                    }
                }
            }
        } else {
            for (int i=uievents.size()-1; i>=0; i--) {
                RunnableEvent e = uievents.get(i);
                //System.out.println(e.getEvent().getTime());
                if (e.getEvent().getTime() <= time) {
                    if (!e.isExecuteCalled()) {
                        e.execute();
                    }
                } else {
                    if (e.isExecuteCalled()) {
                        e.undo();
                    }
                }
            }
        }
        for(UIVehicle v : uivehicles) {
            v.calculatePosition(time);
        }
        for(UIPerson p : uipersons) {
            p.calculatePosition(time);
        }
        this.time = time;
    }

    /**
     * this functions allows us to add facility as UIFacility
     * @param facility UIFacility to add in the Scenario
     */
    private void addUIFacility(Facility facility){
        UIFacility f = new UIFacility(facility);
        this.uifacilities.add(f);

        this.UIInstancesPerID.get(UIFacility.class).put((long) facility.getId(), f);
    }

    /**
     * this functions allows us to add node as UINode
     * @param node UINode to add in the Scenario
     */
    private void addUINode(Node node){
        UINode n = new UINode(node);
        this.uinodes.add(n);

        this.UIInstancesPerID.get(UINode.class).put(node.getId(), n);
    }

    /**
     * this functions allows us to add person as UIPerson
     * @param person UIPerson to add in the Scenario
     */
    private void addUIPerson(Person person){
        UIPerson p = new UIPerson(person);
        this.uipersons.add(p);

        this.UIInstancesPerID.get(UIPerson.class).put((long) person.getId(), p);
    }

    /**
     * this functions allows us to add Link as UILink
     * @param link UILink to add in the Scenario
     */
    private void addUILink(Link link){
        UILink l = new UILink(link);
        this.uilinks.add(l);

        this.UIInstancesPerID.get(UILink.class).put(link.getId(), l);
    }

    /**
     * this functions allows us to add vehicle as UIVehicle
     * @param vehicle UIVehicle to add in the Scenario
     */
    private void addUIVehicle(Vehicle vehicle){
        UIVehicle v = new UIVehicle(vehicle);
        this.uivehicles.add(v);

        this.UIInstancesPerID.get(UIVehicle.class).put((long) vehicle.getId(), v);
    }

    /**
     * this functions allows us to add event as RunnableEvent
     * @param event RunnableEvent to add in the Scenario
     */
    private void addRunnableEvent(Event event) {
        if (event instanceof PersonEntersVehicle){
            PersonEntersVehicle event2 = (PersonEntersVehicle) event;
            UIVehicle vehicle = (UIVehicle) this.UIInstancesPerID.get(UIVehicle.class).get((long)event.getVehicle());
            UIPerson person = (UIPerson) this.UIInstancesPerID.get(UIPerson.class).get((long)event.getPerson());
            RunnablePersonEntersVehicle e = new RunnablePersonEntersVehicle(event2, vehicle, person);
            this.uievents.add(e);

            this.UIInstancesPerID.get(RunnableEvent.class).put((long) event.getId(), e);
        }
        if (event instanceof PersonLeavesVehicle) {
            PersonLeavesVehicle event2 = (PersonLeavesVehicle) event;
            UIVehicle vehicle = (UIVehicle) this.UIInstancesPerID.get(UIVehicle.class).get((long)event.getVehicle());
            UIPerson person = (UIPerson) this.UIInstancesPerID.get(UIPerson.class).get((long)event.getPerson());
            RunnablePersonLeavesVehicle e = new RunnablePersonLeavesVehicle(event2, vehicle, person);
            this.uievents.add(e);

            this.UIInstancesPerID.get(RunnableEvent.class).put((long) event.getId(), e);
        }
        if (event instanceof VehicleEntersLink){
            VehicleEntersLink event2 = (VehicleEntersLink) event;
            UIVehicle vehicle = (UIVehicle) this.UIInstancesPerID.get(UIVehicle.class).get((long)event.getVehicle());
            UILink link = (UILink) this.UIInstancesPerID.get(UILink.class).get((long)event.getLink());
            RunnableVehicleEntersLink e = new RunnableVehicleEntersLink(event2, vehicle, link);
            this.uievents.add(e);

            this.UIInstancesPerID.get(RunnableEvent.class).put((long) event.getId(), e);
        }
        if (event instanceof VehicleLeavesLink){
            VehicleLeavesLink event2 = (VehicleLeavesLink) event;
            UIVehicle vehicle = (UIVehicle) this.UIInstancesPerID.get(UIVehicle.class).get((long)event.getVehicle());
            UILink link = (UILink) this.UIInstancesPerID.get(UILink.class).get((long)event.getLink());
            RunnableVehicleLeavesLink e = new RunnableVehicleLeavesLink(event2, vehicle, link);
            this.uievents.add(e);

            this.UIInstancesPerID.get(RunnableEvent.class).put((long) event.getId(), e);
        }
        if (event instanceof ActStart){
            ActStart event2 = (ActStart) event;
            UIPerson person = (UIPerson) this.UIInstancesPerID.get(UIPerson.class).get((long)event.getPerson());
            UIFacility facility = (UIFacility) this.UIInstancesPerID.get(UIFacility.class).get((long)event.getFacility());
            RunnableActStartEvent e = new RunnableActStartEvent(event2, person, facility);
            this.uievents.add(e);

            this.UIInstancesPerID.get(RunnableEvent.class).put((long) event.getId(), e);
        }
        if (event instanceof ActEnd){
            ActEnd event2 = (ActEnd) event;
            UIPerson person = (UIPerson) this.UIInstancesPerID.get(UIPerson.class).get((long)event.getPerson());
            UIFacility facility = (UIFacility) this.UIInstancesPerID.get(UIFacility.class).get((long)event.getFacility());
            RunnableActEndEvent e = new RunnableActEndEvent(event2, person, facility);
            this.uievents.add(e);

            this.UIInstancesPerID.get(RunnableEvent.class).put((long) event.getId(), e);
        }
    }

    /**
     * the function to get the size of the UINode Set
     * @return the size of the UINode Set
     */
    public int getUINodesSize(){
        return this.uinodes.size();
    }

    /**
     * the function to get the size of the UIVehicle Set
     * @return the size of the UIVehicle Set
     */
    public int getUIVehicleSize(){
        return this.uivehicles.size();
    }

    /**
     * the function to get the size of the UIPerson Set
     * @return the size of the UIPerson Set
     */
    public int getUIPersonSize(){
        return this.uipersons.size();
    }

    /**
     * the function to get the size of the UILink Set
     * @return the size of the UILink Set
     */
    public int getUILinkSize(){
        return this.uilinks.size();
    }

    /**
     * the function to get the size of the UIFacility Set
     * @return the size of the UIFacility Set
     */
    public int getUIFacilitySize(){
        return this.uifacilities.size();
    }

    /**
     * the function to get the size of the UIEvent Set
     * @return the size of the UIEvent Set
     */
    public int getUIEventSize(){
        return this.uievents.size();
    }

    /**
     * recover UISimModel object from outside the UIScenario
     * based on their class type and id
     * @param cl class type
     * @param id id of the element
     * @return The UISimModel object intended if it exists
     */
    public UISimModel getInstancePerID(Class<? extends UISimModel> cl, long id) {
        return UIInstancesPerID.get(cl).get(id);
    }

    /**
     * get the size of all the sets
     * @return the size of all the sets
     */
    public int getUIInstanceperIDSize(){
        int res = 0;
        for (Map<Long, UISimModel> map: this.UIInstancesPerID.values()) {
            res += map.size();
        }
        return res;
    }

    /**
     * getter
     * @return getter
     */
    public Set<UILink> getUilinks() {
        return uilinks;
    }

    /**
     * getter
     * @return getter
     */
    public Set<UIFacility> getUifacilities() {
        return uifacilities;
    }

    /**
     * getter
     * @return getter
     */
    public Set<UIVehicle> getUivehicles() {
        return uivehicles;
    }

    /**
     * getter
     * @return getter
     */
    public Set<UINode> getUinodes() {
        return uinodes;
    }

    /**
     * getter
     * @return getter
     */
    public Set<UIPerson> getUipersons() {
        return uipersons;
    }

    /**
     * getter
     * @return getter
     */
    public List<RunnableEvent> getUievents() {
        return uievents;
    }
}