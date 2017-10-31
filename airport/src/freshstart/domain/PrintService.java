package freshstart.domain;

import java.util.Date;

/**
 * Created by DoNotPanic-NB on 30.10.2017.
 */
public class PrintService {
    public static void printMessage(String messageText){
        System.out.println("["+new Date()+"] " + messageText);
    }

    public static void printMessageObj(String messageText, Object obj){
        String coorString = "";
        String name = "";

        if (obj instanceof Location){
            Coordinates coordinates = ((Location) obj).getCoordinates();
            coorString = (coordinates != null ? String.format("%-6s","[X:" + coordinates.getX() +", ") + String.format("%-6s","Y:"+ coordinates.getY()) +"]" : "");
        }

        if (obj instanceof Named){
            name = ((Named) obj).getObjectName();
        }

        printMessage((coorString != ""? coorString + " " : coorString) + (name != "" ? name + ": " : name ) + messageText);
    }
}
