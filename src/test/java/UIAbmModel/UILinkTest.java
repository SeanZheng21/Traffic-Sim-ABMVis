package UIAbmModel;

import AbmModel.Link;
import AbmModel.Person;
import AbmModel.Vehicle;
import MatsimModel.MSLink;
import MatsimModel.MSPerson;
import MatsimModel.MSVehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class UILinkTest {

    UILink l;

    @BeforeEach
    void setUp(){
        Link l2 = new MSLink(0,1,50,"","", new HashSet<>(),2,0,null,1,null,1000);
        this.l = new UILink(l2);
    }

    @Test
    void constructeurNull(){
        assertThrows(IllegalArgumentException.class, () -> new UILink(null));
    }

    @Test
    void addPerson() {
        Person p = new MSPerson(2,"M",24,"", "", "", new ArrayList<>());
        UIPerson p2 = new UIPerson(p);
        this.l.addPerson(p2);
        assertEquals(1, this.l.getCirculatingPersons().size());
    }

    @Test
    void addPersonNull() {
        assertThrows(IllegalArgumentException.class, () -> this.l.addPerson(null));
    }

    @Test
    void addVehicle() {
        Vehicle v = new MSVehicle(0, new ArrayList<>());
        UIVehicle v2 = new UIVehicle(v);
        this.l.addVehicle(v2);
        assertEquals(1, this.l.getCirculatingVehicles().size());
    }

    @Test
    void addVehicleNull() {
        assertThrows(IllegalArgumentException.class, () -> this.l.addVehicle(null));
    }

    @Test
    void removePerson() {
        Person p = new MSPerson(2,"M",24,"", "", "", new ArrayList<>());
        UIPerson p2 = new UIPerson(p);
        this.l.addPerson(p2);
        Person p3 = new MSPerson(2,"M",24,"", "", "", new ArrayList<>());
        UIPerson p4 = new UIPerson(p3);
        this.l.removePerson(p4);
        assertEquals(0, this.l.getCirculatingPersons().size());
    }

    @Test
    void removePersonNull() {
        assertThrows(IllegalArgumentException.class, () -> this.l.removePerson(null));
    }

    @Test
    void removeVehicle() {
        Vehicle v = new MSVehicle(0, new ArrayList<>());
        UIVehicle v2 = new UIVehicle(v);
        this.l.addVehicle(v2);
        Vehicle v3 = new MSVehicle(0, new ArrayList<>());
        UIVehicle v4 = new UIVehicle(v3);
        this.l.removeVehicle(v4);
        assertEquals(0, this.l.getCirculatingVehicles().size());
    }

    @Test
    void removeVehicleNull() {
        assertThrows(IllegalArgumentException.class, () -> this.l.removeVehicle(null));
    }

    @Test
    void setColorNull() {
        assertThrows(IllegalArgumentException.class, () -> this.l.setColor(null));
    }

    @Test
    void hashCodeTest(){
        Link l2 = new MSLink(0,1,50,"","", new HashSet<>(),2,0,null,1,null,1000);
        UILink link = new UILink(l2);
        assertEquals(this.l.hashCode(), link.hashCode());
        assertEquals(this.l, link);
    }
}