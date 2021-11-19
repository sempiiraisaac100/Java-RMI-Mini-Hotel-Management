import java.io.Serializable;

public class Revenue implements Serializable {
    private int Type;
    private int Cost;

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public int getCost() {
        return Cost;
    }

    public void setCost(int cost) {
        Cost = cost;
    }
}
