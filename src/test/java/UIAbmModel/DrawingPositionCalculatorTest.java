package UIAbmModel;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DrawingPositionCalculatorTest {

    @Test
    void testGetXWithoutZoom1(){
        DrawingPositionCalculator.setMinX(0);
        DrawingPositionCalculator.setZoom(1);
        assertEquals(20, DrawingPositionCalculator.getX(0),0.1);
        assertEquals(60, DrawingPositionCalculator.getX(40),0.1);
    }

    @Test
    void testGetXWithoutZoom2(){
        DrawingPositionCalculator.setMinX(-100);
        DrawingPositionCalculator.setZoom(1);
        assertEquals(20, DrawingPositionCalculator.getX(-100),0.1);
        assertEquals(120, DrawingPositionCalculator.getX(0),0.1);
    }

    @Test
    void testGetXWithoutZoom3(){
        DrawingPositionCalculator.setMinX(100);
        DrawingPositionCalculator.setZoom(1);
        assertEquals(20, DrawingPositionCalculator.getX(100),0.1);
        assertEquals(40, DrawingPositionCalculator.getX(120),0.1);
    }

    @Test
    void testGetXWithZoom1(){
        DrawingPositionCalculator.setMinX(0);
        DrawingPositionCalculator.setZoom(2);
        assertEquals(20, DrawingPositionCalculator.getX(0),0.1);
        assertEquals(80, DrawingPositionCalculator.getX(120),0.1);
    }

    @Test
    void testGetXWithZoom2(){
        DrawingPositionCalculator.setMinX(-100);
        DrawingPositionCalculator.setZoom(2);
        assertEquals(20, DrawingPositionCalculator.getX(-100),0.1);
        assertEquals(70, DrawingPositionCalculator.getX(0),0.1);
    }

    @Test
    void testGetXWithZoom3(){
        DrawingPositionCalculator.setMinX(100);
        DrawingPositionCalculator.setZoom(2);
        assertEquals(20, DrawingPositionCalculator.getX(100),0.1);
        assertEquals(40, DrawingPositionCalculator.getX(140),0.1);
    }

    @Test
    void testGetYWithoutZoom1(){
        DrawingPositionCalculator.setMaxY(100);
        DrawingPositionCalculator.setMinY(0);
        DrawingPositionCalculator.setZoom(1);
        assertEquals(120, DrawingPositionCalculator.getY(0),0.1);
        assertEquals(20, DrawingPositionCalculator.getY(100),0.1);
        assertEquals(90, DrawingPositionCalculator.getY(30),0.1);
    }

    @Test
    void testGetYWithoutZoom2(){
        DrawingPositionCalculator.setMaxY(100);
        DrawingPositionCalculator.setMinY(-100);
        DrawingPositionCalculator.setZoom(1);
        assertEquals(220, DrawingPositionCalculator.getY(-100),0.1);
        assertEquals(20, DrawingPositionCalculator.getY(100),0.1);
        assertEquals(120, DrawingPositionCalculator.getY(0),0.1);
    }

    @Test
    void testGetYWithoutZoom3(){
        DrawingPositionCalculator.setMaxY(200);
        DrawingPositionCalculator.setMinY(100);
        DrawingPositionCalculator.setZoom(1);
        assertEquals(120, DrawingPositionCalculator.getY(100),0.1);
        assertEquals(20, DrawingPositionCalculator.getY(200),0.1);
        assertEquals(70, DrawingPositionCalculator.getY(150),0.1);
    }

    @Test
    void testGetYWithZoom1(){
        DrawingPositionCalculator.setMaxY(100);
        DrawingPositionCalculator.setMinY(0);
        DrawingPositionCalculator.setZoom(2);
        assertEquals(70, DrawingPositionCalculator.getY(0),0.1);
        assertEquals(20, DrawingPositionCalculator.getY(100),0.1);
        assertEquals(50, DrawingPositionCalculator.getY(40),0.1);
    }

    @Test
    void testGetYWithZoom2(){
        DrawingPositionCalculator.setMaxY(100);
        DrawingPositionCalculator.setMinY(-100);
        DrawingPositionCalculator.setZoom(2);
        assertEquals(120, DrawingPositionCalculator.getY(-100),0.1);
        assertEquals(20, DrawingPositionCalculator.getY(100),0.1);
        assertEquals(70, DrawingPositionCalculator.getY(0),0.1);
    }

    @Test
    void testGetYWithZoom3(){
        DrawingPositionCalculator.setMaxY(200);
        DrawingPositionCalculator.setMinY(100);
        DrawingPositionCalculator.setZoom(2);
        assertEquals(70, DrawingPositionCalculator.getY(100),0.1);
        assertEquals(20, DrawingPositionCalculator.getY(200),0.1);
        assertEquals(50, DrawingPositionCalculator.getY(140),0.1);
    }

    @Test
    void testGetZoom(){
        DrawingPositionCalculator.setZoom(2);
        assertEquals(2, DrawingPositionCalculator.getZoom());
    }

    @Test
    void testGetPaneSize1(){
        DrawingPositionCalculator.setMaxY(100);
        DrawingPositionCalculator.setMinY(0);
        DrawingPositionCalculator.setMaxX(100);
        DrawingPositionCalculator.setMinX(0);
        DrawingPositionCalculator.setZoom(1);
        assertEquals(140, DrawingPositionCalculator.getPaneXSize());
        assertEquals(140, DrawingPositionCalculator.getPaneYSize());
    }

    @Test
    void testGetPaneSize2(){
        DrawingPositionCalculator.setMaxY(100);
        DrawingPositionCalculator.setMinY(0);
        DrawingPositionCalculator.setMaxX(100);
        DrawingPositionCalculator.setMinX(0);
        DrawingPositionCalculator.setZoom(2);
        assertEquals(90, DrawingPositionCalculator.getPaneXSize());
        assertEquals(90, DrawingPositionCalculator.getPaneYSize());
    }

    @Test
    void testGetPaneSize3(){
        DrawingPositionCalculator.setMaxY(100);
        DrawingPositionCalculator.setMinY(-100);
        DrawingPositionCalculator.setMaxX(100);
        DrawingPositionCalculator.setMinX(-100);
        DrawingPositionCalculator.setZoom(2);
        assertEquals(140, DrawingPositionCalculator.getPaneXSize());
        assertEquals(140, DrawingPositionCalculator.getPaneYSize());
    }

}