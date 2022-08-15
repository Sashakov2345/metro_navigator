package metro.commands;

import metro.Metro;

public class ErrorCommand extends Command{
    public ErrorCommand(Metro metro) {
        super(metro);
    }

    @Override
    public void execute() {
        System.out.println("Invalid command");
    }
}
