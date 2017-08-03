package service;

import domain.Bird;
import domain.Storage;

/**
 * Created by Red8 on 03/08/2017.
 */
public class MainBirdStorageService implements BirdStorageService {
    private Storage linkedStorage;

    public MainBirdStorageService(Storage linkedStorage){
        this.linkedStorage = linkedStorage;
    }

    public void setLinkedStorage(Storage storage){
        linkedStorage = storage;
    }

    @Override
    public void addBird(Bird b) {
        b.setPrice(new Double(0));
        linkedStorage.addStorageObject(b, 0);
    }

    @Override
    public void addBird(Bird b, Double price) {
        b.setPrice(price);
        linkedStorage.addStorageObject(b, 0);
    }

    @Override
    public void addBird(Bird b, Double price, int initialQuantity) {
        b.setPrice(price);
        linkedStorage.addStorageObject(b, initialQuantity);
    }

    @Override
    public Bird getBirdFromStore(String name) {
        Object result = linkedStorage.findObjByName(name).getStorageObject();
        if (result instanceof Bird){
            return (Bird) result;
        }
        System.out.println("Bird not found");
        return null;
    }

    @Override
    public Double getBirdPrice(String name) {
        Object result = linkedStorage.findObjByName(name).getStorageObject();
        if (result instanceof Bird){
            return ((Bird) result).getPrice();
        }
        System.out.println("Bird not found");
        return null;
    }

    @Override
    public int getStatisticSold(Bird bird) {
        return linkedStorage.findObjByName(bird.getName()).getSoldNumber();
    }

    @Override
    public int getStatisticLeft(Bird bird) {
        return linkedStorage.findObjByName(bird.getName()).getStorageLeftNumber();
    }

    @Override
    public void printReportLeft() {

    }

    @Override
    public void printReportSold() {

    }

    @Override
    public void printReportFull() {

    }

    @Override
    public void fillBirdStore(Bird bird, int quantity) {

    }

    @Override
    public void fillBirdStore(String name, int quantity) {

    }

    @Override
    public boolean checkBirdExists(Bird bird) {
        return false;
    }

    @Override
    public boolean checkBirdExists(String name) {
        return false;
    }

    @Override
    public void setBirdCost(Bird bird, Double price) {

    }

    @Override
    public void setBirdCost(String name, Double price) {

    }
}
