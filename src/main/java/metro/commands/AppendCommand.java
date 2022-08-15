package metro.commands;

import metro.Metro;
import metro.Station;

public class AppendCommand  extends Command{
    public AppendCommand(Metro metro, String lineName, String station) {
        super(metro);
        this.lineName = lineName;
        this.stationName = station;
    }

    @Override
    public void execute() {
//        metro.appendStation(lineName,stationName);
    }
}
