import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author o876
 */
public class HotelServer extends UnicastRemoteObject{

    String dbURL = "jdbc:mysql://localhost:3306/hotel";
    String username = "marv";
    String password = "@marvin256";
    public HotelServer()throws RemoteException, ClassNotFoundException, SQLException{
    try {
        Class.forName("com.mysql.jdbc.Driver");
        Connection con= DriverManager.getConnection(dbURL, username, password);
        if(con!=null){
            System.out.println("connected");

        }else{
            System.out.println("not connected");
        }
                System.out.println("Connection is Ready");
    }catch (SQLException e){System.out.println("Exception "+ e);}


    }
        public static void main (String args [] ) throws RemoteException, SQLException, ClassNotFoundException {

            new HotelServer();
            try {

                Registry reg = LocateRegistry.createRegistry(1099);
                reg.rebind("rmi://localhost:1099/HotelService",new RoomManagerImpl());
                System.out.println("Server is Ready");

            } catch (RemoteException e) {
                System.out.println("Received Exception: " + e);
            }
        }
    }