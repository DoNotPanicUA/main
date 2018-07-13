/**
 * Created by DoNotPanic-NB on 19.06.2018.
 */
public class NewImpl extends IMpl {
    private static int num = 0;

    private final int myNum;

    public NewImpl(){
        NewImpl.num++;
        myNum = NewImpl.num;
    }

    @Override
    public int hashCode() {
        return myNum;
    }

    @Override
    public boolean equals(Object obj) {
        return true;
    }
}
