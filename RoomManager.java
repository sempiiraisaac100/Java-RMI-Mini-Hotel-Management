import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author o876
 */
public interface RoomManager extends Remote{
    public List<Rooms> getRoomsAvailbale() throws RemoteException, SQLException;
    public boolean hotelClientBook(int roomType, String guestName) throws RemoteException, SQLException;
    public List<String> hotelClientGuests() throws RemoteException;
    public List<String> hotelClientRevenue() throws RemoteException;
    
    }
