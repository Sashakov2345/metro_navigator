package metro.commands;

import metro.Metro;
import metro.Station;

public abstract class Command {
    protected String lineName;
    protected String stationName;
    protected Metro metro;
    public Command(Metro metro) {
        this.metro=metro;
    }

    public abstract void execute();
}
