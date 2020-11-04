
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Riprouter {

    List<String> macAddr;
    RoutingTable rt;

    public Riprouter(List<String> macAddr, RoutingTable rt) {
        this.macAddr = macAddr;
        this.rt = rt;
    }

    //convert subnet mask into CIDR notation
    public static String convertMask(String mask){
        int bits = 0;
        String[] arr = mask.split("\\.");

        for(int i = 0; i < arr.length; i++){
            int temp = Integer.bitCount(Integer.parseInt(arr[i]));
            if (temp == 8) {
                bits += temp;
            }else{
                bits += temp;   return String.valueOf(bits);
            }
        }
        return String.valueOf(bits);
    }

    /*
    1. drop Bad MacAddress
    2. drop no destination
    3. drop bad interface
    4. drop broadcast address
    interfaceNum  srcMac   destMac  srcIp destIp  tag payload
     */
    public static boolean checkFrame(Riprouter r, String frame){
        String[] frameArr = frame.split(" ");
        int intNum = Integer.parseInt(frameArr[0]);
        String destIp = frameArr[4];

        //check if the macAddr exist in the router config
        if (!r.macAddr.contains(frameArr[2])){
            return false;
        }

        //drop bad interface
        if(badInterface(r.rt, destIp, intNum)){
            return false;
        }
        return true;
    }

    public static boolean badInterface(RoutingTable rt, String destIp, int intNum){
        List<RoutingTable.Route> routes = rt.routes;
        for(int i = 0; i < routes.size(); i++){
            String networkID = routes.get(i).networkID;
            if(belongsToSubnet(destIp, networkID)){
                if(intNum == routes.get(i).face){
                    return true;
                }
            }
        }
    }

    //Check if a ip address belongs to a subnet (CIDR notation)
    public static boolean belongsToSubnet(String destIp, String networkID) {

    }

    public static void main(String[] args) throws FileNotFoundException {
        //reading the router info
        File routerConfig = new File(args[0]);
        Scanner scan = new Scanner(routerConfig);

        List<String> macAddr = new ArrayList<>();
        RoutingTable rt = new RoutingTable();
        List<RoutingTable.Route> r = new ArrayList<>();

        while (scan.hasNextLine()) {
            String[] line = scan.nextLine().split(" ");
            macAddr.add(line[1]);
            String ip = line[2];    String mask = line[3];
            //TODO: 192.168.3.0
            String networkID = ip + "/" + convertMask(mask);
            r.add(new RoutingTable.Route(networkID, Integer.parseInt(line[0])));
        }

        rt.routes = r;
        Riprouter router = new Riprouter(macAddr, rt);
        System.out.println("Route table has " + macAddr.size()  + " entries");
        System.out.println(rt);

        //Frame configuration
         File frames = new File(args[1]);
         scan = new Scanner(frames);

         while(scan.hasNextLine()){
             if(checkFrame(router, scan.nextLine())){

             }
         }
         
        scan.close();
    }
}
