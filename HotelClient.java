import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author o876
 */
public class HotelClient {
    public static void main (String [] args) {
        try {

            if (args.length < 2) {
                printUsage();
                Registry reg = LocateRegistry.getRegistry("localhost",1099);
                RoomManager c = (RoomManager) reg.lookup("rmi://localhost:1099/HotelService");
                if (args[0].compareTo("list") == 0) {

                    List<Rooms> list = c.getRoomsAvailbale();
                    for (Rooms r :list){
                        System.out.println(r.getCapacity() +" rooms of type "+ r.getType() +" are available for "+ r.getCost() +" UGX per night ");
                    }

                }
                else if (args[0].compareTo("book") == 0)
                    handleBook(args,c);

                else if (args[0].compareTo("guests") == 0)
                    c.hotelClientGuests();
                else
                    printUsage();
            }
        }
         catch(Exception e){
                System.out.println("Received Exception:");
                System.out.println("client exception:"+e.toString());
                e.printStackTrace();
            }
        }
    static private void printUsage() {
        System.out.println("Available options:\n\thotelclient <address> list\n" +
                "\thotelclient <address> book <type> <Guest name>\n" +
                "\thotelclient <address> guests\n");
    }

    static private void handleBook(String[] args, RoomManager r) throws SQLException, RemoteException {
        String name = "";
        String type = null;
        if(args.length < 5)
        for(int i = 0; i < args.length; i++) {
            name += args[1];
            type += args[0];
        }
        r.hotelClientBook((Integer.parseInt(type)),name);
//        try {
//            if(!r.hotelClientBook(Integer.parseInt(args[0]), name))
//                System.out.println("Failed to book");
//            else
//
//        } catch (Exception e) {
//            System.out.println("Received Exception:");
//            System.out.println(e);
//        }

    }
}
