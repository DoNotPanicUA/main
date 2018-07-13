package com.donotpanic.airport.domain.location;


public class GeoLocationService {

    public static Double calculateDistance(Coordinates coordinatesTo, Coordinates coordinatesFrom){
        return Math.sqrt(Math.pow(coordinatesTo.getX() - coordinatesFrom.getX(), 2)+
                Math.pow(coordinatesTo.getY() - coordinatesFrom.getY(), 2));
    }

    public static Direction getDirection(Coordinates coordinatesTo, Coordinates coordinatesFrom){
        double sideX = coordinatesTo.getX() - coordinatesFrom.getX();
        double sideY = coordinatesTo.getY() - coordinatesFrom.getY();
        double hypo = calculateDistance(coordinatesTo, coordinatesFrom);
        return new Direction().setCoefX(sideX/hypo).setCoefY(sideY/hypo);
    }

    public static Coordinates calculateNewCoordinates(Coordinates currentCoordinates, Direction direction, double distance){
        double x = direction.getCoefX() * distance + currentCoordinates.getX();
        double y = direction.getCoefY() * distance + currentCoordinates.getY();
        return CoordinateFactory.getCoordinates(x, y);
    }

    public static Direction shiftCircleDirection(Direction currentDirection, int numberOfCircleSides){

        double x = currentDirection.getCoefX();
        double y = currentDirection.getCoefY();

        double hypo = Math.sqrt(Math.pow(x,2)+Math.pow(y,2));
        double angle;

        angle = Math.round(Math.toDegrees(Math.acos(x/hypo)));

        if (angle < 0){
            angle = 360 + angle;
        }

        if (y < 0){
            angle = 360-angle;
        }

        // Get new angle
        angle = angle - 360/numberOfCircleSides;

        if (angle < 0){
            angle = 360 + angle;
        }

        return new Direction().setCoefX(Math.cos(Math.toRadians(angle))*hypo).setCoefY(Math.sin(Math.toRadians(angle))*hypo);
    }
}
