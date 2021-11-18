import java.io.Serializable;
import java.rmi.RemoteException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author o876
 */
public class RoomManagerImpl extends java.rmi.server.UnicastRemoteObject implements RoomManager, Serializable {
    String dbURL = "jdbc:mysql://localhost:3306/hotel";
    String username = "marv";
    String password = "@marvin256";
    
    public RoomManagerImpl () throws RemoteException {
     super ();
    }

    @Override
    public List<Rooms> getRoomsAvailbale() throws RemoteException, SQLException {
        List <Rooms> list = new ArrayList<>();

        try{
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection con = DriverManager.getConnection(dbURL, username, password);
        Statement st = con.createStatement();
        String availableRooms = "SELECT * FROM Rooms";
        ResultSet rs = st.executeQuery(availableRooms);

        while (rs.next()) {
            int Type = rs.getInt("Type");
            int Capacity = rs.getInt("Capacity");
            String Size = rs.getString("Size");
            String Cost = rs.getString("Cost");

            Rooms room = new Rooms();
            room.setType(Type);
            room.setCapacity(Capacity);
            room.setSize(Size);
            room.setCost(Cost);
            list.add(room);
        }
        rs.close();
        return list;
        }

    @Override
    public boolean hotelClientBook(int roomType, String guestName) throws RemoteException, SQLException {

        try{
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection con = DriverManager.getConnection(dbURL, username, password);
        Statement st = con.createStatement();
        String availableRooms = "SELECT Type,Capacity FROM Rooms WHERE Capacity >=1 AND Type = "+roomType;
        ResultSet rs = st.executeQuery(availableRooms);
        System.out.println("insidehotelbook");
        while (rs.next()) {
            int Type = rs.getInt("Type");
            int Capacity = rs.getInt("Capacity");
            if(Capacity >=1)
                System.out.println("Booked room for " + guestName);
        }
        rs.close();
     return true;
    }
    @Override
    public List<String> hotelClientGuests() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> hotelClientRevenue() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
