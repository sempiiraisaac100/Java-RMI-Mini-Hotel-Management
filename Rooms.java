import java.io.Serializable;

public class Rooms implements Serializable {
    private int Type,Capacity;
    private String Size;
    private String Cost;

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        this.Type = type;
    }

    public int getCapacity() {
        return Capacity;
    }

    public void setCapacity(int capacity) {
        this.Capacity = capacity;
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String size) {
        this.Size = size;
    }

    public String getCost() {
        return Cost;
    }

    public void setCost(String cost) {
        this.Cost = cost;
    }
}
