import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author o876
 */
public interface RoomManager extends Remote{
    List<Room> getRoomsAvailable() throws RemoteException, SQLException;
    boolean hotelClientBook(int roomType, String guestName) throws RemoteException, SQLException;
    List<String> hotelClientGuests() throws RemoteException;
    List<String> hotelClientRevenue() throws RemoteException;
    
    }
