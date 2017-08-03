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

    public void fillStorage(Bird bird, int quantity){
        if (birdStorageService.checkBirdExists(bird)){
            birdStorageService.fillBirdStore(bird, quantity);
        }else{
            birdStorageService.addBird(bird, new Double(0), quantity);
        }
    }

    public void addNewBird(Bird bird, int quantity, Double cost){
        birdStorageService.addBird(bird, cost, quantity);
    }

    public void setBirdPrice(String name, Double price){
        birdStorageService.setBirdCost(name, price);
    }

    public Bird buyBird(String name){
        Bird bird = birdStorageService.getBirdFromStore(name);
        if (bird == null){
            System.out.println("Sorry, we don't have such bird");
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
