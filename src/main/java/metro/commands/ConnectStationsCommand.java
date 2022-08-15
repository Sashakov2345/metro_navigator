package metro.commands;

import metro.Metro;
import metro.Station;

public class ConnectStationsCommand extends Command {
    private String lineName2;
    private String stationName2;

    public ConnectStationsCommand(Metro metro, String lineName1, String stationName1, String lineName2, String stationName2) {
        super(metro);
        this.lineName = lineName1;
        this.stationName = stationName1;
        this.lineName2 = lineName2;
        this.stationName2 = stationName2;
    }

    @Override
    public void execute() {
//        metro.connectStations(lineName,stationName,lineName2,stationName2);
    }
}
