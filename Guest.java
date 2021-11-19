import java.io.Serializable;

public class Guest implements Serializable {
    private String Name;

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

}
