package metro;

import com.google.gson.annotations.Expose;

import java.util.*;

public class Station {
    public static final int TIME_FOR_TRANSFER = 5;
    @Expose
    private final String name;
    @Expose
    private ArrayList<Map<String, String>> transfer = new ArrayList<>();
    @Expose
    private Integer time;
    @Expose
    private ArrayList<String> prev;
    @Expose
    private ArrayList<String> next;

    private ArrayList<Station> prevStations=new ArrayList<>();
    private ArrayList<Station> nextStations=new ArrayList<>();

    private List<Station> transferList = new ArrayList<>();

    private String line;

    public Station(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Station(String name, String line) {
        this.name = name;
        this.line = line;
    }

    public Station(String name, int time) {
        this.name = name;
        this.time = time;
    }

    public ArrayList<Map<String, String>> getTransfer() {
        return transfer;
    }

    public void setTransfer(ArrayList<Map<String, String>> transfer) {
        this.transfer = transfer;
    }

    @Override
    public String toString() {
        String s1 = name;
        StringBuilder connectedStations = new StringBuilder("");
        String s2 = "";
        if (!transfer.isEmpty()) {
            transfer.stream().forEach(map -> connectedStations
                    .append(" - " + map.get("station") + " (" + map.get("line") + ")"));
            s2 = connectedStations.toString();
            ;
        }
        return s1 + s2;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public ArrayList<Station> getPrevStations() {
        return prevStations;
    }

    public void setPrevStations(ArrayList<Station> prevStations) {
        this.prevStations = prevStations;
    }

    public ArrayList<Station> getNextStations() {
        return nextStations;
    }

    public void setNextStations(ArrayList<Station> nextStations) {
        this.nextStations = nextStations;
    }

    public List<Station> getTransferList() {
        return transferList;
    }

    public void setTransferList(List<Station> transferList) {
        this.transferList = transferList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Station)) return false;
        Station station = (Station) o;
        return name.equals(station.name) && line.equals(station.line);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, line);
    }

    public int getTime() {
        return Objects.equals(time, null) ? 0 : time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public ArrayList<String> getPrev() {
        return prev;
    }


    public ArrayList<String> getNext() {
        return next;
    }

}
