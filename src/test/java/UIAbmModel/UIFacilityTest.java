package UIAbmModel;

import AbmModel.Facility;
import AbmModel.Person;
import AbmModel.Vehicle;
import MatsimModel.MSFacility;
import MatsimModel.MSPerson;
import MatsimModel.MSVehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class UIFacilityTest {

    UIFacility facility;

    @BeforeEach
    void setUp() {
        Facility f = new MSFacility(1, 0,100, 0);
        this.facility = new UIFacility(f);
    }

    @Test
    void constructorNull(){
        assertThrows(IllegalArgumentException.class, () -> new UIFacility(null));
    }

    @Test
    void addPerson() {
        Person p2 = new MSPerson(2,"M",24,"", "", "", new ArrayList<>());
        UIPerson p = new UIPerson(p2);
        this.facility.addPerson(p);

        assertEquals(1, this.facility.getInsidePersons().size());
    }

    @Test
    void addPersonNull() {
        assertThrows(IllegalArgumentException.class, () -> facility.addPerson(null));
    }

    @Test
    void addVehicle() {
        Vehicle v2 = new MSVehicle(0, new ArrayList<>());
        UIVehicle v = new UIVehicle(v2);
        this.facility.addVehicle(v);

        assertEquals(1, this.facility.getInsideVehicles().size());
    }

    @Test
    void addVehicleNull() {
        assertThrows(IllegalArgumentException.class, () -> facility.addVehicle(null));
    }

    @Test
    void removePerson() {
        Person p2 = new MSPerson(2,"M",24,"", "", "", new ArrayList<>());
        UIPerson p = new UIPerson(p2);
        this.facility.addPerson(p);
        Person p3 = new MSPerson(2,"M",24,"", "", "", new ArrayList<>());
        UIPerson p4 = new UIPerson(p3);
        this.facility.remove(p4);

        assertEquals(0, this.facility.getInsidePersons().size());
    }

    @Test
    void removePersonNull() {
        assertThrows(IllegalArgumentException.class, () -> facility.remove(null));
    }

    @Test
    void removeVehicle() {
        Vehicle v2 = new MSVehicle(0, new ArrayList<>());
        UIVehicle v = new UIVehicle(v2);
        this.facility.addVehicle(v);
        Vehicle v3 = new MSVehicle(0, new ArrayList<>());
        UIVehicle v4 = new UIVehicle(v3);
        this.facility.removeVehicle(v4);

        assertEquals(0, this.facility.getInsideVehicles().size());
    }

    @Test
    void removeVehicleNull() {
        assertThrows(IllegalArgumentException.class, () -> facility.removeVehicle(null));
    }

    @Test
    void setColorNull(){
        assertThrows(IllegalArgumentException.class, () -> this.facility.setColor(null));
    }

    @Test
    void testHashCode() {
        Facility f2 = new MSFacility(1, 0,100, 0);
        UIFacility f = new UIFacility(f2);
        assertEquals(facility.hashCode(),f.hashCode());
        assertEquals(facility,f);
    }
}