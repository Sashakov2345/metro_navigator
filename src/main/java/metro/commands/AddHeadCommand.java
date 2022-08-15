package metro.commands;

import metro.Metro;
import metro.Station;

public class AddHeadCommand extends Command {
    public AddHeadCommand(Metro metro, String lineName, String stationName) {
        super(metro);
        this.lineName = lineName;
        this.stationName = stationName;
    }

    @Override
    public void execute() {
//        metro.addHeadStation(lineName,stationName);
    }
}
