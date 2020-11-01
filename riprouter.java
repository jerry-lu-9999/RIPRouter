
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class riprouter {

    List<Integer> interfaceNum = new ArrayList<>();
    List<String> macAddr = new ArrayList<>();
    List<String> ipAddr = new ArrayList<>();
    List<Integer> mask = new ArrayList<>();

    public riprouter(List<Integer> num, List<String> mac, List<String> ip, List<Integer> mask){
        this.interfaceNum = num;
        this.macAddr = mac;
        this.ipAddr = ip;
        this.mask = mask;
    }

    //convert subnet mask into CIDR notation
    public static int convertMask(String mask){
        int bits = 0;
        String[] arr = mask.split("\\.");
        for(int i = 0; i < arr.length; i++){
            int temp = Integer.bitCount(Integer.parseInt(arr[i]));
            if (temp == 8) {
                bits += temp;
            }else{
                bits += temp;   return bits;
            }
        }
        return bits;
    }
    public static void main(String[] args) throws FileNotFoundException {
        //reading the router info
        File routerConfig = new File(args[0]);
        Scanner scan = new Scanner(routerConfig);

        List<Integer> interfaceNum = new ArrayList<>();
        List<String> macAddr = new ArrayList<>();
        List<String> ipAddr = new ArrayList<>();
        List<Integer> mask = new ArrayList<>();

        while (scan.hasNextLine()) {
            String[] line = scan.nextLine().split(" ");
            interfaceNum.add(Integer.parseInt(line[0]));
            macAddr.add(line[1]);
            ipAddr.add(line[2]);
            mask.add(convertMask(line[3]));
        }
        riprouter r = new riprouter(interfaceNum, macAddr, ipAddr, mask);
        System.out.printf("Router has %d entry\n", interfaceNum.size());
        System.out.println(mask);
        // File frames = new File(args[1]);
        // scan = new Scanner(frames);

        // while(scan.hasNextLine()){

        // }
         
        scan.close();
    }
}
