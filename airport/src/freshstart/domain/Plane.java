package freshstart.domain;

import java.util.Date;

/**
 * Created by aleonets on 23.08.2017.
 */
public class Plane extends Thread{
    public void crashPlane(){
        System.out.println("["+new Date()+"] Plane: " + this + " has been crashed!!!");
        this.interrupt();
    }

    @Override
    public void run() {
        super.run();
        while (true){
        }
    }
}
