package metro;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MetroLine {
    private String lineName;
    private LinkedList<Station> stations;
    private Station firstStation;
    private Station lastStation;
    private Map<String,Station> stationMap=new HashMap<>();

    public MetroLine(String lineName, LinkedList<Station> stations) {
        this.lineName = lineName;
        this.stations = stations;
        firstStation=stations.getFirst();
        lastStation=stations.getLast();
    }

    public Map<String, Station> getStationMap() {
        return stationMap;
    }

    public void setStationMap(Map<String, Station> stationMap) {
        this.stationMap = stationMap;
    }

    public String getLineName() {
        return lineName;
    }


    public LinkedList<Station> getStations() {
        return stations;
    }

}
