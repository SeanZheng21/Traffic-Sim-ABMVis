package UIAbmModel;

import AbmModel.Person;
import AbmModel.PersonEntersVehicle;
import AbmModel.PersonLeavesVehicle;
import AbmModel.Vehicle;
import MatsimModel.MSPerson;
import MatsimModel.MSPersonEntersVehicle;
import MatsimModel.MSPersonLeavesVehicle;
import MatsimModel.MSVehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class RunnablePersonLeavesVehicleTest {

    PersonLeavesVehicle e;
    RunnablePersonLeavesVehicle eventLeaves;
    UIVehicle vehicle;
    UIPerson person;

    @BeforeEach
    void setUp() {
        Vehicle v = new MSVehicle(1, new ArrayList<>());
        this.vehicle = new UIVehicle(v);
        Person p = new MSPerson(1,"M",24,"", "", "", new ArrayList<>());
        this.person = new UIPerson(p);

        this.e = new MSPersonLeavesVehicle(1, 100, "", 0,100,1, 1, -1, -1, new HashMap<>(), v, p);
        this.eventLeaves = new RunnablePersonLeavesVehicle(e,this.vehicle,this.person);

        PersonEntersVehicle e2 = new MSPersonEntersVehicle(0, 100, "", 0,100,1, 1, -1, -1, new HashMap<>(), v, p);
        RunnablePersonEntersVehicle eventEnter = new RunnablePersonEntersVehicle(e2,this.vehicle,this.person);
        eventEnter.execute();
    }

    @Test
    void construteurNull(){
        assertThrows(IllegalArgumentException.class, () -> new RunnablePersonLeavesVehicle(null, vehicle, person));
        assertThrows(IllegalArgumentException.class, () -> new RunnablePersonLeavesVehicle(e, null, person));
        assertThrows(IllegalArgumentException.class, () -> new RunnablePersonLeavesVehicle(e, vehicle, null));
    }

    @Test
    void constructeurWithBadVehicle(){
        Vehicle v = new MSVehicle(2, new ArrayList<>());
        UIVehicle v2 = new UIVehicle(v);

        assertThrows(IllegalArgumentException.class,() -> new RunnablePersonLeavesVehicle(e, v2, person));
    }

    @Test
    void constructeurWithBadPerson(){
        Person p = new MSPerson(2,"M",24,"", "", "", new ArrayList<>());
        UIPerson p2 = new UIPerson(p);

        assertThrows(IllegalArgumentException.class,() -> new RunnablePersonLeavesVehicle(e, vehicle, p2));
    }

    @Test
    void execute() {
        assertTrue(this.vehicle.getOnBoardPersons().contains(this.person));
        assertEquals(this.vehicle, this.person.getVehicleOnBoard().get());

        this.eventLeaves.execute();

        assertTrue(this.vehicle.getOnBoardPersons().isEmpty());
        assertTrue(this.person.getVehicleOnBoard().isEmpty());
    }

    @Test
    void executeTwice() {

        this.eventLeaves.execute();
        assertThrows(IllegalCallerException.class, () -> this.eventLeaves.execute());

    }

    @Test
    void undo() {

        this.eventLeaves.execute();
        this.eventLeaves.undo();
        assertTrue(this.vehicle.getOnBoardPersons().contains(this.person));
        assertEquals(this.vehicle, this.person.getVehicleOnBoard().get());

    }

    @Test
    void undoWithoutExecute() {
        assertThrows(IllegalCallerException.class, () -> this.eventLeaves.undo());
    }

    @Test
    void undoTwice() {
        this.eventLeaves.execute();
        this.eventLeaves.undo();
        assertThrows(IllegalCallerException.class, () -> this.eventLeaves.undo());
    }
}