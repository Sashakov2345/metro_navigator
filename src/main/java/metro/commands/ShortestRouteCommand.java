package metro.commands;

import metro.Metro;

public class ShortestRouteCommand extends Command {
    private String lineName2;
    private String stationName2;

    public ShortestRouteCommand(Metro metro, String lineName1, String stationName1, String lineName2, String stationName2) {
        super(metro);
        this.lineName = lineName1;
        this.stationName = stationName1;
        this.lineName2 = lineName2;
        this.stationName2 = stationName2;
    }

    @Override
    public void execute() {
        metro.outputShortestPath(lineName, stationName, lineName2, stationName2);
    }
}
