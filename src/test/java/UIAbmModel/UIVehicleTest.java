package UIAbmModel;

import AbmModel.*;
import MatsimModel.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UIVehicleTest {


    UIVehicle v;

    @BeforeEach
    void setUp() {
        Vehicle v2 = new MSVehicle(0, new ArrayList<>());
        this.v = new UIVehicle(v2);
    }

    @Test
    void UIVehicle() {
        assertThrows(IllegalArgumentException.class, () -> new UIVehicle(null));
    }

    @Test
    void addPerson() {
        Person p = new MSPerson(2,"M",24,"", "", "", new ArrayList<>());
        UIPerson p2 = new UIPerson(p);
        this.v.addPerson(p2);

        assertEquals(1, this.v.getOnBoardPersons().size());
    }

    @Test
    void addPersonNull() {
        assertThrows(IllegalArgumentException.class, () -> this.v.addPerson(null));
    }

    @Test
    void removePerson() {
        Person p = new MSPerson(2,"M",24,"", "", "", new ArrayList<>());
        UIPerson p2 = new UIPerson(p);
        this.v.addPerson(p2);

        Person p3 = new MSPerson(2,"M",24,"", "", "", new ArrayList<>());
        UIPerson p4 = new UIPerson(p3);
        this.v.removePerson(p4);
        assertEquals(0, this.v.getOnBoardPersons().size());
    }

    @Test
    void removePersonNull() {
        assertThrows(IllegalArgumentException.class, () -> this.v.removePerson(null));
    }

    @Test
    void enterLink() {
        Link l = new MSLink(0,1,50,"","", new HashSet<>(),2,0,null,1,null,1000);
        UILink l2 = new UILink(l);
        this.v.enterLink(l2);
        Link l3 = new MSLink(0,1,50,"","", new HashSet<>(),2,0,null,1,null,1000);
        UILink l4 = new UILink(l3);
        assertEquals(l4, this.v.getTrafficLink().get());
    }

    @Test
    void enterLinkNull() {
        assertThrows(IllegalArgumentException.class, () -> this.v.enterLink(null));
    }

    @Test
    void leaveLink() {
        this.v.leaveLink();
        assertEquals(true,this.v.getTrafficLink().isEmpty());
    }

    @Test
    void enterFacilityNull() {
        assertThrows(IllegalArgumentException.class, () -> this.v.enterFacility(null));
    }

    @Test
    void enterFacility() {
        Facility f = new MSFacility(1, 0,100, 0);
        UIFacility f2 = new UIFacility(f);
        this.v.enterFacility(f2);
        Facility f3 = new MSFacility(1, 0,100, 0);
        UIFacility f4 = new UIFacility(f3);
        assertEquals(f4, this.v.getRestFacility().get());

    }

    @Test
    void leaveFacility() {
        this.v.leaveFacility();
        assertEquals(true,this.v.getRestFacility().isEmpty());
    }

    @Test
    void setColorNull(){
        assertThrows(IllegalArgumentException.class, () -> this.v.setColor(null));
    }

    @Test
    void hashCodeTest(){
        Vehicle v2 = new MSVehicle(0, new ArrayList<>());
        UIVehicle vehicle = new UIVehicle(v2);
        assertEquals(this.v.hashCode(), vehicle.hashCode());
        assertEquals(this.v, vehicle);
    }

    /**
     * Test for calculating the position when the vehicle isn't moving (in a boarding event in this case)
     */
    @Test
    void testCalculateStaticPosition() {
        Person p = new MSPerson(0, "M", 20, "", "", "", new ArrayList<>());
        Vehicle v = new MSVehicle();

        List events = v.getRelatedEvents();
        events.add(new MSPersonEntersVehicle(2, 4.0, "PersonEntersVehicle", 35.7, 72.8, 0, 0, -1, -1, new HashMap<>(), v, p));
        events.add(new MSPersonLeavesVehicle(0, 10.0, "PersonLeavesVehicle", 35.7, 72.8, 0, 0, -1, -1, new HashMap<>(), v, p));
        events.add(new MSPersonEntersVehicle(1, 20.0, "PersonEntersVehicle", 35.7, 72.8, 0, 0, -1, -1, new HashMap<>(), v, p));
        events.add(new MSPersonLeavesVehicle(3, 24.0, "PersonLeavesVehicle", 35.7, 72.8, 0, 0, -1, -1, new HashMap<>(), v, p));
        v.setRelatedEvents(events);

        UIVehicle uiv = new UIVehicle(v);

        uiv.calculatePosition(16.4);

        assertEquals(35.7, uiv.getX(), 0.01);
        assertEquals(72.8, uiv.getY(), 0.01);
    }

    /**
     * Test for calculating the position when the vehicle is moving (on a link in this case)
     */
    @Test
    void testCalculateDynamicPosition() {
        Person p = new MSPerson(0, "M", 20, "", "", "", new ArrayList<>());
        Vehicle v = new MSVehicle(0, new ArrayList<>());
        Node n0 = new MSNode(0, 24.6, 38.8, "");
        Node n1 = new MSNode(0, 75.2, 2.4, "");
        Link l = new MSLink(3.0, 1.0, 100.0, "", "", new HashSet<>(), 0, 0, n0, 1, n1, Math.sqrt(Math.pow((75.2 - 24.6), 2) + Math.pow((2.4 - 38.8), 2)));

        List events = v.getRelatedEvents();
        events.add(new MSPersonEntersVehicle(2, 4.0, "PersonEntersVehicle", 24.6, 38.8, 0, 0, -1, -1, new HashMap<>(), v, p));
        events.add(new MSVehicleEntersLink(0, 10.0, "entered link", 24.6, 38.8, -1, 0, 0, -1, new HashMap<>(), v, l));
        events.add(new MSVehicleLeavesLink(1, 20.0, "left link", 75.2, 2.4, -1, 0, 0, -1, new HashMap<>(), v, l));
        events.add(new MSPersonLeavesVehicle(3, 24.0, "PersonLeavesVehicle", 75.2, 2.4, 0, 0, -1, -1, new HashMap<>(), v, p));
        v.setRelatedEvents(events);

        UIPerson uip = new UIPerson(p);
        UIVehicle uiv = new UIVehicle(v);
        uip.getInVehicle(uiv);

        uiv.calculatePosition(13.6);

        assertEquals(42.816, uiv.getX(), 0.01);
        assertEquals(25.696, uiv.getY(), 0.01);
    }

    /**
     * Test for calculating the position when some event are taking place simultaneously for the same vehicle
     */
    @Test
    void testCalculatePositionWithSimultaneousEvents() {
        Person p = new MSPerson(0, "M", 20, "", "", "", new ArrayList<>());
        Vehicle v = new MSVehicle(0, new ArrayList<>());
        Node n0 = new MSNode(0, 24.6, 38.8, "");
        Node n1 = new MSNode(1, 75.2, 2.4, "");
        Facility f0 = new MSFacility(0, 24.67, 38.8, 0);
        Facility f1 = new MSFacility(1, 75., 2.4, 0);
        Link l0 = new MSLink(3.0, 1.0, 100.0, "", "", new HashSet<>(), 0, 0, n0, 1, n1, Math.sqrt(Math.pow((75.2 - 24.6), 2) + Math.pow((2.4 - 38.8), 2)));
        Link l1 = new MSLink(3.0, 1.0, 100.0, "", "", new HashSet<>(), 0, 1, n1, 0, n0, Math.sqrt(Math.pow((75.2 - 24.6), 2) + Math.pow((2.4 - 38.8), 2)));

        List vevents = v.getRelatedEvents();
        vevents.add(new MSPersonEntersVehicle(1, 10.0, "PersonEntersVehicle", 24.6, 38.8, 0, 0, -1, -1, new HashMap<>(), v, p));
        vevents.add(new MSVehicleEntersLink(2, 10.0, "entered link", 24.6, 38.8, -1, 0, 0, -1, new HashMap<>(), v, l0));
        vevents.add(new MSVehicleLeavesLink(3, 20.0, "left link", 75.2, 2.4, -1, 0, 0, -1, new HashMap<>(), v, l0));
        vevents.add(new MSPersonLeavesVehicle(4, 20.0, "PersonLeavesVehicle", 75.2, 2.4, 0, 0, -1, -1, new HashMap<>(), v, p));
        vevents.add(new MSPersonEntersVehicle(7, 30.0, "PersonEntersVehicle", 75.2, 2.4, 0, 0, -1, -1, new HashMap<>(), v, p));
        vevents.add(new MSVehicleEntersLink(8, 40.0, "entered link", 75.2, 2.4, -1, 0, 0, -1, new HashMap<>(), v, l1));
        vevents.add(new MSVehicleLeavesLink(9, 50.0, "left link", 24.6, 38.8, -1, 0, 0, -1, new HashMap<>(), v, l1));
        vevents.add(new MSPersonLeavesVehicle(10, 50.0, "PersonLeavesVehicle", 24.6, 38.8, 0, 0, -1, -1, new HashMap<>(), v, p));
        vevents.add(new MSPersonEntersVehicle(11, 60.0, "PersonEntersVehicle", 24.6, 38.8, 0, 0, -1, -1, new HashMap<>(), v, p));
        vevents.add(new MSPersonLeavesVehicle(12, 60.0, "PersonLeavesVehicle", 24.6, 38.8, 0, 0, -1, -1, new HashMap<>(), v, p));
        vevents.add(new MSVehicleEntersLink(14, 60.0, "entered link", 24.6, 38.8, -1, 0, 0, -1, new HashMap<>(), v, l0));
        vevents.add(new MSVehicleLeavesLink(15, 70.0, "left link", 75.2, 2.4, -1, 0, 0, -1, new HashMap<>(), v, l0));
        v.setRelatedEvents(vevents);

        UIPerson uip = new UIPerson(p);
        UIVehicle uiv = new UIVehicle(v);

        uip.getInVehicle(uiv);
        uiv.calculatePosition(13.6);

        assertEquals(42.816, uiv.getX(), 0.01);
        assertEquals(25.696, uiv.getY(), 0.01);

        uiv.calculatePosition(25.7);

        assertEquals(75.2, uiv.getX(), 0.01);
        assertEquals(2.4, uiv.getY(), 0.01);

        uiv.calculatePosition(33.3);

        assertEquals(75.2, uiv.getX(), 0.01);
        assertEquals(2.4, uiv.getY(), 0.01);

        uiv.calculatePosition(44.2);

        assertEquals(53.94, uiv.getX(), 0.01);
        assertEquals(17.69, uiv.getY(), 0.01);

        uiv.calculatePosition(60.0);

        assertEquals(24.6, uiv.getX(), 0.01);
        assertEquals(38.8, uiv.getY(), 0.01);

        uiv.calculatePosition(69.0);

        assertEquals(70.14, uiv.getX(), 0.01);
        assertEquals(6.04, uiv.getY(), 0.01);
    }

    /**
     * Test for calculating relative position on a link
     */
    @Test
    void testCalculateRelativeLinkPosition() {
        Person p = new MSPerson(0, "M", 20, "", "", "", new ArrayList<>());
        Vehicle v = new MSVehicle(0, new ArrayList<>());
        Node n0 = new MSNode(0, 24.6, 38.8, "");
        Node n1 = new MSNode(1, 75.2, 2.4, "");
        Facility f0 = new MSFacility(0, 41.80, 26.42, 0); // 0.34
        Facility f1 = new MSFacility(1, 66.60, 8.59, 0); // 0.83
        Link l = new MSLink(3.0, 1.0, 100.0, "", "", new HashSet<>(), 0, 0, n0, 1, n1, Math.sqrt(Math.pow((75.2 - 24.6), 2) + Math.pow((2.4 - 38.8), 2)));

        List vevents = v.getRelatedEvents();
        vevents.add(new MSPersonEntersVehicle(0, 0.0, "PersonEntersVehicle", 24.6, 38.8, 0, 0, -1, -1, new HashMap<>(), v, p));
        vevents.add(new MSVehicleEntersLink(1, 10.0, "entered link", 24.6, 38.8, -1, 0, 0, -1, new HashMap<>(), v, l));
        Event vlt0 = new MSVehicleLeavesLink(2, 20.0, "vehicle leaves traffic", 41.80, 26.42, -1, 0, 0, -1, new HashMap<>(), v, l);
        vlt0.setRelativePosition(0.34);
        vevents.add(vlt0);
        vevents.add(new MSPersonLeavesVehicle(3, 30.0, "PersonLeavesVehicle", 41.80, 26.42, 0, 0, -1, -1, new HashMap<>(), v, p));
        vevents.add(new MSPersonEntersVehicle(6, 60.0, "PersonEntersVehicle", 41.80, 26.42, 0, 0, -1, -1, new HashMap<>(), v, p));
        Event vet0 = new MSVehicleEntersLink(7, 70.0, "vehicle enters traffic", 41.80, 26.42, -1, 0, 0, -1, new HashMap<>(), v, l);
        vet0.setRelativePosition(0.34);
        vevents.add(vet0);
        Event vlt1 = new MSVehicleLeavesLink(8, 80.0, "vehicle leaves traffic", 66.60, 8.59, -1, 0, 0, -1, new HashMap<>(), v, l);
        vlt1.setRelativePosition(0.83);
        vevents.add(vlt1);
        vevents.add(new MSPersonLeavesVehicle(9, 90.0, "PersonLeavesVehicle", 66.60, 8.59, 0, 0, -1, -1, new HashMap<>(), v, p));
        vevents.add(new MSPersonEntersVehicle(12, 120.0, "PersonEntersVehicle", 66.60, 8.59, 0, 0, -1, -1, new HashMap<>(), v, p));
        Event vet1 = new MSVehicleEntersLink(13, 130.0, "vehicle enters traffic", 66.60, 8.59, -1, 0, 0, -1, new HashMap<>(), v, l);
        vet1.setRelativePosition(0.83);
        vevents.add(vet1);
        vevents.add(new MSVehicleLeavesLink(14, 140.0, "left link", 75.2, 2.4, -1, 0, 0, -1, new HashMap<>(), v, l));
        vevents.add(new MSPersonLeavesVehicle(15, 150.0, "PersonLeavesVehicle", 75.2, 2.4, 0, 0, -1, -1, new HashMap<>(), v, p));
        v.setRelatedEvents(vevents);

        UIPerson uip = new UIPerson(p);
        UIVehicle uiv = new UIVehicle(v);

        uip.getInVehicle(uiv);
        uiv.calculatePosition(13.6);

        assertEquals(30.79, uiv.getX(), 0.01);
        assertEquals(34.34, uiv.getY(), 0.01);

        uiv.calculatePosition(45.7);

        assertEquals(41.80, uiv.getX(), 0.01);
        assertEquals(26.42, uiv.getY(), 0.01);

        uiv.calculatePosition(74.2);

        assertEquals(52.22, uiv.getX(), 0.01);
        assertEquals(18.93, uiv.getY(), 0.01);

        uiv.calculatePosition(127.9);

        assertEquals(66.60, uiv.getX(), 0.01);
        assertEquals(8.59, uiv.getY(), 0.01);

        uiv.calculatePosition(138.7);

        assertEquals(74.08, uiv.getX(), 0.01);
        assertEquals(3.20, uiv.getY(), 0.01);
    }

    /**
     * Test the calcul of a person's position before his first related event
     */
    @Test
    void testCalculateStartingPosition() {
        Person p = new MSPerson(0, "M", 20, "", "", "", new ArrayList<>());
        Vehicle v = new MSVehicle(0, new ArrayList<>());
        Node n0 = new MSNode(0, 24.6, 38.8, "");
        Node n1 = new MSNode(0, 75.2, 2.4, "");
        Link l = new MSLink(3.0, 1.0, 100.0, "", "", new HashSet<>(), 0, 0, n0, 1, n1, Math.sqrt(Math.pow((75.2 - 24.6), 2) + Math.pow((2.4 - 38.8), 2)));

        List events = v.getRelatedEvents();
        events.add(new MSPersonEntersVehicle(2, 4.0, "PersonEntersVehicle", 24.6, 38.8, 0, 0, -1, -1, new HashMap<>(), v, p));
        events.add(new MSVehicleEntersLink(0, 10.0, "entered link", 24.6, 38.8, -1, 0, 0, -1, new HashMap<>(), v, l));
        events.add(new MSVehicleLeavesLink(1, 20.0, "left link", 75.2, 2.4, -1, 0, 0, -1, new HashMap<>(), v, l));
        events.add(new MSPersonLeavesVehicle(3, 24.0, "PersonLeavesVehicle", 75.2, 2.4, 0, 0, -1, -1, new HashMap<>(), v, p));
        v.setRelatedEvents(events);

        UIVehicle uiv = new UIVehicle(v);

        uiv.calculatePosition(3.6);

        assertEquals(24.6, uiv.getX(), 0.01);
        assertEquals(38.8, uiv.getY(), 0.01);
    }

    /**
     * Test the calcul of a person's position after his last related event
     */
    @Test
    void testCalculateEndingPosition() {
        Person p = new MSPerson(0, "M", 20, "", "", "", new ArrayList<>());
        Vehicle v = new MSVehicle(0, new ArrayList<>());
        Node n0 = new MSNode(0, 24.6, 38.8, "");
        Node n1 = new MSNode(0, 75.2, 2.4, "");
        Link l = new MSLink(3.0, 1.0, 100.0, "", "", new HashSet<>(), 0, 0, n0, 1, n1, Math.sqrt(Math.pow((75.2 - 24.6), 2) + Math.pow((2.4 - 38.8), 2)));

        List events = v.getRelatedEvents();
        events.add(new MSPersonEntersVehicle(2, 4.0, "PersonEntersVehicle", 24.6, 38.8, 0, 0, -1, -1, new HashMap<>(), v, p));
        events.add(new MSVehicleEntersLink(0, 10.0, "entered link", 24.6, 38.8, -1, 0, 0, -1, new HashMap<>(), v, l));
        events.add(new MSVehicleLeavesLink(1, 20.0, "left link", 75.2, 2.4, -1, 0, 0, -1, new HashMap<>(), v, l));
        events.add(new MSPersonLeavesVehicle(3, 24.0, "PersonLeavesVehicle", 75.2, 2.4, 0, 0, -1, -1, new HashMap<>(), v, p));
        v.setRelatedEvents(events);

        UIVehicle uiv = new UIVehicle(v);

        uiv.calculatePosition(24.6);

        assertEquals(75.2, uiv.getX(), 0.01);
        assertEquals(2.4, uiv.getY(), 0.01);
    }

    /**
     * Test that the calcul of the position doesn't process while the uiperson is hidden
     */
    @Test
    void testCalculatePositionWhileHidden() {
        Person p = new MSPerson(0, "M", 20, "", "", "", new ArrayList<>());
        Vehicle v = new MSVehicle(0, new ArrayList<>());
        Node n0 = new MSNode(0, 24.6, 38.8, "");
        Node n1 = new MSNode(0, 75.2, 2.4, "");
        Link l = new MSLink(3.0, 1.0, 100.0, "", "", new HashSet<>(), 0, 0, n0, 1, n1, Math.sqrt(Math.pow((75.2 - 24.6), 2) + Math.pow((2.4 - 38.8), 2)));

        List events = v.getRelatedEvents();
        events.add(new MSPersonEntersVehicle(2, 4.0, "PersonEntersVehicle", 24.6, 38.8, 0, 0, -1, -1, new HashMap<>(), v, p));
        events.add(new MSVehicleEntersLink(0, 10.0, "entered link", 24.6, 38.8, -1, 0, 0, -1, new HashMap<>(), v, l));
        events.add(new MSVehicleLeavesLink(1, 20.0, "left link", 75.2, 2.4, -1, 0, 0, -1, new HashMap<>(), v, l));
        events.add(new MSPersonLeavesVehicle(3, 24.0, "PersonLeavesVehicle", 75.2, 2.4, 0, 0, -1, -1, new HashMap<>(), v, p));
        v.setRelatedEvents(events);

        UIPerson uip = new UIPerson(p);
        UIVehicle uiv = new UIVehicle(v);

        uiv.calculatePosition(3.6);

        assertEquals(24.6, uiv.getX(), 0.01);
        assertEquals(38.8, uiv.getY(), 0.01);

        uiv.hide();
        uip.getInVehicle(uiv);

        uiv.calculatePosition(13.6);

        assertEquals(24.6, uiv.getX(), 0.01);
        assertEquals(38.8, uiv.getY(), 0.01);
    }

    /**
     * Test the position with desordered events
     */
    @Test
    void testCalculatePositionWithDesorderedEvents() {
        Person p = new MSPerson(0, "M", 20, "", "", "", new ArrayList<>());
        Vehicle v = new MSVehicle(0, new ArrayList<>());
        Node n0 = new MSNode(0, 24.6, 38.8, "");
        Node n1 = new MSNode(0, 75.2, 2.4, "");
        Link l = new MSLink(3.0, 1.0, 100.0, "", "", new HashSet<>(), 0, 0, n0, 1, n1, Math.sqrt(Math.pow((75.2 - 24.6), 2) + Math.pow((2.4 - 38.8), 2)));

        List events = v.getRelatedEvents();
        events.add(new MSVehicleEntersLink(0, 10.0, "entered link", 24.6, 38.8, -1, 0, 0, -1, new HashMap<>(), v, l));
        events.add(new MSPersonLeavesVehicle(3, 24.0, "PersonLeavesVehicle", 75.2, 2.4, 0, 0, -1, -1, new HashMap<>(), v, p));
        events.add(new MSVehicleLeavesLink(1, 20.0, "left link", 75.2, 2.4, -1, 0, 0, -1, new HashMap<>(), v, l));
        events.add(new MSPersonEntersVehicle(2, 4.0, "PersonEntersVehicle", 24.6, 38.8, 0, 0, -1, -1, new HashMap<>(), v, p));
        v.setRelatedEvents(events);

        UIPerson uip = new UIPerson(p);
        UIVehicle uiv = new UIVehicle(v);
        uip.getInVehicle(uiv);

        uiv.calculatePosition(13.6);

        assertEquals(42.816, uiv.getX(), 0.01);
        assertEquals(25.696, uiv.getY(), 0.01);
    }

    /**
     * Test the position without any event
     */
    @Test
    void testCalculatePositionWithoutEvent() {
        Vehicle v = new MSVehicle();
        UIVehicle uiv = new UIVehicle(v);

        uiv.display();
        assertTrue(uiv.isDisplayed());

        uiv.calculatePosition(100.0);

        assertFalse(uiv.isDisplayed());
    }

    /**
     * Test the position with running events
     */
    @Test
    void testCalculatePositionRunningEvents() {
        Person p = new MSPerson(0, "M", 20, "", "", "", new ArrayList<>());
        Vehicle v = new MSVehicle(0, new ArrayList<>());
        Node n0 = new MSNode(0, 24.6, 38.8, "");
        Node n1 = new MSNode(0, 75.2, 2.4, "");
        Link l = new MSLink(3.0, 1.0, 100.0, "", "", new HashSet<>(), 0, 0, n0, 1, n1, Math.sqrt(Math.pow((75.2 - 24.6), 2) + Math.pow((2.4 - 38.8), 2)));

        List vevents = v.getRelatedEvents();
        List pevents = p.getRelatedEvents();
        MSPersonEntersVehicle e1 = new MSPersonEntersVehicle(2, 4.0, "PersonEntersVehicle", 24.6, 38.8, 0, 0, -1, -1, new HashMap<>(), v, p);
        MSVehicleEntersLink e2 = new MSVehicleEntersLink(0, 10.0, "entered link", 24.6, 38.8, -1, 0, 0, -1, new HashMap<>(), v, l);
        MSVehicleLeavesLink e3 = new MSVehicleLeavesLink(1, 20.0, "left link", 75.2, 2.4, -1, 0, 0, -1, new HashMap<>(), v, l);
        MSPersonLeavesVehicle e4 = new MSPersonLeavesVehicle(3, 24.0, "PersonLeavesVehicle", 75.2, 2.4, 0, 0, -1, -1, new HashMap<>(), v, p);
        vevents.add(e1);vevents.add(e2);vevents.add(e3);vevents.add(e4);
        pevents.add(e1);pevents.add(e4);
        v.setRelatedEvents(vevents);
        p.setRelatedEvents(pevents);

        UIVehicle uiv = new UIVehicle(v);
        UIPerson uip = new UIPerson(p);
        UILink uil = new UILink(l);

        List<RunnableEvent> runnableEvents =  new ArrayList<>();
        runnableEvents.add(new RunnablePersonEntersVehicle(e1, uiv, uip));
        runnableEvents.add(new RunnableVehicleEntersLink(e2, uiv, uil));
        runnableEvents.add(new RunnableVehicleLeavesLink(e3, uiv, uil));
        runnableEvents.add(new RunnablePersonLeavesVehicle(e4, uiv, uip));

        for(RunnableEvent revent : runnableEvents) {
            if(revent.event.getTime() < 13.6) {
                revent.execute();
            } else {
                break;
            }
        }

        uiv.calculatePosition(13.6);

        assertEquals(42.816, uiv.getX(), 0.01);
        assertEquals(25.696, uiv.getY(), 0.01);
    }
}