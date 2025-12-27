package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OptionsParserTest {
    final private String[] args= {"f","b","r","l"};
    final private MoveDirection[] moveDirections= {MoveDirection.FORWARD, MoveDirection.BACKWARD, MoveDirection.RIGHT, MoveDirection.LEFT};
    @Test
    void czyWartosciZgadzajaSie(){
        //when
        List<MoveDirection> moveDirectiontest = OptionsParser.parse(args);
        //then
        assertEquals(moveDirections[0],moveDirectiontest.get(0));
        assertEquals(moveDirections[1],moveDirectiontest.get(1));
        assertEquals(moveDirections[2],moveDirectiontest.get(2));
        assertEquals(moveDirections[3],moveDirectiontest.get(3));
    }//napisanie assertEquals(moveDirections,moveDirectiontest) jest niemozliwe, inne typy

    @Test
    void czyDzialaZJednaZlaWartoscia(){
        String[] args2= {"f","b","r","j","l"};
        List<MoveDirection> moveDirectiontest2 = OptionsParser.parse(args2);
        assertEquals(moveDirections[0],moveDirectiontest2.get(0));
        assertEquals(moveDirections[1],moveDirectiontest2.get(1));
        assertEquals(moveDirections[2],moveDirectiontest2.get(2));
        assertEquals(moveDirections[3],moveDirectiontest2.get(3));
    } //napisanie assertEquals(moveDirections,moveDirectiontest2) jest niemozliwe, inne typy

    @Test
    void czyDzialaZWielomaZlaWartosciami(){
        String[] args2= {"f",",","a,","b","xD","r","j","l"};
        List<MoveDirection> moveDirectiontest2 = OptionsParser.parse(args2);
        assertEquals(moveDirections[0],moveDirectiontest2.get(0));
        assertEquals(moveDirections[1],moveDirectiontest2.get(1));
        assertEquals(moveDirections[2],moveDirectiontest2.get(2));
        assertEquals(moveDirections[3],moveDirectiontest2.get(3));
    }//napisanie assertEquals(moveDirections,moveDirectiontest2) jest niemozliwe, inne typy

}