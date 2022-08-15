package metro.commands;

import metro.Metro;
import metro.Station;

public class RemoveCommand extends Command{
    public RemoveCommand(Metro metro, String lineName, String stationName) {
        super(metro);
        this.lineName = lineName;
        this.stationName = stationName;
    }

    @Override
    public void execute() {
//        metro.removeStation(lineName,stationName);
    }
}
