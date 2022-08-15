package metro.commands;

import metro.Metro;

public class OutputCommand extends  Command{
    public OutputCommand(Metro metro, String lineName) {
        super(metro);
        this.lineName = lineName;
    }

    @Override
    public void execute() {
       // metro.outputLine(lineName);
    }
}
