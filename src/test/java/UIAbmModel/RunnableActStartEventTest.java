package UIAbmModel;

import AbmModel.*;
import MatsimModel.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class RunnableActStartEventTest {

    ActStart e;
    RunnableActStartEvent event;
    UIFacility facility;
    UIPerson person;

    @BeforeEach
    void setUp() {
        Facility f = new MSFacility(1, 0,100, 0);
        this.facility = new UIFacility(f);
        Person p = new MSPerson(1,"M",24,"", "", "", new ArrayList<>());
        this.person = new UIPerson(p);

        this.e = new MSActStart(1, 100, "", 0,100,1, -1, -1, 1, new HashMap<>(), p, f);
        this.event = new RunnableActStartEvent(e,this.person,this.facility);
    }

    @Test
    void construteurNull(){
        assertThrows(IllegalArgumentException.class, () -> new RunnableActStartEvent(null, person, facility));
        assertThrows(IllegalArgumentException.class, () -> new RunnableActStartEvent(e, null, facility));
    }

    @Test
    void constructeurWithBadFacility(){
        Facility f = new MSFacility(2, 0,100, 0);
        UIFacility f2 = new UIFacility(f);

        assertThrows(IllegalArgumentException.class,() -> new RunnableActStartEvent(e, person, f2));
    }

    @Test
    void constructeurWithBadPerson(){
        Person p = new MSPerson(2,"M",24,"", "", "", new ArrayList<>());
        UIPerson p2 = new UIPerson(p);

        assertThrows(IllegalArgumentException.class,() -> new RunnableActStartEvent(e, p2, facility));
    }

    @Test
    void execute() {
        assertTrue(this.person.getFacilityLocation().isEmpty());
        assertTrue(this.facility.getInsidePersons().isEmpty());

        this.event.execute();

        assertTrue(this.facility.getInsidePersons().contains(this.person));
        assertEquals(this.facility, this.person.getFacilityLocation().get());
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

        assertTrue(this.person.getFacilityLocation().isEmpty());
        assertTrue(this.facility.getInsidePersons().isEmpty());
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

    @Test
    void facilityNull(){
        RunnableEvent re = new RunnableActStartEvent(e, person, null);
        assertEquals(1,re.getEvent().getId());
        assertFalse(this.person.getFacilityProperty().get().isPresent());
        re.execute();
        assertFalse(this.person.getFacilityProperty().get().isPresent());
        re.undo();
        assertFalse(this.person.getFacilityProperty().get().isPresent());
    }

}