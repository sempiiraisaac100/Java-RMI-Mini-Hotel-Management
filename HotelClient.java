import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.util.List;


public class HotelClient {

    public static void main (String [] args) {

        //If not correct usage then print usage


        if(args.length > 1 && args.length <= 4) {
            try{

                //Returns a reference to the remote object Registry for the local host
                // on the default registry port of 1099
                Registry reg = LocateRegistry.getRegistry("localhost",1099);

                try{
                    String port = args[1].split(":")[1];  //get port string in the provided server address
                    //check for validity length of the port string
                    if(port.length() < 1 || port.length() > 5){

                        System.out.println("Invalid server address supplied");

                        System.exit(0);

                    }else{
                        int portx = Integer.parseInt(port); //convert port string-integer to integer
                        //check if valid port range 
                        if(portx < 0 || portx > 65535) {

                            System.out.println("Invalid server address supplied");

                            System.exit(0);
                        }
                    }
                }catch (Exception e) {

                    System.out.println("Invalid server address supplied");

                    System.exit(0);
                }

                RoomManager c = (RoomManager) reg.lookup("rmi://"+ args[1] + "/HotelService"); //pass server address to the lookup method after validity check

                //check passed command to take action 
                switch (args[0]) {
                    case "list":
                        if(args.length == 2){
                            List<Room> list = c.getRoomsAvailable();
                            for (Room r :list){
                                System.out.println(r.getCapacity() +" rooms of type "+ r.getType() +" are available for "+ r.getCost() +" UGX per night ");
                            }
                        }else {
                            printUsage();
                        }
                        break;
                    case "guests":
                        if(args.length == 2){
                            List<Guest> list =  c.hotelClientGuests();
                            for (Guest r :list){
                                System.out.println("Name : "+r.getName());
                            }
                        }else printUsage();
                        break;
                    case "book":
                        if(args.length == 4){
                            handleBook(args,c);
                        }else printUsage();
                        break;
                    case "revenue":
                        if(args.length == 2){
                            List<Revenue> list =  c.hotelClientRevenue();
                            for (Revenue r :list){
                                System.out.println("Type : "+r.getType());
                                System.out.println("TOT : "+r.getCost());
                            }
                        }else printUsage();
                        break;
                    default:
                        printUsage();
                        break;
                }
            }catch (RemoteException e) {
                System.out.println("Remote Exception encountered");
                e.printStackTrace();
            }catch (NotBoundException e){
                System.out.println("Error occurred while binding service");
                e.printStackTrace();
            }catch (SQLException e) {
                System.out.println("SQL error occurred");
                e.printStackTrace();
            }
        }else {
            printUsage();
        }
    }
    static private void printUsage() {
        System.out.println("Incorrect command usage");
        System.out.println("Available options:\n\tHotelclient list <address>\n" +
                "\tHotelclient book <address> <type> <Guest name>\n" +
                "\tHotelclient guests <address>\n" +
                "\tHotelclient revenue <address>\n");
    }

    static private void handleBook(String[] args, RoomManager r) throws SQLException, RemoteException {
        String name = args[3];
        try{
            int type = Integer.parseInt(args[2]);

            // check if acceptable room type
            if((type >= 0 && type < 5)){
                boolean result = r.hotelClientBook(type,name);
                System.out.println(result);
            }else{
                System.out.println("type should be between 0 and 4");
            }
        }catch (NumberFormatException e){
            System.out.println("Invalid room type supplied");
            System.exit(0);
        }

    }
}
