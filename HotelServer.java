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

/**
 *HotelServer class extends java.rmi.server.UnicastRemoteObject,thereby inheriting the remote behavior
 *provided by the classes java.rmi.server.RemoteObject and java.rmi.server.RemoteServer
 */

public class HotelServer extends UnicastRemoteObject{

    String dbURL = "jdbc:mysql://localhost:3306/hotel";
    String username = "marv";
    String password = "@marvin256";


    //throwing java.rmi.RemoteException exception to report any remote communication failures encountered.
    public HotelServer()throws RemoteException, ClassNotFoundException, SQLException{

        //Connecting to the database
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

            //loading the Server
            new HotelServer();
            try {

                //This creates a new rmiregistry process at the localhost port 1099, so the URL should be
                //localhost:1099
                Registry reg = LocateRegistry.createRegistry(1099);

                //Rebinds the specified name: "rmi://localhost:1099/HotelService"
                // to a new remote object: new RoomManagerImpl()
                reg.rebind("rmi://localhost:1099/HotelService",new RoomManagerImpl());

                //Echoing server status
                System.out.println("Server is Ready");

            } catch (RemoteException e) {
                System.out.println("Received Exception: " + e);
            }
        }
    }