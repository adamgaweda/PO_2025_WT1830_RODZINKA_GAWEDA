package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;

import java.util.ArrayList;
import java.util.List;

public class OptionsParser {
    public static List<MoveDirection> parse(String[] args){
        List<MoveDirection> result = new ArrayList<MoveDirection>(args.length);;
        for (String b:args){
            switch (b) {
                case "f":
                    result.add(MoveDirection.FORWARD);
                    break;
                case "b":
                    result.add(MoveDirection.BACKWARD);
                    break;
                case "r":
                    result.add(MoveDirection.RIGHT);
                    break;
                case "l":
                    result.add(MoveDirection.LEFT);
                    break;
                default:
                    throw new IllegalArgumentException(b + " is not legal move specification");
            }
        }
        return result;
    }
}
