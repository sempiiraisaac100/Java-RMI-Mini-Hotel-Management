import java.io.Serializable;

public class Revenue implements Serializable {
    private int Type;
    private String Cost;

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public String getCost() {
        return Cost;
    }

    public void setCost(String cost) {
        Cost = cost;
    }
}
