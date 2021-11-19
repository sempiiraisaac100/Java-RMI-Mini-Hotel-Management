import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author o876
 */

/**
 *RoomManager interface extending java.rmi.Remote to ensure
 *that its methods can be invoked from any remote process.
 *
 *And all its methods throwing java.rmi.RemoteException exception
 *to report any remote communication failures encountered.
 *
 *And SQLException to communicate any failures in connecting and passing queries to the mysql database
 */

public interface RoomManager extends Remote{

    //defining interface methods to be implemented by the RoomManagerImpl class

    List<Room> getRoomsAvailable() throws RemoteException, SQLException;

    boolean hotelClientBook(int roomType, String guestName) throws RemoteException, SQLException;

    List<Guest> hotelClientGuests() throws RemoteException, SQLException;

    List<Revenue> hotelClientRevenue() throws RemoteException, SQLException;
    
    }
