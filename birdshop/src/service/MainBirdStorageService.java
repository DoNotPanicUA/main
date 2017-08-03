package service;

import domain.Bird;
import domain.Named;
import domain.Priceable;
import domain.Storage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
    public int getStatisticSold(String name) {
        return linkedStorage.findObjByName(name).getSoldNumber();
    }

    @Override
    public int getStatisticLeft(String name) {
        return linkedStorage.findObjByName(name).getStorageLeftNumber();
    }

    @Override
    public void printReportLeft() {
        Iterator<Storage.StorageObject> iterator = linkedStorage.getIterator();
        int number = 1;

        System.out.println();
        System.out.println("------------- Storage state -------------");
        while(iterator.hasNext()){
            Storage.StorageObject storageObject = iterator.next();

            System.out.println();
            System.out.println(number + ": " + storageObject.getObjectName() + " - " + storageObject.getStorageLeftNumber());
            number++;
        }
        System.out.println("-----------------------------------------");

    }

    @Override
    public void printReportSold() {
        Iterator<Storage.StorageObject> iterator = linkedStorage.getIterator();
        int number = 1;

        System.out.println();
        System.out.println("----------------- Sales -----------------");
        while(iterator.hasNext()){
            Storage.StorageObject storageObject = iterator.next();

            System.out.println();
            System.out.print(number + ": " + storageObject.getObjectName() + " Sold: " + storageObject.getSoldNumber());
            if (storageObject.getStorageObject() instanceof Priceable){
                System.out.println(" Price: " + ((Priceable) storageObject.getStorageObject()).getPrice() + " Total sum: " + ((Priceable) storageObject.getStorageObject()).getPrice() * storageObject.getSoldNumber());
            }else{
                System.out.println(" Price: FREE");
            }
            number++;
        }
        System.out.println("-----------------------------------------");

    }

    @Override
    public void printReportFull() {
        Iterator<Storage.StorageObject> iterator = linkedStorage.getIterator();
        int number = 1;

        System.out.println();
        System.out.println("----------------- Full report -----------------");
        while(iterator.hasNext()){
            Storage.StorageObject storageObject = iterator.next();

            System.out.println();
            System.out.print(number + ": " + storageObject.getObjectName() + " Sold: " + storageObject.getSoldNumber());
            if (storageObject.getStorageObject() instanceof Priceable){
                System.out.print(" Price: " + ((Priceable) storageObject.getStorageObject()).getPrice() + " Total sum: " + ((Priceable) storageObject.getStorageObject()).getPrice() * storageObject.getSoldNumber());
            }else{
                System.out.print(" Price: FREE");
            }
            System.out.println(" Storage left: " + storageObject.getStorageLeftNumber());
            number++;
        }
        System.out.println("-----------------------------------------------");
    }

    @Override
    public void fillBirdStore(Bird bird, int quantity) {
        fillBirdStore(bird.getName(), quantity);
    }

    @Override
    public void fillBirdStore(String name, int quantity) {
        Storage.StorageObject storageObject = linkedStorage.findObjByName(name);
        storageObject.setStorageLeftNumber(storageObject.getStorageLeftNumber() + quantity);
        linkedStorage.updateStoredObject(storageObject);
    }

    @Override
    public boolean checkBirdExists(Bird bird) {
        return checkBirdExists(bird.getName());
    }

    @Override
    public boolean checkBirdExists(String name) {
        if (linkedStorage.findObjByName(name) != null){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public List<Bird> getAllBirds() {
        List<Bird> resultList = new ArrayList<>();
        Iterator<Storage.StorageObject> iterator = linkedStorage.getIterator();

        while (iterator.hasNext()){
            Object obj = iterator.next().getStorageObject();
            if (obj instanceof Bird){
                resultList.add((Bird) obj);
            }
        }

        return resultList;
    }

    @Override
    public void setBirdPrice(Bird bird, Double price) {
        Storage.StorageObject storageObject = linkedStorage.findObjByName(bird.getName());
        if (storageObject != null){
            bird.setPrice(price);
            linkedStorage.updateStoredObject(bird, storageObject.getStorageUID());
        }
    }

    @Override
    public void setBirdPrice(String name, Double price) {
        Storage.StorageObject storageObject = linkedStorage.findObjByName(name);
        if (storageObject.getStorageObject() instanceof Bird){
                Bird bird = (Bird) storageObject.getStorageObject();
                bird.setPrice(price);
                linkedStorage.updateStoredObject(bird, storageObject.getStorageUID());
        }
    }

    @Override
    public Bird getBirdFromStorage(String name) {
        Storage.StorageObject storageObject = linkedStorage.findObjByName(name);
        Bird result = null;

        if (storageObject == null){
            return result;
        }

        if (storageObject.getStorageObject() instanceof  Bird){
            if (linkedStorage.sellObject(storageObject.getStorageUID())){
                result = (Bird) storageObject.getStorageObject();
            }else{
                result = null;
            }
        }else {
            result = null;
        }

        return result;
    }
}
