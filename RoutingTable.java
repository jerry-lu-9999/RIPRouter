import java.util.List;

public class RoutingTable {

    List<Route> routes;

    public static class Route{
        String networkID;
        int metric;
        String nextHop;
        int face;

        public Route(String networkID, int face) {
            this.networkID = networkID;
            this.metric = 1;
            this.nextHop = "FF:FF:FF:FF:FF:FF";
            this.face = face;
        }
    }

    @Override
    public String toString(){
        StringBuilder result = new StringBuilder();
        for(Route r : routes){
            result.append(String.format(r.networkID + " " + r.metric + " " + r.nextHop + " " + r.face));
            result.append("\n");
        }
        return result.toString();
    }
}
