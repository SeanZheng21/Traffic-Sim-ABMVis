package UIAbmModel;

import AbmModel.*;
import MatsimModel.MSPerson;
import MatsimModel.MSPersonEntersVehicle;
import MatsimModel.MSVehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class RunnablePersonEntersVehicleTest {


    PersonEntersVehicle e;
    RunnablePersonEntersVehicle event;
    UIVehicle vehicle;
    UIPerson person;

    @BeforeEach
    void setUp() {
        Vehicle v = new MSVehicle(1, new ArrayList<>());
        this.vehicle = new UIVehicle(v);
        Person p = new MSPerson(1,"M",24,"", "", "", new ArrayList<>());
        this.person = new UIPerson(p);

        this.e = new MSPersonEntersVehicle(0, 100, "", 0,100,1, 1, -1, -1, new HashMap<>(), v, p);
        this.event = new RunnablePersonEntersVehicle(e,this.vehicle,this.person);
    }

    @Test
    void construteurNull(){
        assertThrows(IllegalArgumentException.class, () -> new RunnablePersonEntersVehicle(null, vehicle, person));
        assertThrows(IllegalArgumentException.class, () -> new RunnablePersonEntersVehicle(e, null, person));
        assertThrows(IllegalArgumentException.class, () -> new RunnablePersonEntersVehicle(e, vehicle, null));
    }

    @Test
    void constructeurWithBadVehicle(){
        Vehicle v = new MSVehicle(2, new ArrayList<>());
        UIVehicle v2 = new UIVehicle(v);

        assertThrows(IllegalArgumentException.class,() -> new RunnablePersonEntersVehicle(e, v2, person));
    }

    @Test
    void constructeurWithBadPerson(){
        Person p = new MSPerson(2,"M",24,"", "", "", new ArrayList<>());
        UIPerson p2 = new UIPerson(p);

        assertThrows(IllegalArgumentException.class,() -> new RunnablePersonEntersVehicle(e, vehicle, p2));
    }

    @Test
    void execute() {

        assertTrue(this.vehicle.getOnBoardPersons().isEmpty());
        assertTrue(this.person.getVehicleOnBoard().isEmpty());

        this.event.execute();

        assertTrue(this.vehicle.getOnBoardPersons().contains(this.person));
        assertEquals(this.vehicle, this.person.getVehicleOnBoard().get());

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
        assertTrue(this.vehicle.getOnBoardPersons().isEmpty());
        assertTrue(this.person.getVehicleOnBoard().isEmpty());
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