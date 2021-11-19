import java.io.Serializable;

//Revenue class is a data class serialized to ease the storing and sending of its objects to other processes
public class Revenue implements Serializable {

    //declaring variables only visible to this class
    private int Type;
    private int Cost;

    //creating methods that can be used to access declared private variables above
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
