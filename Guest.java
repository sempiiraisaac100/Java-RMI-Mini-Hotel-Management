import java.io.Serializable;

//Room class is a data class serialized to ease the storing and sending of its objects to other processes
public class Guest implements Serializable {

    //declaring a variable only visible to this class
    private String Name;

    //creating methods that can be used to access declared private variable above
    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

}
