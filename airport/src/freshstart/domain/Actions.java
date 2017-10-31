package freshstart.domain;

/**
 * Created by DoNotPanic-NB on 31.10.2017.
 */
public enum Actions {
    PLANE_TAKEOFF(TimeInMilliSec.TENSECOND.getTimeInMilliSecs()), STANDBY(TimeInMilliSec.MINUTE.getTimeInMilliSecs());

    private int duration;

    Actions(int duration){
        this.duration = duration;
    }

    public void doAction(){
        try{
            Thread.sleep(this.duration);
        }catch(InterruptedException e){

        }
    }
}
