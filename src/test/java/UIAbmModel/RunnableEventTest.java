package UIAbmModel;

import AbmModel.ActEnd;
import AbmModel.ActStart;
import AbmModel.Facility;
import AbmModel.Person;
import MatsimModel.MSActEnd;
import MatsimModel.MSActStart;
import MatsimModel.MSFacility;
import MatsimModel.MSPerson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class RunnableEventTest {

    ActEnd e;
    RunnableActEndEvent event;
    UIFacility facility;
    UIPerson person;
    Person p;
    Facility f;

    @BeforeEach
    void setUp() {
        f = new MSFacility(1, 0,100, 0);
        this.facility = new UIFacility(f);
        p = new MSPerson(1,"M",24,"", "", "", new ArrayList<>());
        this.person = new UIPerson(p);

        this.e = new MSActEnd(1, 100, "", 0,100,1, -1, -1, 1, new HashMap<>(), p, f);
        this.event = new RunnableActEndEvent(e,this.person,this.facility);
    }

    @Test
    void testEquals() {
        assertTrue(event.equals(event));
        assertFalse(event.equals(null));
        assertFalse(event.equals(e));
        RunnableEvent event2 = new RunnableActEndEvent(e,this.person,this.facility);
        assertTrue(event.equals(event2));
        ActEnd e2 = new MSActEnd(2, 100, "", 0,100,1, -1, -1, 1, new HashMap<>(), p, f);
        RunnableEvent event3 = new RunnableActEndEvent(e2,this.person,this.facility);
        assertFalse(event.equals(event3));
    }

    @Test
    void testHashCode() {
        RunnableEvent event2 = new RunnableActEndEvent(e,this.person,this.facility);
        assertEquals(event2.hashCode(), event.hashCode());
    }
}