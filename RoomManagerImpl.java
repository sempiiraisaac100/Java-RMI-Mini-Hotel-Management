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
    public List<Room> getRoomsAvailable() throws RemoteException, SQLException {
        List <Room> list = new ArrayList<>();

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

            Room room = new Room();
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

            if(roomType >=0 && roomType < 5) {
                String roomTypeChange = Integer.toString(roomType);
                Connection con = DriverManager.getConnection(dbURL, username, password);
                String costOfRoom = "SELECT Cost FROM Rooms WHERE Type ='"+roomType+"'";
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(costOfRoom);
                rs.next();
                String cost = rs.getString("Cost");

                String reserv = "INSERT INTO Reservation(Type,Name,Cost) values(?,?,?)";
                PreparedStatement pst = con.prepareStatement(reserv);
                pst.setString(1, roomTypeChange);
                pst.setString(2, guestName);
                pst.setString(3, cost);
                pst.execute();
                System.out.println("Booking successful");
                con.close();
                return true;
            }
            else {
                System.out.println("why false");
                return false;

            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("e");
            return false;
        }catch ( SQLException e ){
            e.printStackTrace();
            return false;
        }
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
