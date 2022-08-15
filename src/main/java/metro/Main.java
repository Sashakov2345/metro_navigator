package metro;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
      //  System.out.println(System.getProperty("user.dir"));
        try {
            Metro metro = new Metro(args[0]);
            if (metro.isCorrectFile()) {
                metro.applyCommands();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error! Such a file doesn't exist!");
        }
    }
}

