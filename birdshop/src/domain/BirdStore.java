package domain;

import service.BirdStorageService;

/**
 * Created by Red8 on 03/08/2017.
 */
public class BirdStore {
    private BirdStorageService birdStorageService;
    private Storage shopStorage = new Storage();

    public BirdStore(BirdStorageService birdStorageService){
        setBirdStorageService(birdStorageService);
    }

    public void fillStorage(String name, int quantity){
        if (birdStorageService.checkBirdExists(name)){
            birdStorageService.fillBirdStore(name, quantity);
        }else{
            birdStorageService.addBird(new Bird(name), 0D, quantity);
        }
    }

    public void addNewBird(Bird bird, int quantity, Double cost){
        birdStorageService.addBird(bird, cost, quantity);
    }

    public void setBirdPrice(String name, Double price){
        birdStorageService.setBirdPrice(name, price);
    }

    public Bird buyBird(String name){
        Bird bird = birdStorageService.getBirdFromStorage(name);
        if (bird == null){
            System.out.println("Sorry, we don't have a " + name);
            return null;
        }else{
            return bird;
        }
    }

    public void sendTaxReport(){
        birdStorageService.printReportSold();
    }

    public void sendCompanyReport(){
        birdStorageService.printReportFull();
    }

    public void sendStorageReport(){
        birdStorageService.printReportLeft();
    }

    public void setBirdStorageService(BirdStorageService birdStorageService){
        this.birdStorageService = birdStorageService;
    }
}
