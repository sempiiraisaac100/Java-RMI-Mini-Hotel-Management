import java.io.Serializable;
import java.rmi.RemoteException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author o876
 */

/**
 * RoomManagerImpl class implements methods in the RoomManager interface and serialized to ease
 *ease the storing and sending of its objects to other processes.
 *
 *RoomManagerImpl class extends java.rmi.server.UnicastRemoteObject,thereby inheriting the remote behavior
 *provided by the classes java.rmi.server.RemoteObject and java.rmi.server.RemoteServer
 */

public class RoomManagerImpl extends java.rmi.server.UnicastRemoteObject implements RoomManager, Serializable {

    //declaring string variables to be used for getting connection to mysql database server
    String dbURL = "jdbc:mysql://localhost:3306/hotel";
    String username = "marv";
    String password = "@marvin256";
    
    //RoomManagerImpl() constructor for instantiating a remote object with inherited remote behaviours
    //and throws RemoteException to report any remote communication failures encountered in invoking it.
    public RoomManagerImpl () throws RemoteException {
     super ();
    }

    //returns a list of available rooms
    @Override
    public List<Room> getRoomsAvailable() throws RemoteException, SQLException {

        //instantiating a list object to store an available rooms
        List <Room> list = new ArrayList<>();

        try{
            //locating the mysql jdbc driver class
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //Using the located driver to get connection to the database server
        Connection con = DriverManager.getConnection(dbURL, username, password);

        //creating an interface used to send a SQL query to the database
        Statement st = con.createStatement();

        //creating a query to fetch available rooms from Rooms table
        String availableRooms = "SELECT * FROM Rooms";

        //executing the query through the st interface
        ResultSet rs = st.executeQuery(availableRooms);

        //extracting results and assigning them to appropriate variables
        while (rs.next()) {
            int Type = rs.getInt("Type");
            int Capacity = rs.getInt("Capacity");
            String Size = rs.getString("Size");
            String Cost = rs.getString("Cost");

            //instantiating a Room data class object for storing the returned variables
            Room room = new Room();

            //Using interfaces provided by to the room object to set its private variables
            room.setType(Type);
            room.setCapacity(Capacity);
            room.setSize(Size);
            room.setCost(Cost);

            //adding available rooms to the list
            list.add(room);
        }

        //closing the connection
        rs.close();

        //returning the list of available rooms
        return list;
        }

    //Returns status of either a room can be booked or not(if that room type is not available) by the guest
    @Override
    public boolean hotelClientBook(int roomType, String guestName) throws RemoteException, SQLException {

        try{
            //loading the driver class
            Class.forName("com.mysql.jdbc.Driver");

            //setting the bound to only available room types
            if(roomType >=0 && roomType < 5) {

                //changing the passed int roomType to String
                String roomTypeChange = Integer.toString(roomType);

                //establishing connection to the database using the loaded driver class
                Connection con = DriverManager.getConnection(dbURL, username, password);

                     //Query to fetch the cost of a roomType
                    String NoOfBookedRooms = "SELECT `Type`,`Cost`,Count(*) as tot FROM `Reservation` WHERE Type ='"+roomType+"' GROUP BY Type";
                    
                     //creating an interface used to send a SQL query to the database
                    Statement st = con.createStatement();

                    //executing the query through the st interface
                    ResultSet rs = st.executeQuery(NoOfBookedRooms);

                    //ensuring that we have results
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

                    //Assigning the fetched cost to cost variable
                    String cost = rs2.getString("Cost");
                    int costFinal = Integer.parseInt(cost);

                    //query to insert a reservation made by the guest
                    String reserv = "INSERT INTO Reservation(Type,Name,Cost) values(?,?,?)";

                    //Using a prepared statement interface to allow execution of the query once
                    PreparedStatement pst = con.prepareStatement(reserv);

                    //passing guest data to the created interface
                    pst.setString(1, roomTypeChange);
                    pst.setString(2, guestName);
                    pst.setInt(3, costFinal);
                    pst.execute();

                    //Echoing status
                    System.out.println("Booking successful");

                    //Closing the connection
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

        //catching driver not found error
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("e");
            return false;

        //catching sql query errors encountered
        }catch ( SQLException e ){
            e.printStackTrace();
            return false;
        }
    }

    //returns a list of registered guests
    @Override
    public List<Guest> hotelClientGuests() throws RemoteException, SQLException {

        //creating an object to store registered guests
        List <Guest> list1 = new ArrayList<>();

        try{
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection con = DriverManager.getConnection(dbURL, username, password);
        Statement st = con.createStatement();

        //Creating a query to fetch a reservation from which a guest is to be obtained
        String availableReservation = "SELECT * FROM Reservation";
        ResultSet rs = st.executeQuery(availableReservation);

        while (rs.next()) {
            //storing the guest name from the results
            String Name = rs.getString("Name");

            //Instantiating a guest data class
            Guest reserv = new Guest();

            //set the private variable name of the Guest object
            reserv.setName(Name);

            //adding the guest object to the list
            list1.add(reserv);
        }

        //closing the connection
        rs.close();

        //returning a list of registered guests
        return list1;

    }

    //Returning the revenue obtained from each room type
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
