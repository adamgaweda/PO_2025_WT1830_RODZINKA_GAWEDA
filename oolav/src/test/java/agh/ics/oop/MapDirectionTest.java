package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapDirectionTest {

    @Test
    void czyNextDlaNorth(){
        // when
        MapDirection m = MapDirection.North;
        // then
        assertEquals(MapDirection.East,m.next());
    }

    @Test
    void czyNextDlaSouth(){
        // when
        MapDirection m = MapDirection.South;
        //then
        assertEquals(MapDirection.West,m.next());
    }
    @Test
    void czyNextDlaWest(){
        //when
        MapDirection m = MapDirection.West;
        //then
        assertEquals(MapDirection.North,m.next());
    }
    @Test
    void czyNextDlaEast(){
        //when
        MapDirection m = MapDirection.East;
        //then
        assertEquals(MapDirection.South,m.next());
    }

    @Test
    void czyPrevDlaNorth(){
        // when
        MapDirection m = MapDirection.North;
        // then
        assertEquals(MapDirection.West,m.prev());
    }

    @Test
    void czyPrevDlaSouth(){
        // when
        MapDirection m = MapDirection.South;
        //then
        assertEquals(MapDirection.East,m.prev());
    }
    @Test
    void czyPrevDlaWest(){
        //when
        MapDirection m = MapDirection.West;
        //then
        assertEquals(MapDirection.South,m.prev());
    }
    @Test
    void czyPrevDlaEast(){
        //when
        MapDirection m = MapDirection.East;
        //then
        assertEquals(MapDirection.North,m.prev());
    }
}