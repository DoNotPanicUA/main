import java.io.IOException;

/**
 * Created by DoNotPanic-NB on 19.06.2018.
 */
public class IMpl implements MainClass {
    @Override
    public IMpl method1(int s, Object obj) throws IOException{

        class OO{};

        OO o = new OO();

        Object obb = new IMpl(){
            @Override
            public IMpl method1(int s, Object obj) throws IOException {
                return super.method1(s, obj);
            }
        };
        return null;
    }

    private class S{}

    static void method2(){};

}
