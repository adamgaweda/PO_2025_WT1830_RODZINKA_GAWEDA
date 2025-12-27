package agh.ics.oop;

import agh.ics.oop.model.Vector2d;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Vector2dTest {
    // sprawdzanie funkcji "czy + 'nazwa funcji ktora testuje'"
    @Test
    void czyEquals(){
        //when
        Vector2d v1 = new Vector2d(0,0);
        Vector2d v2 = new Vector2d(0,0);
        //then
        assertEquals(v1,v2);
    }

    @Test
    void czyToString(){
        //when
        Vector2d v1 = new Vector2d(0,0);
        //then
        assertEquals("(0,0)", v1.toString());
    }

    @Test
    void czyPrecedes(){
        //when
        Vector2d v1 = new Vector2d(0,0);
        Vector2d v2 = new Vector2d(1,0);
        //then
        assertTrue(v2.precedes(v1));
    }

    @Test
    void czyFollows(){
        // when
        Vector2d v1 = new Vector2d(0,0);
        Vector2d v2 = new Vector2d(1,0);
        // then
        assertTrue(v1.follows(v2));
    }

    @Test
    void czyUpperRight(){
        //when
        Vector2d v1 = new Vector2d(0,0);
        Vector2d v2 = new Vector2d(0,1);
        //then
        assertEquals(new Vector2d(0,1), v1.upperRight(v2));

    }

    @Test
    void czyUpperRight2(){
        //when
        Vector2d v2 = new Vector2d(0,1);
        Vector2d v3 = new Vector2d(1,0);
        //then
        assertEquals(new Vector2d(1,1), v2.upperRight(v3));
    }

    @Test
    void czyLowerLeft(){
        //when
        Vector2d v1 = new Vector2d(0,0);
        Vector2d v2 = new Vector2d(0,1);
        Vector2d v3 = new Vector2d(1,0);
        //then
        assertEquals(new Vector2d(0,0), v1.lowerLeft(v2));
        assertEquals(new Vector2d(0,0), v2.lowerLeft(v3));
    }

    @Test
    void czyLowerLeft2(){
        //when
        Vector2d v2 = new Vector2d(0,1);
        Vector2d v3 = new Vector2d(1,0);
        //then
        assertEquals(new Vector2d(0,0), v2.lowerLeft(v3));
    }

    @Test
    void czyLowerLeft3(){
        //when
        Vector2d v2 = new Vector2d(0,1);
        Vector2d v3 = new Vector2d(0,3);
        //then
        assertEquals(new Vector2d(0,1), v2.lowerLeft(v3));
    }

    @Test
    void czyAdd(){
        // when
        Vector2d v1 = new Vector2d(7,2);
        Vector2d v2 = new Vector2d(3,6);
        //then
        assertEquals(new Vector2d(10,8), v1.add(v2));
    }

    @Test
    void czySub(){
        //when
        Vector2d v1 = new Vector2d(7,2);
        Vector2d v2 = new Vector2d(3,6);
        //then
        assertEquals(new Vector2d(4,-4),v1.sub(v2));
    }

    @Test
    void czyOpposite(){
        Vector2d v1 = new Vector2d(7,2);
        assertEquals(new Vector2d(-7,-2), v1.opposite());
    }
}