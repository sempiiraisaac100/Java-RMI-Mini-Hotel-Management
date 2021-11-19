import java.io.Serializable;

//Room class is a data class serialized to ease the storing and sending of its objects to other processes
public class Room implements Serializable {

    //declaring variables only visible to this class
    private int Type,Capacity;
    private String Size;
    private String Cost;

    //creating methods that can be used to access declared private variables above
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
