package domain;

/**
 * Created by Red8 on 03/08/2017.
 */
public class Bird implements Named, Priceable {

    private final String name;
    private Double price;

    public Bird(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object obj) {
        boolean result = false;

        if (obj instanceof Bird){
            if (this.name.equals(((Bird) obj).name)){
                result = true;
            }
        }
        return result;
    }
}
