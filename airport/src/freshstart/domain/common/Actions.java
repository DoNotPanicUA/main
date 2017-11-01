package freshstart.domain.common;


/**
 * Created by DoNotPanic-NB on 31.10.2017.
 */
public enum Actions {
    PLANE_TAKEOFF(TimeInMilliSec.TENSECOND.getTimeInMilliSecs()), STANDBY(TimeInMilliSec.MINUTE.getTimeInMilliSecs()),
    PLANE_REQUESTRADIOTOWER(TimeInMilliSec.FIVESECONDS.getTimeInMilliSecs()), PLANE_LAND(TimeInMilliSec.TENSECOND.getTimeInMilliSecs()),
    PLANE_FLYMIN(TimeInMilliSec.MINUTE.getTimeInMilliSecs()), STANDBY_5SEC(TimeInMilliSec.FIVESECONDS.getTimeInMilliSecs());

    private int duration;

    public int getDurationSec(){
        return this.duration/1000;
    }

    public int getDurationMilliSec(){
        return this.duration;
    }

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
