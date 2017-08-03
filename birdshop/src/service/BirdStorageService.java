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

    Bird getBirdFromStorage(String name);

    int getStatisticSold(String name);
    int getStatisticLeft(String name);

    void printReportLeft();
    void printReportSold();
    void printReportFull();

    void fillBirdStore(Bird bird, int quantity);
    void fillBirdStore(String name, int quantity);

    boolean checkBirdExists(Bird bird);
    boolean checkBirdExists(String name);

    void setBirdPrice(Bird bird, Double price);
    void setBirdPrice(String name, Double price);

    List<Bird> getAllBirds();
}
