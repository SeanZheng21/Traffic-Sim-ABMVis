package UIAbmModel;

import AbmModel.*;
import MatsimModel.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class RunnableVehicleLeavesLinkTest {

    VehicleLeavesLink e;
    RunnableVehicleLeavesLink event;
    UIVehicle vehicle;
    UILink link;
    UIPerson person;

    @BeforeEach
    void setUp() {
        Vehicle v = new MSVehicle(1, new ArrayList<>());
        this.vehicle = new UIVehicle(v);
        Person p = new MSPerson(1,"M",24,"", "", "", new ArrayList<>());
        this.person = new UIPerson(p);

        Link l = new MSLink(0,1,50,"","", new HashSet<>(),1,0,null,1,null,1000);
        this.link = new UILink(l);

        this.e = new MSVehicleLeavesLink(2, 100, "", 0,100,-1, 1, 1, -1, new HashMap<>(), v, l);
        this.event = new RunnableVehicleLeavesLink(e,this.vehicle,this.link);

        PersonEntersVehicle e2 = new MSPersonEntersVehicle(0, 100, "", 0,100,1, 1, -1, -1, new HashMap<>(), v, p);
        RunnablePersonEntersVehicle event2 = new RunnablePersonEntersVehicle(e2,this.vehicle,this.person);
        event2.execute();

        MSVehicleEntersLink e3 = new MSVehicleEntersLink(1, 100, "", 0,100,-1, 1, 1, -1, new HashMap<>(), v, l);
        RunnableVehicleEntersLink event3 = new RunnableVehicleEntersLink(e3,this.vehicle,this.link);
        event3.execute();
    }

    @Test
    void construteurNull(){
        assertThrows(IllegalArgumentException.class, () -> new RunnableVehicleLeavesLink(null, vehicle, link));
        assertThrows(IllegalArgumentException.class, () -> new RunnableVehicleLeavesLink(e, null, link));
        assertThrows(IllegalArgumentException.class, () -> new RunnableVehicleLeavesLink(e, vehicle, null));
    }

    @Test
    void constructeurWithBadVehicle(){
        Vehicle v = new MSVehicle(2, new ArrayList<>());
        UIVehicle v2 = new UIVehicle(v);

        assertThrows(IllegalArgumentException.class,() -> new RunnableVehicleLeavesLink(e, v2, this.link));
    }

    @Test
    void constructeurWithBadLink(){
        Link l = new MSLink(0,1,50,"","", new HashSet<>(),2,0,null,1,null,1000);
        UILink l2 = new UILink(l);

        assertThrows(IllegalArgumentException.class,() -> new RunnableVehicleLeavesLink(e, vehicle, l2));
    }

    @Test
    void execute() {
        assertTrue(this.link.getCirculatingPersons().contains(this.person));
        assertTrue(this.link.getCirculatingVehicles().contains(this.vehicle));
        assertEquals(this.link, this.vehicle.getTrafficLink().get());
        assertEquals(this.link, this.person.getLinkLocation().get());

        this.event.execute();

        assertTrue(this.link.getCirculatingPersons().isEmpty());
        assertTrue(this.link.getCirculatingVehicles().isEmpty());
        assertTrue(this.vehicle.getTrafficLink().isEmpty());
        assertTrue(this.person.getLinkLocation().isEmpty());
    }

    @Test
    void executeTwice() {

        this.event.execute();
        assertThrows(IllegalCallerException.class, () -> this.event.execute());

    }

    @Test
    void undo() {
        this.event.execute();
        this.event.undo();

        assertTrue(this.link.getCirculatingPersons().contains(this.person));
        assertTrue(this.link.getCirculatingVehicles().contains(this.vehicle));
        assertEquals(this.link, this.vehicle.getTrafficLink().get());
        assertEquals(this.link, this.person.getLinkLocation().get());
    }

    @Test
    void undoWithoutExecute() {
        assertThrows(IllegalCallerException.class, () -> this.event.undo());
    }

    @Test
    void undoTwice() {
        this.event.execute();
        this.event.undo();
        assertThrows(IllegalCallerException.class, () -> this.event.undo());
    }

}