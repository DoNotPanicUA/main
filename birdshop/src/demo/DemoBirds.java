package demo;

import domain.Bird;
import domain.BirdStore;
import domain.Storage;
import service.MainBirdStorageService;

/**
 * Created by DoNotPanic-UA on 03.08.2017.
 */
public class DemoBirds {

    public static void main(String[] args) {
        BirdStore birdsForAll = new BirdStore(new MainBirdStorageService(new Storage()));

        birdsForAll.addNewBird(new Bird("Parrot"), 5, 500D );
        birdsForAll.addNewBird(new Bird("Sparrow"), 100, 13D );
        birdsForAll.addNewBird(new Bird("Penguin"), 1, 100500D );

        birdsForAll.setBirdPrice("Parrot", 130D);
        birdsForAll.fillStorage("Sparrow", 50);

        birdsForAll.buyBird("Penguin");
        birdsForAll.buyBird("Penguin");

        birdsForAll.buyBird("Parrot");
        birdsForAll.buyBird("Parrot");
        birdsForAll.buyBird("Parrot");
        birdsForAll.buyBird("Parrot");

        birdsForAll.buyBird("Sparrow");
        birdsForAll.buyBird("Sparrow");
        birdsForAll.buyBird("Sparrow");
        birdsForAll.buyBird("Sparrow");
        birdsForAll.buyBird("Sparrow");
        birdsForAll.buyBird("Sparrow");
        birdsForAll.buyBird("Sparrow");

        birdsForAll.sendCompanyReport();

        birdsForAll.sendStorageReport();

        birdsForAll.sendTaxReport();

    }

}
