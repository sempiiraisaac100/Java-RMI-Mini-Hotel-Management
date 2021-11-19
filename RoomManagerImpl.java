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

                    String NoOfBookedRooms = "SELECT `Type`,`Cost`,Count(*) as tot FROM `Reservation` WHERE Type ='"+roomType+"' GROUP BY Type";
                    Statement st = con.createStatement();
                    ResultSet rs = st.executeQuery(NoOfBookedRooms);
                    rs.next();
                    int TotalNoOfBookedRooms = rs.getInt("tot");


                    String TotalAvailableCapacity = "SELECT `Capacity`FROM `Rooms`WHERE Type ='"+roomType+"'";
                    Statement st1 = con.createStatement();
                    ResultSet rs1 = st1.executeQuery(TotalAvailableCapacity);
                    rs1.next();
                    int TotalAvailableCapacityValue = rs1.getInt("Capacity");


                if (TotalNoOfBookedRooms < TotalAvailableCapacityValue) {
                    String costOfRoom = "SELECT Cost FROM Rooms WHERE Type ='" + roomType + "'";
                    Statement st2 = con.createStatement();
                    ResultSet rs2 = st2.executeQuery(costOfRoom);
                    rs2.next();
                    String cost = rs2.getString("Cost");
                    int costFinal = Integer.parseInt(cost);

                    String reserv = "INSERT INTO Reservation(Type,Name,Cost) values(?,?,?)";
                    PreparedStatement pst = con.prepareStatement(reserv);
                    pst.setString(1, roomTypeChange);
                    pst.setString(2, guestName);
                    pst.setInt(3, costFinal);
                    pst.execute();
                    System.out.println("Booking successful");
                    con.close();
                    return true;
                }else{
                    System.out.println("Total No Of BookedRooms is greater than Total Available Capacity");
                    return  false;
                }
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
    public List<Guest> hotelClientGuests() throws RemoteException, SQLException {
        List <Guest> list1 = new ArrayList<>();

        try{
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection con = DriverManager.getConnection(dbURL, username, password);
        Statement st = con.createStatement();
        String availableReservation = "SELECT * FROM Reservation";
        ResultSet rs = st.executeQuery(availableReservation);

        while (rs.next()) {
            String Name = rs.getString("Name");

            Guest reserv = new Guest();
            reserv.setName(Name);
            list1.add(reserv);
        }
        rs.close();
        return list1;

    }

    @Override
    public List<Revenue> hotelClientRevenue() throws RemoteException, SQLException {
        List <Revenue> list2 = new ArrayList<>();

        try{
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection con = DriverManager.getConnection(dbURL, username, password);
        Statement st = con.createStatement();
        String revenueFromRooms = "SELECT * ,SUM(Cost) AS TOT FROM Reservation GROUP BY Type";
        ResultSet rs = st.executeQuery(revenueFromRooms);

        while (rs.next()) {
            int Type = rs.getInt("Type");
            int Cost = rs.getInt("TOT");

            Revenue re = new Revenue();
            re.setType(Type);
            re.setCost(Cost);
            list2.add(re);
        }
        rs.close();
        return list2;
    }
    
}
