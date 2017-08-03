package service;

import domain.Bird;

import java.util.List;

/**
 * Created by Red8 on 03/08/2017.
 */
public interface BirdStorageService {

    void addBird(Bird b);
    void addBird(Bird b, Double price);
    void addBird(Bird b, Double price, int initialQuantity);

    Bird getBirdFromStore(String name);

    Double getBirdPrice(String name);

    int getStatisticSold(Bird bird);
    int getStatisticLeft(Bird bird);

    void printReportLeft();
    void printReportSold();
    void printReportFull();

    void fillBirdStore(Bird bird, int quantity);
    void fillBirdStore(String name, int quantity);

    boolean checkBirdExists(Bird bird);
    boolean checkBirdExists(String name);

    void setBirdCost(Bird bird, Double price);
    void setBirdCost(String name, Double price);

    List<Bird> getAllBirds();
}
